package cmps312.lab.lab12storagewithworkmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        showNotification("Completed the work")
        return Result.success()
    }

    private fun showNotification(completed : String){
        Log.d("TAG", "showNotification: ")
    }
}