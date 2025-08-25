package com.example.androidbasics

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Spinner
import android.widget.*

import com.example.androidbasics.api.ApiService
import com.example.androidbasics.api.UserCategory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast


import android.view.View
import android.widget.AdapterView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // list view
        val myListView = findViewById<ListView>(R.id.my_list_view)
        val fruits = arrayOf("Apple", "Banana", "Cherry", "Grape", "Orange")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Built-in layout for single text item
            fruits
        )
        myListView.adapter = adapter

        // 1. Get a reference to the Spinner
        val mySpinner: Spinner = findViewById(R.id.my_spinner)
        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")

        // 3. Create an ArrayAdapter using a built-in spinner layout
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, // Built-in layout for the single selected item
            items
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = spinnerAdapter

        // ===================== spinner for API data ==============================================
        // Spinner for API data
        val userSpinner: Spinner = findViewById(R.id.user_spinner)

        // Retrofit setup
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        // Call API
        api.getUsers().enqueue(object : Callback<List<UserCategory>> {
            override fun onResponse(
                call: Call<List<UserCategory>>,
                response: Response<List<UserCategory>>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body() ?: emptyList()

                    // Extract names
                    val categoryNames = categories.map { it.name }

                    // Setup adapter for spinner
                    val spinnerAdapter = ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_spinner_item,
                        categoryNames
                    )
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    userSpinner.adapter = spinnerAdapter

                    // Handle selection
                    userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val selected = categories[position]
                            Toast.makeText(
                                this@MainActivity,
                                "Selected: ${selected.name}, ID: ${selected.id}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }
                }
            }

            override fun onFailure(call: Call<List<UserCategory>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}