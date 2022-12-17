package com.example.sastreilor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.TextureView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    //private lateinit var binding: ActivityMainBinding No need of binding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        // Obtain the FirebaseAnalytics instance.


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = Firebase.analytics

        firebaseAuth = FirebaseAuth.getInstance()
        val email =intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")

       findViewById<TextView>(R.id.randomTV).text = email + "\n" + displayName

        //defining fragments
        val dashboardFragment: Fragment = DashboardFragment()
        val measurmentFragment: Fragment = Measurements_list_Fragment()
        val todoFragment: Fragment = To_do_Fragment()
        val calendarFragment: Fragment = Calendar_view_Fragment()

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.itemIconTintList = null //insert images as it is in the bottom

        //hnalde navigation selection
        bottomNavigation.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.nav_dashboard -> fragment = dashboardFragment
                R.id.nav_measurements -> fragment = measurmentFragment
                R.id.nav_Todo -> fragment = todoFragment
                R.id.nav_calendar -> fragment = calendarFragment
            }
            //commented the fragment use in place and use the fuction created to handle the fragments
            replaceFragment(fragment)
            //            fragmentManager.beginTransaction().replace(R.id.rlcontainer, fragment).commit() // rlcontainer is the id that i set for main layout and then changes with fragments
            true
        }
        //set default selection
        bottomNavigation.selectedItemId = R.id.nav_dashboard

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_top_navegation,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.top_nav_Button -> {
            // User chose the "Settings" item, show the app settings UI...
            val intent = Intent(this,AboutAppActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.top_nav_logout_btn -> {

            //logout
            firebaseAuth.signOut()
            // User chose the "Settings" item, show the app settings UI...
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.top_nav_Profile -> {

            // User chose the "Settings" item, show the app settings UI...
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(AllFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.rlcontainer, AllFragment)
        fragmentTransaction.commit()
    }
}