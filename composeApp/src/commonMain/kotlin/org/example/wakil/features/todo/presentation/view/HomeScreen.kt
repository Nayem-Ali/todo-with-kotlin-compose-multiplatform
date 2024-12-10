package org.example.wakil.features.todo.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.wakil.core.components.AppBarComponent
import org.example.wakil.features.todo.data.model.Todo
import org.example.wakil.features.todo.presentation.view_model.TodoViewModel


class HomeScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val todoViewModel = navigator.koinNavigatorScreenModel<TodoViewModel>()

        val todos = todoViewModel.todos.collectAsState()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Todo App")
                    }
                )

            },

            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator.push(AddTodoScreen())
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add todo")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                ) {

                    items(todos.value.size) { index: Int ->

                        val todo = todos.value[index]

                        TodoAppInterface(
                            todo = todo,
                            onDeleted = {
                                todoViewModel.deleteTodo(index = index)
                            },
                            onUpdate = {
                                todoViewModel.updateTodo(index=index)
                            },
                            onUpdateTitle = {
                                navigator.push(AddTodoScreen(todo = todo))
                            }
                        )

                    }

                }

            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoAppInterface(
    todo: Todo,
    onDeleted: () -> Unit = {},
    onUpdate: () -> Unit = {},
    onUpdateTitle: () -> Unit = {},
) {

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(all = 5.dp)
            .combinedClickable(
                onLongClick = {
                    onDeleted()
                }

            ){

            }
            .clickable {
                onUpdateTitle()
            }


    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = todo.title, modifier = Modifier.padding(all = 10.dp)
            )
            Checkbox(
                modifier = Modifier.padding(all = 10.dp),
                colors = CheckboxDefaults.colors(checkedColor = Color.Green),
                checked = todo.isCompleted,
                onCheckedChange = { onUpdate() }
            )

        }
    }
}