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
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.MatchedCompany
import com.rndtechnosoft.bni.Model.MyAllMatchesResponseData
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.ViewModel.MyMatchViewModel
import com.rndtechnosoft.bni.databinding.ActivityMyMatchesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMatchesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyMatchesBinding
    private lateinit var viewModel: MyMatchViewModel
    private lateinit var adapter: MyMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toast.makeText(this@MyMatchesActivity,"Hello",Toast.LENGTH_SHORT).show()
        viewModel = ViewModelProvider(this@MyMatchesActivity)[MyMatchViewModel::class.java]

        binding.rvMyMatch.layoutManager = LinearLayoutManager(this)

        //getMyAllMatch()

        val token: String? = SaveSharedPreference.getInstance(this@MyMatchesActivity).getToken()
        val userId: String? = SaveSharedPreference.getInstance(this@MyMatchesActivity).getUserId()

        // viewModel.setMyAllMatch(token!!, userId!!)

        /*   viewModel.getMyAllMatch().observe(this@MyMatchesActivity, Observer {
               adapter = MyMatchAdapter(this@MyMatchesActivity, it)
               binding.rvMyMatch.adapter = adapter
           })

           viewModel.getErrorData().observe(this@MyMatchesActivity, Observer {
               Toast.makeText(this@MyMatchesActivity, "Error: $it", Toast.LENGTH_SHORT).show()
               Log.d("Api Response", "Error: $it")
           })
        */

        myAllMatchList()


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

    /* private fun getMyAllMatch(){

         val token: String? =  "bearer " + SaveSharedPreference.getInstance(this@MyMatchesActivity).getToken()
         val userId: String? = SaveSharedPreference.getInstance(this@MyMatchesActivity).getUserId()

         RetrofitInstance.apiInterface.myAllMatches(token!!,userId).enqueue(object : Callback<MyAllMatchesResponseData?> {
             override fun onResponse(
                 call: Call<MyAllMatchesResponseData?>,
                 response: Response<MyAllMatchesResponseData?>
             ) {
                     if(response.isSuccessful){
                         Toast.makeText(this@MyMatchesActivity,"Success !!",Toast.LENGTH_SHORT).show()
                         val responseData:MyAllMatchesResponseData = response.body()!!
                         val list:MutableList<MatchedCompany> = responseData.matchedCompanies as MutableList
                         adapter = MyMatchAdapter(this@MyMatchesActivity, list)
                         binding.rvMyMatch.adapter = adapter

                     }
                 else{
                         Toast.makeText(this@MyMatchesActivity,"Response Error: ${response.code()}",Toast.LENGTH_SHORT).show()
                 }
             }

             override fun onFailure(call: Call<MyAllMatchesResponseData?>, t: Throwable) {
                 Toast.makeText(this@MyMatchesActivity,"Error: ${t.localizedMessage}",Toast.LENGTH_SHORT).show()
             }
         })
     }*/

}