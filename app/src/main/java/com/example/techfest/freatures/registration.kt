package com.example.techfest.freatures

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techfest.R
import com.example.techfest.databinding.FragmentEdetailsBinding
import com.example.techfest.databinding.FragmentRegistrationBinding
import com.example.techfest.utility.Regsinadaptor
import com.example.techfest.utility.Regsindta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class registration : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Inflate the layout for this fragment
        binding.selfbuttonsin.setOnClickListener {
            Toast.makeText(requireContext(),"you are already in individual", Toast.LENGTH_SHORT).show()
        }
        //Toast.makeText(requireContext(),"step1", Toast.LENGTH_SHORT).show()
        binding.teamreg.setOnClickListener {
            replacefragment(teamregistration())
        }
        fetchRegisteredEvents()
        return binding.root
    }
    private fun replacefragment(fragment: Fragment) {
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


    private fun fetchRegisteredEvents() {
        Toast.makeText(requireContext(),"step2", Toast.LENGTH_SHORT).show()
        val userId = auth.currentUser?.email

        Toast.makeText(requireContext(),"step3", Toast.LENGTH_SHORT).show()
        if (userId == null) {
            Log.e("RegEvents", "User not logged in.")
            // Optionally, navigate to login screen or show a message
            return
        }

        val docRef = db.collection("eventreg").document(userId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Convert document to our data class
                    Log.d("RegEvents_DEBUG", "Document found! Raw data: ${document.data}")
                    val registeredData = document.toObject(Regsindta::class.java)
                    val eventNameList = registeredData?.regsin

                    if (!eventNameList.isNullOrEmpty()) {
                        // Setup RecyclerView
                        Toast.makeText(requireContext(),"setup event list", Toast.LENGTH_SHORT).show()
                        setupRecyclerView(eventNameList)
                    } else {
                        Toast.makeText(requireContext(),"setup event list else", Toast.LENGTH_SHORT).show()
                        Log.d("RegEvents", "No registered events found in 'regsin' array.")
                        // Optionally, show a "no events" message
                    }
                } else {
                    Toast.makeText(requireContext(),"doc not found", Toast.LENGTH_SHORT).show()
                    Log.d("RegEvents", "No such document for user: $userId")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("RegEvents", "Error getting documents: ", exception)
            }
    }



    private fun setupRecyclerView(eventNames: List<String>) {
        // 1. Convert the list to a mutable one
        val mutableEventNames = eventNames.toMutableList()

        val adapter = Regsinadaptor(
            mutableEventNames,
            onItemClicked = { eventName ->
                val bundle = bundleOf("eventname" to eventName)
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, edetails())
                transaction.commit()
            },
            // 2. Implement the logic for the long press
            onItemLongPressed = { eventName, position ->
               // deleteEvent(eventName, position)
                Toast.makeText(requireContext(),"reee", Toast.LENGTH_SHORT).show()
            }
        )
        binding.eventregsingle.layoutManager = LinearLayoutManager(requireContext())
        binding.eventregsingle.adapter = adapter
    }
//    fun updateData(newEvents: List<String>) {
//        .clear().addAll(newEvents)
//        notifyDataSetChanged() // Tells the adapter to refresh the view
//    }



}