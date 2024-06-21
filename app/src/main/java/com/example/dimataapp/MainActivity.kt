package com.example.dimataapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dimataapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ticketAdapter: TicketAdapter
    private var tickets: MutableList<Ticket> = mutableListOf()
    private var filteredTickets: List<Ticket> = mutableListOf()
    private var originalTickets: MutableList<Ticket> = mutableListOf()
    private var nextTicketId = 4 // Mulai dari ID yang sesuai dengan data dummy

    companion object {
        private const val ADD_TICKET_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnOpenTicket.setOnClickListener {
            binding.btnOpenTicket.alpha = 0.5f // Set alpha to 50% to show button press effect
            binding.btnOpenTicket.postDelayed({
                binding.btnOpenTicket.alpha = 1.0f // Restore alpha to 100% after 200ms
                val intent = Intent(this@MainActivity, AddTicketActivity::class.java)
                startActivityForResult(intent, ADD_TICKET_REQUEST_CODE)
            }, 200) // Delay in milliseconds
        }

        // Setup SearchView
        val searchView: SearchView = binding.search
        searchView.queryHint = "Search ticket here"
        searchView.isIconifiedByDefault = false
        setupSearchView(searchView)

        // Setup Spinner
        val spinner: Spinner = binding.spinnerFilter
        val items = listOf("Filter", "Open", "Closed")
        val adapter = object : ArrayAdapter<String>(this, R.layout.custom_spinner_item, items) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                view.background = ContextCompat.getDrawable(context, R.drawable.bg_form)
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)

        // Dummy data
        tickets.addAll(
            listOf(
                Ticket(1, "open", "John Doe", "john.doe@example.com", "Tidak bisa login di prochain", "Last 5 Minutes", "https://png.pngtree.com/png-vector/20191101/ourmid/pngtree-cartoon-color-simple-male-avatar-png-image_1934459.jpg", "Pasek Oksana"),
                Ticket(2, "closed", "Jane Smith", "jane.smith@example.com", "Tidak bisa login di prochain", "Last 5 Minutes", "https://png.pngtree.com/png-vector/20191101/ourmid/pngtree-cartoon-color-simple-male-avatar-png-image_1934459.jpg", "Pasek Oksana"),
                Ticket(3, "closed", "Michael Johnson", "michael.johnson@example.com", "Tidak bisa login di prochain", "Last 5 Minutes", "https://png.pngtree.com/png-vector/20191101/ourmid/pngtree-cartoon-color-simple-male-avatar-png-image_1934459.jpg", "Pasek Oksana")
            )
        )

        // Initialize adapter with all tickets
        ticketAdapter = TicketAdapter(tickets)
        // Set LinearLayoutManager with reverseLayout=true to display items in reverse order
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvTicket.layoutManager = layoutManager
        binding.rvTicket.adapter = ticketAdapter

        // Handle spinner item selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedFilter = items[position]
                filterTickets(selectedFilter)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ticketAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun filterTickets(filter: String) {
        filteredTickets = when (filter) {
            "Open" -> tickets.filter { it.status.equals("open", ignoreCase = true) }
            "Closed" -> tickets.filter { it.status.equals("closed", ignoreCase = true) }
            else -> tickets
        }
        ticketAdapter.submitList(filteredTickets)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TICKET_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<Ticket>("new_ticket")?.let { newTicket ->
                // Assign a new unique ID to the new ticket
                val newId = nextTicketId++
                val ticketWithId = newTicket.copy(id = newId)

                // Use default image URL if imageUrl is empty
                if (ticketWithId.imageUrl == "") {
                    ticketWithId.copy(imageUrl = "https://png.pngtree.com/png-vector/20191101/ourmid/pngtree-cartoon-color-simple-male-avatar-png-image_1934459.jpg")
                }

                // Add ticket to the beginning of the list
                tickets.add(0, ticketWithId)
                ticketAdapter.notifyItemInserted(0)

            }
        }
    }
}

