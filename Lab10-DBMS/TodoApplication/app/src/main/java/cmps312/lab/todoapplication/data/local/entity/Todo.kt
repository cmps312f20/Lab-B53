package cmps312.lab.todoapplication.data.local.entity

data class Todo(
    var title: String? = null,
    var status: Boolean? = null,
    var priority: String? = null,
    var date: String? = null,
    var time: String? = null,
    var pid: Int,
    val id: Int = 0
)
