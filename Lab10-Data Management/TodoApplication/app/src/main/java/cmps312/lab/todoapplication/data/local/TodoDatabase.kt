package cmps312.lab.todoapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cmps312.lab.todoapplication.data.local.entity.Project
import cmps312.lab.todoapplication.data.local.entity.Todo

@Database(entities = [Todo::class, Project::class], version = 2, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    //inject the dao that you created
    abstract fun todoDao(): TodoDao

    //companion object to create the database or get the instance of the database

    companion object {
        @Volatile // [we dont want it to be cached..always the users should get the updated instance]
        private var database: TodoDatabase? = null

        /*protected from concurrent execution by multiple threads by the monitor of the instance*/
        @Synchronized
        fun getDatabase(context: Context): TodoDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_db"
                ).fallbackToDestructiveMigration().build()
            }
            return database as TodoDatabase
        }
    }
}