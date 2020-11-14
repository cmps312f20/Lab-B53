package cmps312.lab.todoapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import cmps312.lab.todoapplication.ui.todo.viewmodel.ProjectViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val SIGN_IN_CODE = 123
    private val projectViewModel: ProjectViewModel by viewModels()

    private lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.projectListFragment));
        setupActionBarWithNavController(this, navController, appBarConfiguration)

        //todo create the listner
        Firebase.auth.addAuthStateListener {
            if(it.currentUser?.uid == null){
                showSignIn();
            }else{
                projectViewModel.registerListners()
                val displayName = it.currentUser!!.displayName ?: "Unknown"
                Toast.makeText(this, "Welcome Mr $displayName", Toast.LENGTH_SHORT).show()
            }
        }
    }
/
    //todo create the sign in
    fun  showSignIn(){
        //providers for the sign in
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.mipmap.ic_launcher)
            .setIsSmartLockEnabled(false)
            .build()
        startActivityForResult(intent, SIGN_IN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = IdpResponse.fromResultIntent(data)
        if(requestCode == SIGN_IN_CODE){
            if(resultCode == Activity.RESULT_OK){

            }else{
                Toast.makeText(this,
                    response?.error?.message
                    , Toast.LENGTH_SHORT).show()
                showSignIn()
            }
        }
    }
    //override fun onSupportNavigateUp() = navController.navigateUp()
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutMI -> {
                //todo signout the user from the firestore auth
                Firebase.auth.signOut()
                projectViewModel.unRegisterListners()
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}