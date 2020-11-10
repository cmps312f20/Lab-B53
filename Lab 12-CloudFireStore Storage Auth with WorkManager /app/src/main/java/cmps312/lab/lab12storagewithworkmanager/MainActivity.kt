package cmps312.lab.lab12storagewithworkmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var request: WorkRequest
    val SIGN_IN_CODE = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Firebase.auth.addAuthStateListener {
            if (it.currentUser == null) {
                Toast.makeText(applicationContext, "You are not logged in", Toast.LENGTH_SHORT).show()
                showSignInActivity()
            } else {
                Toast.makeText(this,
                        "Welcome ${it.currentUser!!.displayName}", Toast.LENGTH_SHORT).show()
            }
        }

        signOutBtn.setOnClickListener {
            Firebase.auth.signOut()
        }

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

        request = OneTimeWorkRequestBuilder<MyWorker>()
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(request.id)
                .observe(this) {
                    workStatusTv.append("${it.state.name}\n")
                }
//        request = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.MINUTES).build()

        startWorkBtn.setOnClickListener {
            WorkManager.getInstance(applicationContext)
                    .enqueue(request)
        }

        openCloudFireStorage.setOnClickListener {
            val intent = Intent(this, CloudStorageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showSignInActivity() {
        val providers = listOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_baseline_account_circle_24)
                .setIsSmartLockEnabled(false)
                .build()
        startActivityForResult(intent, SIGN_IN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(this, "I am called", Toast.LENGTH_SHORT).show()
        if (requestCode == SIGN_IN_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Successfully signed In", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Unable to sign In", Toast.LENGTH_SHORT).show()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}