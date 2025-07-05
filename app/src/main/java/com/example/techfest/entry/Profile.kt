package com.example.techfest.entry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techfest.R
import com.example.techfest.databinding.ActivityProfileBinding
import com.example.techfest.utility.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject


class Profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore
    private lateinit var currentuser:FirebaseUser

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
         auth = FirebaseAuth.getInstance()
         db = FirebaseFirestore.getInstance()
         currentuser = auth.currentUser!!
     // Toast.makeText(this,currentuser.email,Toast.LENGTH_SHORT).show()
           val usermail = intent.getStringExtra("useremail")
         binding.profEmail.text = currentuser.email


        fetchUserProfile { profile ->
            if(profile!=null)
            {
                binding.profName.setText( profile.username)
                binding.profPhoneno.setText(profile.phoneNumber)
                binding.profCollege.setText(profile.collegeName)
            }else
                Toast.makeText(this,"empty profile",Toast.LENGTH_SHORT).show()

        }
//        createprofile(
//            name = "jacon",
//            email = "jack@gmail.com",
//            phno = "8989898899",
//            colname = "MOIT"
//        )


        binding.profUpdate.setOnClickListener{

            createprofile(
                name = binding.profName.text.toString(),
                email = usermail.toString(),
                phno = binding.profPhoneno.text.toString(),
                colname = binding.profCollege.text.toString()
            )



        }



    }

    fun createprofile(name:String,email:String,phno:String,colname:String)
    {

        if (currentuser != null)
        {
            val email = currentuser.email ?: return
            val userProfile = UserProfile(
                username = name,
                email = email,
                phoneNumber = phno,
                collegeName = colname
            )
            db.collection("users").document(email)
                .set(userProfile)
                .addOnSuccessListener {
                    println("User profile created successfully.")
                }
                .addOnFailureListener { e ->
                    println(" Error creating user profile: ${e.message}")
                }
        }

    }
    fun fetchUserProfile(onResult: (UserProfile?) -> Unit) {


        if (currentuser != null) {
            val email = currentuser.email ?: return

            db.collection("users").document(email)

                .get()
                .addOnSuccessListener { document ->
                    Toast.makeText(this, email, Toast.LENGTH_SHORT).show()

                    if (document.exists()) {
                       Toast.makeText(this, "Document: ${document.data}",Toast.LENGTH_SHORT).show()
                        val userProfile = document.toObject<UserProfile>()
                        onResult(userProfile)
                    } else {
                        println(" No profile found for this user.")
                        onResult(null)
                    }
                }
                .addOnFailureListener { e ->
                    println(" Error fetching user profile: ${e.message}")
                    onResult(null)
                }
        } else {
            Toast.makeText(this,"⚠ No user is currently logged in.",Toast.LENGTH_SHORT).show()
            onResult(null)
        }
    }




}