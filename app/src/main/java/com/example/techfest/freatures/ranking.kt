package com.example.techfest.freatures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.techfest.R
import com.example.techfest.databinding.FragmentEventBinding
import com.example.techfest.databinding.FragmentRankingBinding
import com.google.firebase.firestore.FirebaseFirestore


class ranking : Fragment() {

    private lateinit var db: FirebaseFirestore
    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRankingBinding.inflate(inflater,container,false)
        db = FirebaseFirestore.getInstance()
        rankset()
        return binding.root
    }


    fun rankset( ){
        db.collection("event")
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val eventName = doc.getString("name") ?: continue
                    val button = Button(requireContext()).apply {
                        setBackgroundColor(android.graphics.Color.TRANSPARENT) // ✅ transparent background
                        setTextColor(android.graphics.Color.WHITE)
                        text = eventName
                        setOnClickListener {


                            val listwin = doc.get("winners") as? List<String> ?: null
                            binding.rankFirst.text = listwin?.get(0).toString()
                            binding.rankSec.text = listwin?.get(1).toString()
                            binding.rankThird.text = listwin?.get(2).toString()

                        }
                    }
                    binding.buttonContainer.addView(button)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to load events", Toast.LENGTH_SHORT).show()
            }


    }



}


