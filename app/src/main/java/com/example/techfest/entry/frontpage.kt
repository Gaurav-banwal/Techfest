package com.example.techfest.entry

import android.content.Intent
import android.os.Bundle
import android.view.View
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
        val intent = Intent(this,MainActivity::class.java)
        binding.choice.visibility = View.VISIBLE
        binding.guest.setOnClickListener{

            intent.putExtra("type_of_guest","guest")


            startActivity(intent)
        }

        binding.patricipant.setOnClickListener{

            intent.putExtra("type_of_guest","participant")

                binding.choice.visibility = View.GONE
                binding.login.visibility = View.VISIBLE

            binding.forgetpass.setOnClickListener{
                binding.login.visibility = View.GONE
                binding.forlayout.visibility = View.VISIBLE
            }
            binding.submit.setOnClickListener{
                startActivity(intent)
            }

        }
        binding.formail.setOnClickListener{
            startActivity(intent)
        }
        binding.backl.setOnClickListener{
            binding.login.visibility= View.GONE
            binding.choice.visibility = View.VISIBLE
        }
        binding.backf.setOnClickListener {
            binding.forlayout.visibility = View.GONE
            binding.login.visibility = View.VISIBLE
        }
        binding.registerpass.setOnClickListener{
            binding.login.visibility = View.GONE
            binding.createaccount.visibility = View.VISIBLE

            binding.backcr.setOnClickListener {
                binding.createaccount.visibility = View.GONE
                binding.login.visibility = View.VISIBLE
            }
            binding.submitcr.setOnClickListener{
                startActivity(intent)
            }
        }





    }
}