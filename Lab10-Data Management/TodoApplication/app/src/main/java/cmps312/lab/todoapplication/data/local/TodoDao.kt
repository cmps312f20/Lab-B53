package cmps312.lab.todoapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import cmps312.lab.todoapplication.data.local.entity.Project
import cmps312.lab.todoapplication.data.local.entity.ProjectWithTodos
import cmps312.lab.todoapplication.data.local.entity.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM Project")
    fun getProjects(): LiveData<List<Project>>

    @Query("SELECT * FROM Todo")
    fun getAllTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM Todo WHERE pid =:pid")
    fun getTodoListByProject(pid : Int): LiveData<List<Todo>>

    @Query("SELECT * FROM Todo WHERE id = :id")
    suspend fun getTodo(id: Int): Todo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: Todo) : Long

    @Update //similar to the add [but preserves the original ID]
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo): Int

    @Transaction
    @Query ("SELECT * FROM Project")
    suspend fun getProjectWithTodos(): List<ProjectWithTodos>

    @Delete
    suspend fun deleteProject(project: Project)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProject(project: Project)

}