package cmps312.lab.todoapplication.data.repository

import android.content.Context
import cmps312.lab.todoapplication.data.local.TodoDatabase
import cmps312.lab.todoapplication.data.local.entity.Project
import cmps312.lab.todoapplication.data.local.entity.Todo

class TodoListRepo(private val context: Context) {

    private val todoDao by lazy {
        TodoDatabase.getDatabase(context).todoDao()
    }

    fun getProjects() = todoDao.getProjects()
    suspend fun getProjectWithTodos()  = todoDao.getProjectWithTodos()
    suspend fun addProject(project: Project) = todoDao.addProject(project)
    suspend fun deleteProject(project: Project) = todoDao.deleteProject(project)

    fun getTodoListByProject(pid : Int) = todoDao.getTodoListByProject(pid)
    suspend fun getTodo(id: Int) = todoDao.getTodo(id)
    suspend fun addTodo(todo: Todo) =todoDao.addTodo(todo)
    suspend fun deleteTodo(todo: Todo)  = todoDao.deleteTodo(todo)
    suspend fun updateToDo(todo: Todo)  = todoDao.updateTodo(todo)




}