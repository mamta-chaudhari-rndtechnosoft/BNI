package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rndtechnosoft.bconn.Fragment.HomeFragment
import com.rndtechnosoft.bconn.Fragment.MyAskFragment
import com.rndtechnosoft.bconn.Fragment.MyGiveFragment
import com.rndtechnosoft.bconn.Fragment.MyMatchFragment
import com.rndtechnosoft.bconn.Fragment.ProfileFragment
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var token:String? = SaveSharedPreference.getInstance(this@MainActivity).getToken()
        var userId:String? = SaveSharedPreference.getInstance(this@MainActivity).getUserId()

        //Toast.makeText(this@MainActivity,"token: $token  userId:$userId",Toast.LENGTH_SHORT).show()

        Log.d("Api Response","token: $token  userId:$userId")

        loadFragment(MyAskFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.myAsk -> {
                    loadFragment(MyAskFragment())
                    true
                }

                R.id.myGive -> {
                    loadFragment(MyGiveFragment())
                    true
                }

                R.id.myMatch -> {
                    loadFragment(MyMatchFragment())
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