package com.example.daydo

data class Challenge(
    var name: String,
    var description: String,
    var date: String,
    var isCompleted: Boolean = false
)
