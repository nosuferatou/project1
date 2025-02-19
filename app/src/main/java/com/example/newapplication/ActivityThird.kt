package com.example.newapplication

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.newapplication.databinding.ActivityThirdBinding

class ActivityThird : ComponentActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etwelcome = findViewById<EditText>(R.id.etwelcome)
        val name = intent.getStringExtra("inputname")
        etwelcome.setText("Welcome $name")

    }
}