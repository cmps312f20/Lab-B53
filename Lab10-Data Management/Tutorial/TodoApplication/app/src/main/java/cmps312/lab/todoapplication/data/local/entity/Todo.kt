package cmps312.lab.todoapplication.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//One to Many [One projecet can have many stodos]
//this will enforce to not have inconsitent ID for a project.
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = ["id"],
            childColumns = ["pid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Todo(
    var title: String? = null,
    var status: Boolean? = null,
    var priority: String? = null,
    var date: String? = null,
    var time: String? = null,

    @ColumnInfo(index = true)
    var pid: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
