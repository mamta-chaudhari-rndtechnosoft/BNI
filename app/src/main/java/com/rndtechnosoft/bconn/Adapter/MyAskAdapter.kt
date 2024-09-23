package com.rndtechnosoft.bconn.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bconn.Activity.MyMatchByDepartmentAndCompany
import com.rndtechnosoft.bconn.Model.MyAskListData
import com.rndtechnosoft.bconn.databinding.ItemMyAskBinding

class MyAskAdapter(
    private val context: Context,
    private val myAskList: MutableList<MyAskListData>
) : RecyclerView.Adapter<MyAskAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemMyAskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myAskData: MyAskListData) {

            binding.tvCompanyName.text = myAskData.companyName
            binding.tvDeptName.text = myAskData.dept
            binding.tvMessage.text = myAskData.message

            binding.cardMyAsk.setOnClickListener {
                val intent:Intent = Intent(context,MyMatchByDepartmentAndCompany::class.java)
                intent.putExtra("dept",myAskList[adapterPosition].dept)
                intent.putExtra("companyName",myAskList[adapterPosition].companyName)
                context.startActivity(intent)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAskAdapter.ViewHolder {
        val view = ItemMyAskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAskAdapter.ViewHolder, position: Int) {
        val myAskDataList = myAskList[position]
        holder.bind(myAskDataList)
    }

    override fun getItemCount(): Int {
        return myAskList.size
    }
}