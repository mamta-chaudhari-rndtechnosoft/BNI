package com.rndtechnosoft.bconn.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bconn.Model.MatchedCompany
import com.rndtechnosoft.bconn.databinding.ItemMyAllMatchBinding

class MyMatchAdapter(
    private val context: Context,
    private val myMatchList: MutableList<MatchedCompany>
) : RecyclerView.Adapter<MyMatchAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemMyAllMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myMatchData: MatchedCompany) {

            binding.tvCompanyName.text = myMatchData.companyName
            binding.tvDeptName.text = myMatchData.dept
            binding.tvEmail.text = myMatchData.email
            binding.tvPhone.text = myMatchData.phoneNumber
            binding.tvUrl.text = myMatchData.webURL
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMatchAdapter.ViewHolder {
        val view = ItemMyAllMatchBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyMatchAdapter.ViewHolder, position: Int) {
        val allMatchData: MatchedCompany = myMatchList[position]
        holder.bind(allMatchData)

    }

    override fun getItemCount(): Int {
        return myMatchList.size
    }


}