package org.example.wakil.features.todo.presentation.view_model

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.wakil.features.todo.data.model.Todo

class TodoViewModel:ScreenModel {

    private var _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos : StateFlow<List<Todo>> = _todos

    fun addTodo(title: String){
        val newTodo = Todo(title = title, isCompleted = false)
        _todos.value += newTodo

        println(_todos.value.size)
    }

    fun updateTodo(index: Int){
        var myTodos: MutableList<Todo> = _todos.value.toMutableList()
        myTodos[index] = myTodos[index].copy(isCompleted = !myTodos[index].isCompleted)
        _todos.value = myTodos
    }

    fun updateTitle(index: Int, title: String){
        var myTodos: MutableList<Todo> = _todos.value.toMutableList()
        myTodos[index] = myTodos[index].copy(title = title)
        _todos.value = myTodos
    }

    override fun onDispose() {
        println("Todo view model detached")
        super.onDispose()
    }

    fun deleteTodo(index: Int) {


        _todos.value = _todos.value.filterIndexed { i, _ -> i != index }
        println("Delete command hit ${_todos.value.size}")

    }

}