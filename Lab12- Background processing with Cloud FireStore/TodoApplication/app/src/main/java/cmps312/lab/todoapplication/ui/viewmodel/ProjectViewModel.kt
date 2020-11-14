package cmps312.lab.todoapplication.ui.todo.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo
import cmps312.lab.todoapplication.repository.TodoListRepo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "ProjectViewModel"
    private var _projects = MutableLiveData<List<Project>>()
    private var _todos = MutableLiveData<List<Todo>>()
    val userId = MutableLiveData<String>()

    lateinit var registerTodoListener: ListenerRegistration
    lateinit var registerProjectListener: ListenerRegistration

    var projects: LiveData<List<Project>> = _projects
    var todos: LiveData<List<Todo>> = _todos

    lateinit var selectedTodo: Todo
    var selectedProject: Project? = null

    init {
       registerListners()
    }

    fun getTodos(projectId: String) {
        _todos.value = listOf<Todo>() //clear the list
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _todos.value =
                    cmps312.lab.todoapplication.repository.TodoListRepo.getTodoListByProject(
                        projectId
                    )
            }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.addTodo(todo).await()
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

    fun addProject(project: Project, photoUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            project.imageUrl = TodoListRepo.uploadPhoto(photoUri)
            project.userId = Firebase.auth.currentUser?.uid.toString()
            TodoListRepo.addProject(project)
        }
    }

    fun getUserProjects() {
        viewModelScope.launch {
            _projects.value =
                TodoListRepo.getUserProject(Firebase.auth.currentUser!!.uid.toString());
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.deleteProject(project)
        }
    }

    fun registerListners() {
        registerProjectListener()
        registerTodoListener()
    }

    fun unRegisterListners() {
        registerProjectListener.remove()
        registerTodoListener.remove()
    }

    private fun registerProjectListener() {
        registerProjectListener = TodoListRepo.projectDocumentsRef
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                val updatedProjectDocuments = mutableListOf<Project>()

                snapshot?.forEach {
                    val project = it.toObject(Project::class.java)
                    project.id = it.id

                    //todo wrap this with project.userId == Firebase.auth.currentUser!!.uid.toString()
                    updatedProjectDocuments.add(project)
                }

                _projects.value = updatedProjectDocuments

            }
    }


    private fun registerTodoListener() {

        registerTodoListener = TodoListRepo.todoDocumentsRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (selectedProject != null) {
                val todos = mutableListOf<Todo>()
                snapshot?.forEach {
                    val todo = it.toObject(Todo::class.java)
                    todo.id = it.id
                    if(todo.projectId == selectedProject!!.id)
                        todos.add(todo)
                }
                _todos.value = todos
            }

            val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                "Local"
            else
                "Server"

            if (snapshot != null && !snapshot.isEmpty) {
                Log.d(TAG, "$source data: ${snapshot.documents}")
            } else {
                Log.d(TAG, "$source data: null")
            }
        }
    }

}