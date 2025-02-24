package com.example.newapplication


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import com.example.newapplication.databinding.ActivityDetailBinding
import com.example.newapplication.fragment.FragmentHome
import com.example.newapplication.fragment.FragmentProfile
import com.example.newapplication.fragment.FragmentSearch
import com.example.newapplication.sharedPref.sharedPref

class ActivityDetail : AppCompatActivity() {
    private lateinit var sharedPref: sharedPref
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = sharedPref(this)

        // load fragment menggunakan fungsi loadFragment(Fragment yang dituju())
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

    // fungsi loadFragment menggunakan supportFragmentManager.beginTransaction()
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
