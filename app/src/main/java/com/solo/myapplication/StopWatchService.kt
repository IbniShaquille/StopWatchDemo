package com.solo.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.Timer
import java.util.TimerTask

class StopWatchService : Service() {
    private val timer = Timer()
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(CURRENT_TIME, 0.0)
        timer.scheduleAtFixedRate(StopWatchTimerTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    companion object{
        const val CURRENT_TIME = "CURRENT_TIME"
        const val UPDATED_TIME = "UPDATED_TIME"
    }

    private inner class StopWatchTimerTask(private var time: Double): TimerTask(){
        override fun run() {
            val intent = Intent(UPDATED_TIME)
            intent.setPackage(packageName) //This is required for RECEIVER_NOT_EXPORTED
            time++
            intent.putExtra(CURRENT_TIME, time)
            sendBroadcast(intent)
        }
    }
}