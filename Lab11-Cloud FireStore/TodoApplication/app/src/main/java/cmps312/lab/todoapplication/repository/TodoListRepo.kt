package cmps312.lab.todoapplication.repository

import android.util.Log
import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import kotlinx.coroutines.tasks.await

object TodoListRepo {
    val TAG = "TodoListRepo"

    //dao
    val db by lazy { FirebaseFirestore.getInstance() }

    val projectDocumentRef by lazy { db.collection("projects") }
    val todosDocumentRef by lazy { db.collection("todos") }

    //let make the database work offline
    //money
    init {
        db.firestoreSettings = firestoreSettings { isPersistenceEnabled = true }
    }

    suspend fun getProjects() = projectDocumentRef.get().await().toObjects(Project::class.java)

    fun addProject(project: Project) = projectDocumentRef.add(project)
        .addOnSuccessListener { Log.d(TAG, "Added successfully") }
        .addOnFailureListener { Log.d(TAG, "Failed to add the project") }

    suspend fun deleteProject(project: Project) = projectDocumentRef
        .document(project.projectId).delete()

    suspend fun getTodoListByProject(pid: String): MutableList<Todo> {
        val snapshot = todosDocumentRef.whereEqualTo("projectId", pid).get().await()
        val todos = mutableListOf<Todo>()

        snapshot?.forEach {
            val todo = it.toObject(Todo::class.java)
            todo.todoId = it.id
            todos.add(todo)
        }
        return todos
    }

    suspend fun addTodo(todo: Todo) = todosDocumentRef.add(todo)
        .addOnSuccessListener { Log.d(TAG, "Added successfully") }
        .addOnFailureListener { Log.d(TAG, "Failed to add the Todo")}

    suspend fun getTodo(id: String) = todosDocumentRef.document(id).get().await().toObject(Todo::class.java)
    suspend fun deleteTodo(todo: Todo) = todo.todoId?.let { todosDocumentRef.document(it).delete().await() }
    suspend fun updateToDo(todo: Todo) = todo.todoId?.let { todosDocumentRef.document(it).set(todo).await() }
}
