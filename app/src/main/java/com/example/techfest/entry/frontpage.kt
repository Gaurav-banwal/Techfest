package com.example.techfest.entry

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techfest.MainActivity
import com.example.techfest.R
import com.example.techfest.databinding.ActivityFrontpageBinding

class frontpage : AppCompatActivity() {
    private lateinit var binding: ActivityFrontpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFrontpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.guest.setOnClickListener{
           val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("type_of_guest","guest")
            startActivity(intent)
        }

        binding.patricipant.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("type_of_guest","participant")
            startActivity(intent)
        }




    }
}