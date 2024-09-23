package com.rndtechnosoft.bconn.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Adapter.MyAskAdapter
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.ViewModel.MyAskViewModel
import com.rndtechnosoft.bconn.databinding.ActivityMyAsksBinding

class MyAsksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyAsksBinding
    private lateinit var myAskViewModel: MyAskViewModel
    private lateinit var adapter: MyAskAdapter
    private var userId: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAsksBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userId = SaveSharedPreference.getInstance(this@MyAsksActivity).getUserId()
        //val userId:String? = "667e59a0df828f83115bd1fb"
        //Toast.makeText(this@MyAsksActivity, "userId: $userId", Toast.LENGTH_SHORT).show()
        token = "bearer " + SaveSharedPreference.getInstance(this@MyAsksActivity).getToken()


        binding.rvMyAsks.layoutManager = LinearLayoutManager(this)

        myAskViewModel = ViewModelProvider(this@MyAsksActivity)[MyAskViewModel::class.java]

        fetchMyAsk(token!!, userId!!)

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add -> {
                    //Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MyAsksActivity,AddMyAskActivity::class.java))

                    true
                }
                else -> false
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.swipeMyAsks.setOnRefreshListener {

            fetchMyAsk(token!!, userId!!)
            binding.swipeMyAsks.isRefreshing = false
        }

    }

    private fun fetchMyAsk(token: String, userId: String) {

        myAskViewModel.myAskListResult(userId, token)

        myAskViewModel.observeMyAskResult().observe(this, Observer {

            //Toast.makeText(this@MyAsksActivity, "Success: $it", Toast.LENGTH_SHORT).show()

            adapter = MyAskAdapter(this@MyAsksActivity, it)
            binding.rvMyAsks.adapter = adapter

        })

        myAskViewModel.errorLiveData().observe(this@MyAsksActivity, Observer {
            Toast.makeText(this@MyAsksActivity, "Error: $it", Toast.LENGTH_SHORT).show()
            Log.d("Api Response", "Error: $it")
        })
    }


}