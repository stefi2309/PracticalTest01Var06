package ro.pub.cs.systems.eim.practicaltest01var06

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.Date
import java.util.Objects

class PracticalTest01Var06Service : Service() {

    private inner class ProcessingThread(
        private val context: Context,
        private val received: String?
    ) : Thread() {

        override fun run() {
            Log.d(
                "TAG",
                "Victory, "
            )
            sendMessage()
        }


        private fun sendMessage() {
            val currentDate = Date(System.currentTimeMillis()).toString()

            sleepForAWhile()

            val intent = Intent()
            intent.action = "ro.pub.cs.systems.eim.practicaltest01var06.INTENT"
            intent.putExtra(
                "SOMETHING",
                "$currentDate $received"
            )

            val packageName = "ro.pub.cs.systems.eim.practicaltest01var06.startedserviceactivity"
            intent.setPackage(packageName)

            Log.d(
                "TAG",
                "Sending broadcast with action: ${intent.action} to package: $packageName"
            )

            context.sendBroadcast(intent)
        }


        private fun sleepForAWhile() {
            try {
                Thread.sleep(2000)
            } catch (interruptedException: InterruptedException) {
                Log.e("TAG", interruptedException.message ?: "InterruptedException occurred")
                interruptedException.printStackTrace()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate() method was invoked")

        val CHANNEL_ID = "my_channel_01"

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        (Objects.requireNonNull<Any?>(getSystemService(NOTIFICATION_SERVICE)) as NotificationManager)
            .createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("")
            .setContentText("")
            .build()

        startForeground(1, notification)
    }


    override fun onBind(intent: Intent): IBinder? {
        Log.d("TAG", "onBind() method was invoked")
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d("TAG", "onUnbind() method was invoked")
        return true
    }

    override fun onRebind(intent: Intent) {
        Log.d("TAG", "onRebind() method was invoked")
    }

    override fun onDestroy() {
        Log.d("TAG", "onDestroy() method was invoked")
        super.onDestroy()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "onStartCommand() method was invoked")

        val score = intent?.getStringExtra("score")

        val processingThread = ProcessingThread(this, score)
        processingThread.start()

        return START_REDELIVER_INTENT
    }
}
