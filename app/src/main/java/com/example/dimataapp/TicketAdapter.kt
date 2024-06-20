package com.example.dimataapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Locale

class TicketAdapter(private var originalTickets: List<Ticket>) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>(), Filterable {

    private var filteredTickets: List<Ticket> = originalTickets.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = filteredTickets[position]
        holder.bind(ticket)
    }

    override fun getItemCount(): Int = filteredTickets.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                if (constraint.isNullOrBlank()) {
                    results.values = originalTickets // Show all items if search query is empty
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                    val filteredList = originalTickets.filter { ticket ->
                        ticket.name.toLowerCase(Locale.getDefault()).contains(filterPattern) ||
                                ticket.email.toLowerCase(Locale.getDefault()).contains(filterPattern) ||
                                ticket.subject.toLowerCase(Locale.getDefault()).contains(filterPattern) ||
                                ticket.lastMessage.toLowerCase(Locale.getDefault()).contains(filterPattern)
                    }
                    results.values = filteredList
                }
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredTickets = results?.values as? List<Ticket> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    fun submitList(list: List<Ticket>?) {
        originalTickets = list ?: emptyList()
        filteredTickets = originalTickets // Reset filtered list to match new original list
        notifyDataSetChanged()
    }

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_image)
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        private val status: ImageView = itemView.findViewById(R.id.img_status)
        private val emailTextView: TextView = itemView.findViewById(R.id.tv_email)
        private val subjectTextView: TextView = itemView.findViewById(R.id.tv_subject)
        private val lastMessage: TextView = itemView.findViewById(R.id.tv_last_message)
        private val agent: TextView = itemView.findViewById(R.id.tv_agent)

        fun bind(ticket: Ticket) {
            Glide.with(itemView.context)
                .load(ticket.imageUrl)
                .into(imageView)
            nameTextView.text = ticket.name
            emailTextView.text = ticket.email
            subjectTextView.text = ticket.subject
            lastMessage.text = ticket.lastMessage

            // Set status image based on ticket status
            if (ticket.status.equals("open", ignoreCase = true)) {
                status.setImageResource(R.drawable.open_status)
            } else {
                status.setImageResource(R.drawable.closed_status)
            }

            agent.text = ticket.agent
        }
    }
}

