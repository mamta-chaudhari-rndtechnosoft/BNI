package com.rndtechnosoft.bni.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.rndtechnosoft.bni.Fragment.HomeFragment
import com.rndtechnosoft.bni.Fragment.ProfileFragment
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

        /*val userId:String? = SaveSharedPreference.getInstance(this@MainActivity).getUserId()
        //Toast.makeText(this@MainActivity,"ID: $userId",Toast.LENGTH_SHORT).show()
        Log.d("Api Response",userId!!)

        binding.cardMyAsk.setOnClickListener {
            startActivity(Intent(this@MainActivity,MyAsksActivity::class.java))
        }

        binding.cardMyGives.setOnClickListener {
            startActivity(Intent(this@MainActivity,MyGivesActivity::class.java))
        }*/

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.mainContainer.id, fragment)
        transaction.commit()
    }

    fun setSelectedBottomNavItem(itemId: Int) {
        binding.bottomNav.selectedItemId = itemId
    }
}