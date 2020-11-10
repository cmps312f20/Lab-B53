package cmps312.lab.todoapplication.repository

import android.util.Log
import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import kotlinx.coroutines.tasks.await

object TodoListRepo {
    const val TAG = "TodoListRepo"
    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val projectDocumentRef by lazy { db.collection("projects") }
    val todosDocumentRef by lazy { db.collection("todos") }

    init {
        db.firestoreSettings = firestoreSettings { isPersistenceEnabled = true }
    }

    suspend fun getProjects()= projectDocumentRef.get().await().toObjects(Project::class.java)

    fun addProject(project: Project) = projectDocumentRef.add(project)
        .addOnSuccessListener { Log.d(TAG, "Project successfully added: ") }
        .addOnFailureListener { Log.d(TAG, "Failed to add Project ") }


    suspend fun deleteProject(project: Project) =
        projectDocumentRef.document(project.projectId).delete().await()

    suspend fun getTodoListByProject(pid: String): MutableList<Todo> {
        val snapShot = todosDocumentRef
            .whereEqualTo("projectId", pid).get().await()
        val todos = mutableListOf<Todo>()
        snapShot.forEach {
            val todo = it.toObject(Todo::class.java)
            todo.todoId = it.id
            todos.add(todo)
        }
        return todos
    }

    fun addTodo(todo: Todo) = todosDocumentRef.add(todo)
        .addOnSuccessListener { Log.d(TAG, "Todo successfully added: ") }
        .addOnFailureListener { Log.d(TAG, "Failed to add Todo ") }

    suspend fun getTodo(id: String) =
        todosDocumentRef.document(id).get().await().toObject(Todo::class.java)

    fun deleteTodo(id: String) = todosDocumentRef.document(id).delete()
    fun updateToDo(todo: Todo) = todo.todoId?.let { todosDocumentRef.document(it).set(todo) }
}
