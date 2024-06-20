package com.example.dimataapp


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dimataapp.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            // Set up onFocusChangeListener for edtEmail
            edtEmail.onFocusChangeListener = TextInputFocusChangeListener(binding.lyEdtEmail)

            // Set up onFocusChangeListener for edtPassword
            edtPassword.onFocusChangeListener = TextInputFocusChangeListener(binding.lyEdtPassword)

            // btn sign in
            btnSignIn.setOnClickListener {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            }
        }

    private class TextInputFocusChangeListener(private val textInputLayout: TextInputLayout) :
         View.OnFocusChangeListener {
        override fun onFocusChange(view: View, hasFocus: Boolean) {
            if (view is TextInputEditText) {
                if (hasFocus) {
                    textInputLayout.hint = ""
                } else {
                    textInputLayout.hint = "Place Holder Here"
                }
            }
        }
    }
}


