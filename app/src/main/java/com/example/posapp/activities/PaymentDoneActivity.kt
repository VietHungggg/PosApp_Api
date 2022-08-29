package com.example.posapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posapp.R
import com.example.posapp.databinding.ActivityPaymentDoneBinding
import com.example.posapp.fragments.HomeFragment

class PaymentDoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentDoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHomeComback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}