package com.example.newapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
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
import com.example.newapplication.databinding.ActivityDetailBinding
import com.example.newapplication.ui.theme.NewApplicationTheme

class ActivityDetail : ComponentActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        val btnclick = findViewById<Button>(R.id.btnclick)
        val etname = findViewById<EditText>(R.id.etname)

        btnclick.setOnClickListener {
            Intent(this, ActivityThird::class.java).also{
                startActivity(it)

                val name = etname.text.toString()
                Intent(this, ActivityThird::class.java).also {
                    it.putExtra("inputname", name)
                    startActivity(it)
                }

            }
        }
        binding.btnlogout.setOnClickListener {
            logout()
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

    }
    private fun logout() {
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}
