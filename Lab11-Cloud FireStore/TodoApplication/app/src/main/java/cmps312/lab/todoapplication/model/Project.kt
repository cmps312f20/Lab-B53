package cmps312.lab.todoapplication.model

import com.google.firebase.firestore.DocumentId

data class Project(
    var projectId: String = "",
    var name: String? = "",
    val userId: String? = ""
)