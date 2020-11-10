package cmps312.lab.lab12storagewithworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var request: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        request = OneTimeWorkRequestBuilder<MyWorker>().build()
//        request = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.MINUTES).build()

        startWorkBtn.setOnClickListener {
            WorkManager.getInstance(applicationContext)
                    .enqueue(request)
        }
    }
}