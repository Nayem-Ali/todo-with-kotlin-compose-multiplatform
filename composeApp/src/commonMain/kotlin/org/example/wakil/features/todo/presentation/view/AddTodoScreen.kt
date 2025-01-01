package org.example.wakil.features.todo.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.wakil.core.components.AppBarComponent
import org.example.wakil.features.todo.data.model.Todo
import org.example.wakil.features.todo.presentation.view_model.TodoViewModel

class AddTodoScreen(val todo: Todo?=null): Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var title by remember { mutableStateOf( todo?.title ?: "") }

        var focusRequester = FocusRequester()

        val todoViewModel = navigator.koinNavigatorScreenModel<TodoViewModel>()

        LaunchedEffect(Unit){
            focusRequester.requestFocus()
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Add Todo")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator.pop()
                            }
                        ){
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Navigation")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if(todo==null) {
                                    todoViewModel.addTodo(title)
                                } else {
                                    todoViewModel.updateTitle(
                                        index = todoViewModel.todos.value.indexOf(todo),
                                        title = title
                                    )
                                }

                                navigator.pop()
                            },
                            enabled = title.isNotEmpty()
                        ){
                            Icon(Icons.Default.Check, contentDescription = "Done")
                        }
                    }
                )

            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {

                BasicTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxSize()
                        .focusRequester(focusRequester=focusRequester),
                    decorationBox = { innerTextField ->

                        Row {
                            if(title.isEmpty()){
                                Text(text = "Enter Todo")
                            }
                            innerTextField()
                        }



                    }
                )


            }
        }
    }
}