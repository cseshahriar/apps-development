package com.example.androidbasics

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.androidbasics.model.PhotoResponse
import com.example.androidbasics.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var textView: TextView

    companion object {
        private const val REFERENCE_NAME = "MyPrefsFile"
        private const val TEXT_KEY = "saveText"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_text_id)
        button = findViewById(R.id.button_id)
        textView = findViewById(R.id.text_view_id)

        // prev value save
        val settings = getSharedPreferences(REFERENCE_NAME, Context.MODE_PRIVATE)
        val saveText = settings.getString(TEXT_KEY, "")
        textView.text = saveText

        button.setOnClickListener {
            val inputText = editText.text.toString()
            textView.text = inputText

            val edit = settings.edit()
            edit.putString(TEXT_KEY, inputText)
            edit.apply()
        }
    }

}
