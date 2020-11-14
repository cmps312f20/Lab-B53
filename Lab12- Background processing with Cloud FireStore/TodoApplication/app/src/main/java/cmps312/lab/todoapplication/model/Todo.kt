package cmps312.lab.todoapplication.model

import androidx.room.PrimaryKey

data class Todo(
    @PrimaryKey
//    @DocumentId
    var id: String = "",
    var title: String,
    var status: Boolean,
    var priority: String,
    var date: String,
    var time: String,
    var projectId: String
) {
    constructor() : this("", "", false, "", "", "", "")

    override fun toString(): String {
        return "id = $id :  projectId =$projectId"
    }
}