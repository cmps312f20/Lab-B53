package cmps312.lab.todoapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragment)
        val appBarConfiguration =  AppBarConfiguration(setOf(R.id.projectListFragment));
        setupActionBarWithNavController(this, navController, appBarConfiguration)

    }
    override fun onSupportNavigateUp() =  navController.navigateUp() || super.onSupportNavigateUp()
}