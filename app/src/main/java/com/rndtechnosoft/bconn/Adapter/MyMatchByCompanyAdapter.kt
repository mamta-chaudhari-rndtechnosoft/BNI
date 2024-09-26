package com.rndtechnosoft.bconn.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bconn.Model.Data
import com.rndtechnosoft.bconn.databinding.ItemMatchByCompanyBinding



class MyMatchByCompanyAdapter(private val context:Context, private val matchListByCompany:MutableList<Data>):RecyclerView.Adapter<MyMatchByCompanyAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMatchByCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myMatchData: Data) {

            binding.tvCompanyName.text = myMatchData.myGives.companyName
            binding.tvDeptName.text = myMatchData.myGives.dept
            binding.tvEmail.text = myMatchData.myGives.email
            binding.tvPhone.text = myMatchData.myGives.phoneNumber
            binding.tvUrl.text = myMatchData.myGives.webURL

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyMatchByCompanyAdapter.ViewHolder {
        val view = ItemMatchByCompanyBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyMatchByCompanyAdapter.ViewHolder, position: Int) {
        val allMatchData: Data = matchListByCompany[position]
        holder.bind(allMatchData)
    }

    override fun getItemCount(): Int {
        return matchListByCompany.size
    }
}