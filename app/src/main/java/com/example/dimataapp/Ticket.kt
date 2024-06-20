package com.example.dimataapp

data class Ticket(
    val id: Int,
    val status: String,
    val name: String,
    val email: String,
    val subject: String,
    val lastMessage: String,
    val imageUrl: String // URL gambar untuk ImageView di dalam item
)
