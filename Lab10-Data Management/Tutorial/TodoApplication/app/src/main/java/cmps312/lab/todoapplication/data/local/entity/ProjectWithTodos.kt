package cmps312.lab.todoapplication.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectWithTodos(
    @Embedded val project : Project,
    @Relation(
        parentColumn = "id",
        entityColumn = "pid"
    )
    val todos : List<Todo>
)