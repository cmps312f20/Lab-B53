package cmps312.lab.todoapplication.repository

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

object TodoListRepo {
    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    val projectDocumentsRef by lazy { db.collection("projects") }
    val todoDocumentsRef by lazy { db.collection("todos") }

    init {
        //enable offline caching
        val settings = firestoreSettings { isPersistenceEnabled = true }
        db.firestoreSettings = settings
    }

//    suspend fun getProjects(): List<Project> =
//        projectDocumentsRef.get().await().toObjects(Project::class.java)

    fun addProject(project: Project) = projectDocumentsRef.add(project)
        .addOnSuccessListener {
            Log.d(TAG, it.toString())
        }.addOnFailureListener {
            Log.d(TAG, it.stackTraceToString())
        }


    suspend fun deleteProject(project: Project) =
        projectDocumentsRef.document(project.id).delete()

    suspend fun getTodoListByProject(pid: String): List<Todo> {

        val querySnapShot = todoDocumentsRef.whereEqualTo("projectId", pid).get().await()
        val todos = mutableListOf<Todo>()
//        val todos = querySnapShot.toObjects(Todo::class.java)
//        todos.get(0).id?.let { Log.d("MY TAG", it) }

        querySnapShot.forEach {
            val todo = it.toObject(Todo::class.java)
            todo.id = it.id
            todos.add(todo)
        }

        return todos
    }

    suspend fun addTodo(todo: Todo) = todoDocumentsRef.add(todo)
        .addOnSuccessListener {
            Log.d(TAG, it.toString())
        }.addOnFailureListener {
            Log.d(TAG, it.stackTraceToString())
        }

//    suspend fun getTodo(id: String) =
//        todoDocumentsRef.document(id).get().await().toObject(Todo::class.java)

    suspend fun deleteTodo(todo: Todo) = todoDocumentsRef.document(todo.id).delete()
    suspend fun updateToDo(todo: Todo) = todoDocumentsRef.document(todo.id).set(todo).await()

    suspend fun getUserProject(userId: String): List<Project>? {
        val querySnapShot = projectDocumentsRef
            .whereEqualTo("userId", userId).get().await()
        val projects = mutableListOf<Project>()
        querySnapShot.forEach {
            val project = it.toObject(Project::class.java)
            project.id = it.id
            projects.add(project)
        }
        return projects
    }

    //Todo  suspend fun uploadPhoto(photoUri: Uri): String
    suspend fun uploadPhoto(photoUri: Uri): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "IMAGE_" + timestamp + "_.png"
        val storageRef = FirebaseStorage.getInstance()
            .reference.child("images").child(fileName)
        storageRef.putFile(photoUri).await()
        return storageRef.downloadUrl.await().toString()
    }

}
