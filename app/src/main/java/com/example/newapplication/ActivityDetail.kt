package com.example.newapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.example.newapplication.databinding.ActivityDetailBinding
import com.example.newapplication.fragment.FragmentHome
import com.example.newapplication.fragment.FragmentProfile
import com.example.newapplication.fragment.FragmentSearch
import com.example.newapplication.sharedPref.sharedPref
import com.example.newapplication.ui.theme.NewApplicationTheme

class ActivityDetail : AppCompatActivity() {
    private lateinit var sharedPref: sharedPref


    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = sharedPref(this)

        loadFragment(FragmentHome())

        binding.navbottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(FragmentHome())
                }
                R.id.nav_search -> {
                    loadFragment(FragmentSearch())
                }
                R.id.nav_profile -> {
                    loadFragment(FragmentProfile())
                }
            }
            true
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
