package com.example.dimataapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dimataapp.databinding.ActivityAddTicketBinding
import com.google.android.material.snackbar.Snackbar


class AddTicketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTicketBinding.inflate(layoutInflater)
        // set theme before calling setContentView!
        setTheme(R.style.Theme_DimataApp);
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@AddTicketActivity, MainActivity::class.java))
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            var isAllFieldValid = true
            binding.apply {
                if (edtEmail.text.isNullOrEmpty() || !edtEmail.error.isNullOrEmpty())
                    isAllFieldValid = false
                if (edtSubject.text.isNullOrEmpty() || !edtSubject.error.isNullOrEmpty())
                    isAllFieldValid = false
                if (edtName.text.isNullOrEmpty() || !edtName.error.isNullOrEmpty())
                    isAllFieldValid = false
                if (edtMessage.text.isNullOrEmpty() || !edtMessage.error.isNullOrEmpty())
                    isAllFieldValid = false
                if (spinnerTeam.selectedItemPosition == 0)
                    isAllFieldValid = false
                if (spinnerAgent.selectedItemPosition == 0)
                    isAllFieldValid = false
                if (spinnerPriority.selectedItemPosition == 0)
                    isAllFieldValid = false
                if (spinnerStatus.selectedItemPosition == 0)
                    isAllFieldValid = false

                if (isAllFieldValid) {
                    val newTicket = Ticket(
                        id = 0, // Temporary ID, will be set in MainActivity
                        status = spinnerStatus.selectedItem.toString(),
                        name = edtName.text.toString(),
                        email = edtEmail.text.toString(),
                        subject = edtSubject.text.toString(),
                        lastMessage = edtMessage.text.toString(),
                        imageUrl = "https://png.pngtree.com/png-vector/20191101/ourmid/pngtree-cartoon-color-simple-male-avatar-png-image_1934459.jpg", // Provide a default or selected image URL
                        agent = spinnerAgent.selectedItem.toString()
                    )

                    val intent = Intent().apply {
                        putExtra("new_ticket", newTicket)
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }else{
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

        // Setup Spinner Team
        val spinnerTeam: Spinner = binding.spinnerTeam
        val itemsTeam = listOf("Selected Team", "All Team (default team)", "Dimata", "Apple")
        val adapterTeam = object : ArrayAdapter<String>(this, R.layout.custom_spinner_item, itemsTeam) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                view.background = ContextCompat.getDrawable(context, R.drawable.bg_form)
                return view
            }
        }
        adapterTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTeam.adapter = adapterTeam
        spinnerTeam.setSelection(0)

        // Setup Spinner Agent
        val spinnerAgent: Spinner = binding.spinnerAgent
        val itemsAgent = listOf("Selected Agent", "Vincky Sedana", "Philip Steven")
        val adapterAgent = object : ArrayAdapter<String>(this, R.layout.custom_spinner_item, itemsAgent) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                view.background = ContextCompat.getDrawable(context, R.drawable.bg_form)
                return view
            }
        }
        adapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAgent.adapter = adapterAgent
        spinnerAgent.setSelection(0)

        // Setup Spinner Priority
        val spinnerPriority: Spinner = binding.spinnerPriority
        val itemsPriority = listOf("Selected Priority", "Low", "Medium", "High")
        val adapterPriority = object : ArrayAdapter<String>(this, R.layout.custom_spinner_item, itemsPriority) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                view.background = ContextCompat.getDrawable(context, R.drawable.bg_form)
                return view
            }
        }
        adapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapterPriority
        spinnerPriority.setSelection(0)

        // Setup Spinner Status
        val spinnerStatus: Spinner = binding.spinnerStatus
        val itemsStatus = listOf("Selected Status", "Plan", "In Progress", "Pending")
        val adapterStatus = object : ArrayAdapter<String>(this, R.layout.custom_spinner_item, itemsStatus) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                view.background = ContextCompat.getDrawable(context, R.drawable.bg_form)
                return view
            }
        }
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapterStatus
        spinnerStatus.setSelection(0)
    }
}