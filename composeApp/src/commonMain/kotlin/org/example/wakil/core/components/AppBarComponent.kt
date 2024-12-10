package org.example.wakil.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarComponent(
    title: String,

){
    val navigator = LocalNavigator.currentOrThrow
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {

            if(navigator.canPop){
                IconButton(
                    onClick = {
                        navigator.pop()
                    }
                ){
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Navigation")

                }
            }
            // Back button
        }

    )
}