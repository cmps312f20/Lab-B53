package cmps312.lab.todoapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Project (
    val name : String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)