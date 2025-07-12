package com.example.techfest.freatures

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.techfest.R
import com.example.techfest.databinding.FragmentEventBinding
import com.example.techfest.utility.EventAdapter
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.techfest.utility.Event


class event : Fragment() {
    private lateinit var adapter: EventAdapter

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding.inflate(inflater, container, false)



        fetchEventsFromFirestore()
        return binding.root
    }


    private fun fetchEventsFromFirestore() {
        binding.progressBar.visibility = View.VISIBLE
        FirebaseFirestore.getInstance().collection("event")
            .get()
            .addOnSuccessListener { snapshot ->


                Log.d("Firestore", "Documents found: ${snapshot.size()}")


                val eventList = mutableListOf<Event>()
                for (doc in snapshot.documents) {

                    val eventob = doc.toObject(Event::class.java)
                    //Toast.makeText(requireContext(), "entered", Toast.LENGTH_SHORT).show()
                    if (eventob != null) {
                        // 🔁 Match event name to local image
                        eventob.imageResId = when (eventob.name.lowercase()) {
                            "techquiz" -> R.drawable.techquiz
                            "hackathon" -> R.drawable.hackathon
                            "concert" -> R.drawable.concert
                            "robowar" -> R.drawable.robowar
                            else -> R.drawable.placeholder // default image
                        }
                        Log.d("Firestore", "Parsed event: $eventob")
                        eventList.add(eventob)
                    }
                }

                adapter = EventAdapter(eventList) { clevent ->
                    val bundle = Bundle()
                    bundle.putString("eventname", clevent.name.toString())

                    val fragment = edetails()
                    fragment.arguments = bundle

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()

                }
                binding.eventRecycleView.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.eventRecycleView.adapter = adapter
                binding.progressBar.visibility = View.GONE
                Log.d("Adapter", "Item count: ${adapter.itemCount}")
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error loading events: ${exception.message}", exception)
                Toast.makeText(requireContext(), "Failed to load events", Toast.LENGTH_SHORT).show()
            }

    }


}