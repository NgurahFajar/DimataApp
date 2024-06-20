package com.example.dimataapp


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dimataapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setErrorValidateForm()

        binding.apply {

            // btn sign in
            btnSignIn.setOnClickListener {
                doUserLogin()
            }
        }


    }

    private fun doUserLogin() {
        var isAllFieldValid = true

        binding.apply {
            if (edtEmail.text.isNullOrEmpty() || !edtEmail.error.isNullOrEmpty())
                isAllFieldValid = false
            if (edtPassword.text.isNullOrEmpty() || !edtPassword.error.isNullOrEmpty())
                isAllFieldValid = false

            if (isAllFieldValid) {
                binding.apply {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            } else {
                Snackbar.make(
                    binding.root,
                    "There is some field that empty or not valid, please check again your data",
                    Snackbar.LENGTH_SHORT
                ).apply {
                    view.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red
                        )
                    ) // Set background color to red
                    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                        .setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        ) // Set text color to white
                }.show()
            }

        }
    }

        private fun setErrorValidateForm() {
            binding.apply {
                // Set listener change for email edit text
                edtEmail.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {}

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                            edtEmail.error = "Must be email format"
                        }
                    }
                })

                //Set listener change for password
                edtPassword.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        val regexPassword = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$".toRegex()
                        if (s.isNullOrEmpty() || !regexPassword.containsMatchIn(s)) {
                            edtPassword.error = "Minimal 8 character & 1 number"
                            lyEdtPassword.endIconMode = TextInputLayout.END_ICON_NONE
                        } else {
                            lyEdtPassword.error = null
                            lyEdtPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                        }
                    }
                })
            }
        }
    }



