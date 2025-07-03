package com.example.techfest.freatures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.techfest.R
import com.example.techfest.databinding.FragmentEdetailsBinding
import com.example.techfest.databinding.FragmentEventBinding


class edetails : Fragment() {

    private var _binding: FragmentEdetailsBinding? = null
    private val binding get() = _binding!!
    private var username: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = arguments?.getString("eventname")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEdetailsBinding.inflate(inflater, container, false)

        Toast.makeText(requireContext(),username,Toast.LENGTH_SHORT).show()

        

        // Inflate the layout for this fragment
        return binding.root
    }


}