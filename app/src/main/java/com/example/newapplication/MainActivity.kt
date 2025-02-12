package com.example.newapplication

import android.content.Context
import android.view.View
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
    private lateinit var sharedPref: SharedPreferences

    private val inputemail = "admin"
    private val inputpw = "admin123"
    private val pref_name = "LoginPref"
    private val emailkey = "email"
    private val passwordkey = "password"
    private val LoggedInkey = "LoggedIn"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        if (isLoggedIn()) {
            Intent(this, ActivityDetail::class.java).also {
                startActivity(it)
            }
        }
        binding.btnlogin.setOnClickListener {
            val inputtedemail = binding.txtemail.text.toString()
            val inputtedpassword = binding.txtpassword.text.toString()

            if (inputtedemail == inputemail && inputtedpassword == inputpw) {

                saveLoginCredentials(inputtedemail, inputtedpassword)
                Intent(this, ActivityDetail::class.java).also {
                    startActivity(it)
                }
            } else {
                Toast.makeText(this, "False", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun validateCredentials(email: String, password: String):Boolean{
        return email == inputemail && password == inputpw
    }
    private fun saveLoginCredentials(email: String, password: String) {
        val editor = sharedPref.edit()
        editor.putString(emailkey, email)
        editor.putString(passwordkey, password)
        editor.putBoolean(LoggedInkey, true)
        editor.apply()

    }
    private fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(LoggedInkey, false)
    }
    fun logout() {
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}




