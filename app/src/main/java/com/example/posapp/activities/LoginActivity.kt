package com.example.posapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.posapp.databinding.ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val editTextId = binding.etId
        val editTextPassword = binding.etPassword
        
        binding.btnLogin.setOnClickListener {
            if (editTextId.text.trim().isEmpty() || editTextPassword.text.trim().isEmpty()){
                Toast.makeText(this, "Email or Password is empty", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
        
        binding.tvSingup.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}