package cmps312.lab.todoapplication.repository

import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo

object TodoListRepo {
    suspend fun getProjects()= null
    fun addProject(project: Project) = null


    suspend fun deleteProject(project: Project) = null

    suspend fun getTodoListByProject(pid: String) = null
    suspend fun addTodo(todo: Todo) = null
    suspend fun getTodo(id: String) =null
    suspend fun deleteTodo(todo: Todo) = null
    suspend fun updateToDo(todo: Todo) = null
}
