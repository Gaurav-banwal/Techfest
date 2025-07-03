package com.example.techfest.freatures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techfest.R
import com.example.techfest.databinding.FragmentEdetailsBinding
import com.example.techfest.databinding.FragmentEventBinding
import com.example.techfest.utility.EDetailAdapter
import com.example.techfest.utility.EventData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class edetails : Fragment() {

    private var _binding: FragmentEdetailsBinding? = null
    private val binding get() = _binding!!
    private var eventtype: String? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: EDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventtype = arguments?.getString("eventname")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEdetailsBinding.inflate(inflater, container, false)

       // Toast.makeText(requireContext(),eventtype,Toast.LENGTH_SHORT).show()


        Toast.makeText(requireContext(), eventtype ?: "No event type", Toast.LENGTH_SHORT).show()

        db = FirebaseFirestore.getInstance()
        adapter = EDetailAdapter()

        binding.edetailList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@edetails.adapter
        }

        eventtype?.let { fetchDetails(it) }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun fetchDetails(eventType: String) {
        db.collection(eventType.lowercase())
            .orderBy("startTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                val eventList = snapshot.documents.mapNotNull { doc ->

                    doc.toObject(EventData::class.java)
                }
                Toast.makeText(requireContext(),eventList.toString(),Toast.LENGTH_SHORT).show()
                adapter.submitList(eventList)
            }
    }


}