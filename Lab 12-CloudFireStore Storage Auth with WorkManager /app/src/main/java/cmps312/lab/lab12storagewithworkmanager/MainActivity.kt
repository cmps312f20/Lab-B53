package cmps312.lab.lab12storagewithworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var request: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

        request = OneTimeWorkRequestBuilder<MyWorker>()
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(request.id)
                .observe(this){
                    workStatusTv.append("${it.state.name}\n")
                }
//        request = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.MINUTES).build()

        startWorkBtn.setOnClickListener {
            WorkManager.getInstance(applicationContext)
                    .enqueue(request)
        }
    }
}