package com.rndtechnosoft.bconn.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bconn.Activity.CompanyActivity
import com.rndtechnosoft.bconn.Model.BusinessListData
import com.rndtechnosoft.bconn.databinding.ItemBusinessListBinding

class BusinessListAdapter(
    private val context: Context,
    private val businessList: List<BusinessListData>
) : RecyclerView.Adapter<BusinessListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemBusinessListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(businessData: BusinessListData) {
            binding.layoutBusiness.setOnClickListener {

                val intent: Intent = Intent(context, CompanyActivity::class.java)
                intent.putExtra("companyId", businessData._id)
                context.startActivity(intent)
            }

            binding.tvIndustry.text = businessData.industryName
            binding.tvCompany.text = businessData.companyName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBusinessListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return businessList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val businessListData = businessList[position]
        holder.bind(businessListData)
    }


}