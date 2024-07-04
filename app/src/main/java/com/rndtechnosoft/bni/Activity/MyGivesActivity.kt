package com.rndtechnosoft.bni.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bni.Adapter.MyGivesAdapter
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.ViewModel.MyGivesViewModel
import com.rndtechnosoft.bni.databinding.ActivityMyGivesBinding

class MyGivesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyGivesBinding
    private lateinit var myGivesViewModel: MyGivesViewModel
    private lateinit var adapter: MyGivesAdapter
    var token: String? = null
    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGivesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = "bearer " + SaveSharedPreference.getInstance(this@MyGivesActivity).getToken()
        userId = SaveSharedPreference.getInstance(this@MyGivesActivity).getUserId()


        myGivesViewModel = ViewModelProvider(this@MyGivesActivity)[MyGivesViewModel::class.java]


        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add -> {
                    // Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
                    /*
                     val dialogFragment = AddMyGivesDialogFragment.newInstance()
                     dialogFragment.show(supportFragmentManager, dialogFragment.tag)
                     */

                    startActivity(Intent(this@MyGivesActivity, AddMyGivesActivity::class.java))

                    true
                }
                /*  R.id.search -> {
                      Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
                      true
                  }
                  */
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }


        binding.rvMyGives.layoutManager = LinearLayoutManager(this@MyGivesActivity)

        getMyGives()

        binding.swipeMyGives.setOnRefreshListener {
            getMyGives()
            binding.swipeMyGives.isRefreshing = false
        }

    }

    private fun getMyGives() {

        myGivesViewModel.getMyGivesLiveData(token!!, userId!!)

        myGivesViewModel.observeGetMyGives().observe(this@MyGivesActivity, Observer {

            adapter = MyGivesAdapter(it, this@MyGivesActivity)
            binding.rvMyGives.adapter = adapter
        })


        myGivesViewModel.errorLiveData().observe(this@MyGivesActivity, Observer {
            Toast.makeText(this@MyGivesActivity, "Error: $it", Toast.LENGTH_SHORT).show()
            Log.d("Api Response", "Error: $it")
        })

    }


    override fun onResume() {
        super.onResume()
        getMyGives()
        //Toast.makeText(this@MyGivesActivity, "Resume", Toast.LENGTH_SHORT).show()
    }


}