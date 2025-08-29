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
    // UI elements
    private lateinit var tvTimer: TextView
    private lateinit var btnStartPause: Button
    private  lateinit var btnReset: Button

    // Stopwatch state variables
    private var isRunning = false
    private var startTime: Long = 0
    private var pauseTime: Long = 0

    // Handler → schedules and runs tasks on UI thread repeatedly
    private val handle = Handler()

    // Runnable → logic that updates the timer continuously
    private val stopwatchRunnable = object : Runnable {
        override fun run() {
            // Calculate elapsed time (now - start)
            val elapsedTime = SystemClock.elapsedRealtime() - startTime

            // Break elapsed time into minutes, seconds, milliseconds
            val minutes = (elapsedTime / 60000).toInt()
            val seconds = ((elapsedTime % 60000) / 1000).toInt()
            val milliseconds = ((elapsedTime % 1000) / 10).toInt()

            // Format output: "MM:SS:MS" if minutes > 0 else "SS:MS"
            val formattedTime = if(minutes > 0) {
                String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
            } else {
                String.format("%02d:%02d", seconds, milliseconds)
            }

            // Update text on screen
            tvTimer.text = formattedTime

            // Run again after 50ms → creates a loop
            handle.postDelayed(this, 50)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Connect UI elements with XML IDs
        tvTimer = findViewById(R.id.tvTimer)
        btnStartPause = findViewById(R.id.btnStartPause)
        btnReset = findViewById(R.id.btnReset)


        // Start/Pause button toggles stopwatch
        btnStartPause.setOnClickListener {
            if(isRunning) {
                pausStopWatch()
            } else {
                startStopWatch()
            }
        }

        // Reset button resets stopwatch
        btnReset.setOnClickListener {
            resetStopWatch()
        }
    }

    // Start or resume stopwatch
    fun startStopWatch() {
        if(!isRunning) {
            // Adjust start time so stopwatch resumes correctly after pause
            startTime = SystemClock.elapsedRealtime() - pauseTime
            // Start updating UI
            handle.post(stopwatchRunnable)
            isRunning = true
            // Change button text
            btnStartPause.text = "Pause"
        }
    }
    fun pausStopWatch() {
        if(isRunning) {
            // Stop UI updates
            handle.removeCallbacks(stopwatchRunnable)
            // Save elapsed time so we can resume
            pauseTime = SystemClock.elapsedRealtime() - startTime
            isRunning = false
            // Change button text
            btnStartPause.text = "Start"
        }
    }

    // Reset stopwatch
    fun resetStopWatch() {
        // Stop runnable
        handle.removeCallbacks(stopwatchRunnable)
        // Reset state
        isRunning = false
        pauseTime = 0
        // Reset UI
        tvTimer.text = "00.00"
        btnStartPause.text = "Start"
    }
}