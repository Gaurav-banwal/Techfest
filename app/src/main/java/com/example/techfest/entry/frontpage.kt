package com.example.techfest.entry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techfest.MainActivity
import com.example.techfest.R
import com.example.techfest.databinding.ActivityFrontpageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class frontpage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
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
        auth = FirebaseAuth.getInstance()
        val intent = Intent(this, MainActivity::class.java)
        binding.choice.visibility = View.VISIBLE
        binding.guest.setOnClickListener {

            intent.putExtra("type_of_guest", "guest")


            startActivity(intent)
        }


        //here login page starts
        binding.patricipant.setOnClickListener {
            intent.putExtra("type_of_guest", "participant")
            binding.choice.visibility = View.GONE
            binding.login.visibility = View.VISIBLE
            binding.backl.setOnClickListener {
                binding.login.visibility = View.GONE
                binding.choice.visibility = View.VISIBLE
            }
            binding.submit.setOnClickListener {

                val emailc = binding.emaill.text.toString().trim()
                val passwordc = binding.passwordl.text.toString().trim()

                if (emailc.isBlank() || passwordc.isBlank()) {

                    Toast.makeText(this, "Blank email or password ", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener

                } else {

                    auth.signInWithEmailAndPassword(emailc, passwordc)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {


                                val user = auth.currentUser

                                //doing email verification
                                if (user != null && user.isEmailVerified) {
                                    // Allow login
                                    Toast.makeText(
                                        applicationContext,
                                        "Login successful!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(intent)
                                    //Toast.makeText(this, "yes", Toast.LENGTH_LONG).show()
                                    Log.d(
                                        "Auth",
                                        "Login successful: ${auth.currentUser?.uid}"
                                    )
                                } else {

                                    user?.reload()?.addOnCompleteListener { reload ->


                                        if (reload.isSuccessful) {
                                            // Restrict unverified users
                                            if (user != null && !user.isEmailVerified) {
                                                user?.sendEmailVerification()
                                                    ?.addOnCompleteListener { taske ->
                                                        if (taske.isSuccessful) {
                                                            Toast.makeText(
                                                                applicationContext,
                                                                "Verification email resent!",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            Log.d(
                                                                "EmailDebug",
                                                                "Verification email sent to: ${user.email}"
                                                            )
                                                        } else {
                                                            Log.e(
                                                                "EmailVerification",
                                                                "Failed to resend verification email.",
                                                                taske.exception
                                                            )
                                                        }
                                                    }
                                            }//user email verification ends
                                        } else {
                                            Toast.makeText(this, "reload failed", Toast.LENGTH_LONG)
                                        }
                                    }

                                    Toast.makeText(
                                        applicationContext,
                                        "Please verify your email before logging in!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    auth.signOut() // Prevent them from staying logged in
                                }


                            }//if auth is success ends
                            else {//auth is not successful
                                Toast.makeText(
                                    applicationContext,
                                    "Login  unsuccessful!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }//user data is feed closed


            }

        }


        //here is the create account
        binding.registerpass.setOnClickListener {
            binding.login.visibility = View.GONE
            binding.createaccount.visibility = View.VISIBLE

            binding.backcr.setOnClickListener {
                binding.createaccount.visibility = View.GONE
                binding.login.visibility = View.VISIBLE
            }
            binding.submitcr.setOnClickListener {

                val email = binding.emailcr.text.toString().trim()
                val pass = binding.passwordcr.text.toString().trim()
                val conpass = binding.conpasswordcr.text.toString().trim()

                if(pass.equals(conpass)) {


                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {


                            val user = auth.currentUser

                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { verifyTask ->
                                    if (verifyTask.isSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Verification email sent!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        Toast.makeText(
                                            this,
                                            "Registration Successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.createaccount.visibility = View.GONE
                                        binding.login.visibility = View.VISIBLE
                                        // redirects to the login page after registration is successful
                                    } else {
                                        Log.e(
                                            "EmailVerification",
                                            "Failed to send verification email.",
                                            verifyTask.exception
                                        )
                                    }
                                }

                        } else {
                            val errorCode = (task.exception as FirebaseAuthException).errorCode
                            when (errorCode) {
                                "ERROR_EMAIL_ALREADY_IN_USE" -> {
                                    Log.e("UserCheck", "Email already in use.")
                                    Toast.makeText(
                                        applicationContext,
                                        "Email already registered!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }

                            Toast.makeText(
                                this,
                                "Registration Failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show() //shows the exception that caused registration to fail
                        }
                    }

                }else{
                    Toast.makeText(this,"passwords should be same",Toast.LENGTH_LONG).show()
                }


            }
        }

        //give control to forget password
        binding.forgetpass.setOnClickListener {
            binding.login.visibility = View.GONE
            binding.forlayout.visibility = View.VISIBLE


            binding.formail.setOnClickListener {
                val emailAddress = binding.emailf.text.toString().trim()
                auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { resetmail ->
                        if (resetmail.isSuccessful) {
                            Log.d("ResetPassword", "Email sent.")
                            Toast.makeText(
                                applicationContext,
                                "Reset email sent!",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.forlayout.visibility = View.GONE
                            binding.login.visibility = View.VISIBLE
                        } else {
                            Log.e("ResetPassword", "Failed to send reset email.", resetmail.exception)
                        }
                    }



            }

            binding.backf.setOnClickListener {
                binding.forlayout.visibility = View.GONE
                binding.login.visibility = View.VISIBLE
            }


        }


    }
}