package cmps312.lab.todoapplication.ui.todo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo
import cmps312.lab.todoapplication.repository.TodoListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "ProjectViewModel"
    private var _projects = MutableLiveData<List<Project>>()
    private var _todos = MutableLiveData<List<Todo>>()

    var projects: LiveData<List<Project>> = _projects
    var todos: LiveData<List<Todo>> = _todos

    lateinit var selectedTodo: Todo
    var selectedProject: Project? = null

    init {
        listenToProjectChanges()
    }

    fun getTodos(projectId: String) {
        _todos.value = listOf<Todo>() //clear the list
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
//                _todos.value =
//                    cmps312.lab.todoapplication.repository.TodoListRepo.getTodoListByProject(projectId)
            }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.addTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.deleteTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.updateToDo(todo)
        }
    }

    fun addProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.addProject(project)
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.deleteProject(project)
        }
    }

    private fun listenToProjectChanges() {
        TodoListRepo.projectDocumentRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d("TAG", "Failed to listen $snapshot")
                return@addSnapshotListener
            }

            // _projects.value = snapshot?.toObjects(Project::class.java)

            val projects = mutableListOf<Project>()
            snapshot?.forEach {
                val project = it.toObject(Project::class.java)
                project.projectId = it.id
                projects.add(project)
            }
            _projects.value = projects
        }
    }

}





