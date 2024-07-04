package com.rndtechnosoft.bni.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bni.Adapter.MyMatchAdapter
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.ViewModel.MyMatchViewModel
import com.rndtechnosoft.bni.databinding.ActivityMyMatchesBinding

class MyMatchesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyMatchesBinding
    private lateinit var viewModel: MyMatchViewModel
    private lateinit var adapter: MyMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this@MyMatchesActivity,"Hello",Toast.LENGTH_SHORT).show()


        viewModel = ViewModelProvider(this@MyMatchesActivity)[MyMatchViewModel::class.java]

        binding.rvMyMatch.layoutManager = LinearLayoutManager(this)


        val token: String? = SaveSharedPreference.getInstance(this@MyMatchesActivity).getToken()
        val userId: String? = SaveSharedPreference.getInstance(this@MyMatchesActivity).getUserId()

        viewModel.setMyAllMatch(token!!, userId!!)

        viewModel.getMyAllMatch().observe(this@MyMatchesActivity, Observer {
            adapter = MyMatchAdapter(this@MyMatchesActivity, it)
            binding.rvMyMatch.adapter = adapter
        })

        viewModel.getErrorData().observe(this@MyMatchesActivity, Observer {
            Toast.makeText(this@MyMatchesActivity, "Error: $it", Toast.LENGTH_SHORT).show()
            Log.d("Api Response", "Error: $it")
        })

        //myAllMatchList()


    }

    private fun myAllMatchList() {

        val token: String? = SaveSharedPreference.getInstance(this@MyMatchesActivity).getToken()
        val userId: String? = SaveSharedPreference.getInstance(this@MyMatchesActivity).getUserId()

        viewModel.setMyAllMatch(token!!, userId!!)

        viewModel.getMyAllMatch().observe(this@MyMatchesActivity, Observer {
            adapter = MyMatchAdapter(this@MyMatchesActivity, it)
            binding.rvMyMatch.adapter = adapter
        })

        viewModel.getErrorData().observe(this@MyMatchesActivity, Observer {
            Toast.makeText(this@MyMatchesActivity, "Error: $it", Toast.LENGTH_SHORT).show()
            Log.d("Api Response", "Error: $it")
        })

    }
}