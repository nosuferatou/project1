package com.example.newapplication

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newapplication.databinding.ActivityMainBinding
import com.example.newapplication.sharedPref.sharedPref

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: sharedPref
    private var passwordvisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = sharedPref(this)

        if (sharedPref.isLoggedIn()) {
            startActivity(Intent(this, ActivityDetail::class.java))
        }

        binding.btnlogin.setOnClickListener {
            val username = binding.txtemail.text.toString()
            val password = binding.txtpassword.text.toString()

            // memvalidasi login
            if (sharedPref.validateLogin(username, password)) {
                (sharedPref.saveLogin(username))
                binding.txtpassword.text.clear()
                binding.txtemail.text.clear()
                startActivity(Intent(this, ActivityDetail::class.java))
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }

        // membuat logika agar tombol berubah ketika diklik dan password di show
        binding.btnshow.setOnClickListener {
            passwordvisible = !passwordvisible
            if (passwordvisible) {
                binding.txtpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnshow.text = "Unshow"
            } else {
                binding.txtpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnshow.text = "Show"
            }
        }
    }
}



