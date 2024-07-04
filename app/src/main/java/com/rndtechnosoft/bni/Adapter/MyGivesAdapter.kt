package com.rndtechnosoft.bni.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bni.Model.MyGivesData
import com.rndtechnosoft.bni.databinding.ItemMyGivesBinding

class MyGivesAdapter(private var myGivesList:MutableList<MyGivesData>,private var context:Context):RecyclerView.Adapter<MyGivesAdapter.ViewHolder>() {

    class ViewHolder(private var binding:ItemMyGivesBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(myGiveData:MyGivesData){

            binding.tvCompanyName.text = myGiveData.companyName
            binding.tvDeptName.text = myGiveData.dept
            binding.tvEmail.text = myGiveData.email
            binding.tvPhone.text = myGiveData.phoneNumber
            binding.tvUrl.text = myGiveData.webURL
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGivesAdapter.ViewHolder {
        val view = ItemMyGivesBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyGivesAdapter.ViewHolder, position: Int) {

        val myGiveData:MyGivesData = myGivesList[position]
        holder.bind(myGiveData)

    }

    override fun getItemCount(): Int {
      return myGivesList.size
    }
}