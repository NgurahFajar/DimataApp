package com.example.dimataapp


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    val id: Int,
    val status: String,
    val name: String,
    val email: String,
    val subject: String,
    val lastMessage: String,
    val imageUrl: String = "https://png.pngtree.com/png-vector/20191101/ourmid/pngtree-cartoon-color-simple-male-avatar-png-image_1934459.jpg", // Default image URL
    val agent: String
) : Parcelable

