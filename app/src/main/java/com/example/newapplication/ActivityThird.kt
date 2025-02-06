package com.example.newapplication

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newapplication.databinding.ActivityThirdBinding
import com.example.newapplication.ui.theme.NewApplicationTheme

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