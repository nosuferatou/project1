package com.example.newapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
import com.example.newapplication.databinding.ActivityMainBinding
import com.example.newapplication.ui.theme.NewApplicationTheme

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnlogin = findViewById<Button>(R.id.btnlogin)
        val txtemail = findViewById<EditText>(R.id.txtemail)
        val txtpassword = findViewById<EditText>(R.id.txtpassword)
        val pwerror = findViewById<TextView>(R.id.pwerror)
        val emailerror = findViewById<TextView>(R.id.emailerror)

        txtpassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.length < 8 || !password.any { it.isUpperCase() } || !password.any { it.isDigit() }) {
                    pwerror.visibility = TextView.VISIBLE
                } else {
                    pwerror.visibility = TextView.INVISIBLE
                }
            }
        })

        fun isValidEmail(email: String): Boolean {
            return if (email.isEmpty()) {
                false
            } else {
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        }

        txtemail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputemail = s.toString()
                if (!isValidEmail(inputemail)) {
                    emailerror.visibility = TextView.VISIBLE
                } else {
                    emailerror.visibility = TextView.INVISIBLE
                }
            }

        })

        btnlogin.setOnClickListener {
            Intent(this, ActivityDetail::class.java).also {
                startActivity(it)
            }
            Toast.makeText(this, "Selamat Datang!", Toast.LENGTH_LONG).show()
        }
    }
}





