package com.example.techfest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.techfest.freatures.event
import com.example.techfest.freatures.gallery
import com.example.techfest.freatures.mapact
import com.example.techfest.freatures.ranking
import com.example.techfest.freatures.registration
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            val topInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
//
//            v.setPadding(0, topInset, 0,0)
//            insets
//        }

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar= findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.techfestlogoham)

        setSupportActionBar(toolbar)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        val toogle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navopen,R.string.navclose)
        drawerLayout.addDrawerListener(toogle)
//         toogle.syncState()

//        toolbar.setNavigationOnClickListener {
//            Toast.makeText(this,"drawer opend",Toast.LENGTH_SHORT).show()
//            drawerLayout.openDrawer(GravityCompat.START)
//        }


        //set the default fragment

        if(savedInstanceState==null)
        {
            replacefragment(event())
            navigationView.setCheckedItem(R.id.events)
        }





    }
    private fun replacefragment(fragment: Fragment)
    {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }



    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START)

        }
        else{
            onBackPressedDispatcher.onBackPressed()
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.events -> replacefragment(event())
            R.id.gallery -> replacefragment(gallery())
            R.id.maps -> { startActivity(Intent(this,mapact::class.java))
            }
            R.id.ranking -> replacefragment(ranking())
            R.id.registration -> replacefragment(registration())

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    }






