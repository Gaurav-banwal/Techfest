package com.example.techfest.utility

import com.google.firebase.Timestamp

data class EventData(
    val title: String = "",
    val venue: String = "",
    val startTime: Timestamp = Timestamp.now()
)
