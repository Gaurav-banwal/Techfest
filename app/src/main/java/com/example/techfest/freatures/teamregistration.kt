package com.example.techfest.freatures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.techfest.R
import com.example.techfest.databinding.FragmentEdetailsBinding
import com.example.techfest.databinding.FragmentTeamregistrationBinding


class teamregistration : Fragment() {
    private var _binding: FragmentTeamregistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamregistrationBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.indivisualreg.setOnClickListener {
            replacefragment(registration())
        }
        binding.teamregself.setOnClickListener {
            Toast.makeText(requireContext(),"Already in Team Registration", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
    private fun replacefragment(fragment: Fragment) {
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


}