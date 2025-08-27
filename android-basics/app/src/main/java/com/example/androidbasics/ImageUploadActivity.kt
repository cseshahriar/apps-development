package com.example.androidbasics

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.ImageView
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

class ImageUploadActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var selectUploadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        imageView = findViewById(R.id.imageView)
//        selectUploadButton = findViewById(R.id.uploadButton)

        checkPermission()

        // One button: select + upload
        selectUploadButton.setOnClickListener {
            openGallery()
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 100
                )
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100
                )
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedUri: Uri? = result.data?.data
                selectedUri?.let { uri ->
                    // Show selected image
                    Glide.with(this).load(uri).into(imageView)

                    // Upload immediately
                    uploadImage(uri)
                }
            }
        }

    private fun uploadImage(uri: Uri) {
        val file = createFileFromUri(uri)

        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val title = RequestBody.create("text/plain".toMediaTypeOrNull(), "My Test Photo")

        RetrofitClient.instance.uploadPhoto(body, title)
            .enqueue(object : Callback<PhotoResponse> {
                override fun onResponse(
                    call: Call<PhotoResponse>,
                    response: Response<PhotoResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("UPLOAD", "Success: ${response.body()}")
                        // keep showing selected image (local)
                    } else {
                        Log.e("UPLOAD", "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                    Log.e("UPLOAD", "Failure: ${t.message}")
                }
            })
    }

    private fun createFileFromUri(uri: Uri): File {
        val inputStream = contentResolver.openInputStream(uri)!!
        val file = File(cacheDir, getFileName(uri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        return file
    }

    private fun getFileName(uri: Uri): String {
        var name = "temp_file"
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex >= 0) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }
}
