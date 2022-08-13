package com.example.posapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.posapp.databinding.ActivityLoginBinding
import com.example.posapp.databinding.ActivitySingupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val editTextId = binding.etId
        val editTextPassword = binding.etPassword
        val editTextConfirmPassword = binding.etPasswordConfirm
        
        binding.btnSignup.setOnClickListener {
            if (editTextId.text.trim().isEmpty() || editTextPassword.text.trim().isEmpty() || editTextConfirmPassword.text.trim().isEmpty()){
                Toast.makeText(this, "Email or Password is empty", Toast.LENGTH_SHORT).show()
            }
            else if(editTextPassword.text.toString() != editTextConfirmPassword.text.toString()) {
                Toast.makeText(this, "Confirm password is wrong", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}