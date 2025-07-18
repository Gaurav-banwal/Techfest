package com.example.techfest.utility

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName
@Keep
data class Regsindta(
    @PropertyName("indipart")
    val regsin: List<String> = emptyList()
)
