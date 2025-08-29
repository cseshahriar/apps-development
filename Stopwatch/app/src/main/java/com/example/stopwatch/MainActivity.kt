package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // input field
    private lateinit var tvTimer: TextView
    private lateinit var btnStartPause: Button
    private  lateinit var btnReset: Button

    // local var
    private var isRunning = false
    private var startTime: Long = 0
    private var pauseTime: Long = 0

    // event handler
    private val handle = Handler()

    private val stopwatchRunnable = object : Runnable {
        override fun run() {
            val elapsedTime = SystemClock.elapsedRealtime() - startTime
            val minutes = (elapsedTime / 60000).toInt()
            val seconds = ((elapsedTime % 60000) / 1000).toInt()
            val milliseconds = ((elapsedTime % 1000) / 10).toInt()

            val formattedTime = if(minutes > 0) {
                String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
            } else {
                String.format("%02d:%02d", seconds, milliseconds)
            }
            tvTimer.text = formattedTime
            handle.postDelayed(this, 50)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvTimer = findViewById(R.id.tvTimer)
        btnStartPause = findViewById(R.id.btnStartPause)
        btnReset = findViewById(R.id.btnReset)


        btnStartPause.setOnClickListener {
            if(isRunning) {
                pausStopWatch()
            } else {
                startStopWatch()
            }
        }

        btnReset.setOnClickListener {
            resetStopWatch()
        }
    }

    fun startStopWatch() {
        if(!isRunning) {
            startTime = SystemClock.elapsedRealtime() - pauseTime
            handle.post(stopwatchRunnable)
            isRunning = true
            btnStartPause.text = "Pause"
        }
    }
    fun pausStopWatch() {
        if(isRunning) {
            handle.removeCallbacks(stopwatchRunnable)
            pauseTime = SystemClock.elapsedRealtime() - startTime
            isRunning = false
            btnStartPause.text = "Start"
        }
    }

    fun resetStopWatch() {
        handle.removeCallbacks(stopwatchRunnable)
        isRunning = false
        pauseTime - 0
        tvTimer.text = "00.00"
        btnStartPause.text = "Start"
    }
}