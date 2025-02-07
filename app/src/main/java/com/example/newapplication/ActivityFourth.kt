package com.example.newapplication

import android.app.Activity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
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
import com.example.newapplication.databinding.ActivityFourthBinding
import com.example.newapplication.ui.theme.NewApplicationTheme

class ActivityFourth : Activity() {

    private lateinit var binding: ActivityFourthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val hasilpesanan = findViewById<TextView>(R.id.hasil)

        binding.btntest.setOnClickListener {
            val inputradiogroup = binding.radiogroup1.checkedRadioButtonId
            val inputtedrg = findViewById<RadioButton>(inputradiogroup)



            val hasil = "Hasil:\n" +
                    "${inputtedrg.text}" +
                    (if (binding.cb1.isChecked) "1" else "") +
                    (if (binding.cb2.isChecked) "2" else "") +
                    (if (binding.cb3.isChecked) "3" else "")

            hasilpesanan.text = hasil
        }
    }
}
