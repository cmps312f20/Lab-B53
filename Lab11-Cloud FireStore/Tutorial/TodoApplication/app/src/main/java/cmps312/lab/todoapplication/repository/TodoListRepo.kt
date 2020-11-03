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

    val projectDocumentRef  by lazy { db.collection("projects") }
    val todosDocumentRef by lazy { db.collection("todos") }

    init {
        db.firestoreSettings = firestoreSettings { isPersistenceEnabled = true }
     }

    suspend fun getProjects()= projectDocumentRef.get().await().toObjects(Project::class.java)

    fun addProject(project: Project) = projectDocumentRef.add(project)
        .addOnSuccessListener { Log.d(TAG, "Project successfully added: ") }
        .addOnFailureListener { Log.d(TAG, "Failed to add Project ")}


    suspend fun deleteProject(project: Project) = null
    suspend fun getTodoListByProject(pid: String) = null
    suspend fun addTodo(todo: Todo) = null
    suspend fun getTodo(id: String) =null
    suspend fun deleteTodo(todo: Todo) = null
    suspend fun updateToDo(todo: Todo) = null
}
