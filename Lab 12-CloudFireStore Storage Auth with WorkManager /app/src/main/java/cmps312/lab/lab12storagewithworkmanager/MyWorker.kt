package cmps312.lab.lab12storagewithworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class MyWorker(context: Context, workerParams: WorkerParameters)
    : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope{
        val jobs = (0..10).map {
            async { downloadSomethingBig(it) }
        }
//        jobs.awaitAll()

        Result.success()
    }

    private suspend fun downloadSomethingBig(num : Int){
        delay(1000)
        showNotification("Completed the background work $num")
    }
    private fun showNotification(completed : String){
        val manager : NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val channel = NotificationChannel("Woker1", "Worker Manager",
                NotificationManager.IMPORTANCE_DEFAULT)

            val notification = NotificationCompat.Builder(applicationContext, "Woker1")
                .setContentTitle("Work Manager Notification")
                .setContentText(completed)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

            manager.createNotificationChannel(channel)
            manager.notify(1, notification)
        }

    }
}