package com.example.techfest.utility

import com.google.firebase.Timestamp

data class eventdata(
    val title: String = "",
    val startTime: Timestamp = Timestamp.now(),
    val venue:String = ""
)   


