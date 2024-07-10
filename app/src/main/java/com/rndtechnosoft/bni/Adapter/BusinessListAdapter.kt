package com.rndtechnosoft.bni.Adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bni.Activity.CompanyActivity
import com.rndtechnosoft.bni.Activity.MyMatchByDepartmentAndCompany
import com.rndtechnosoft.bni.Model.BusinessListResponseData
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.databinding.ItemBusinessListBinding

class BusinessListAdapter(
    private val context: Context,
    private val businessList: List<BusinessListResponseData>
) : RecyclerView.Adapter<BusinessListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemBusinessListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(businessData:BusinessListResponseData) {
            binding.layoutBusiness.setOnClickListener {

                val intent: Intent = Intent(context, CompanyActivity::class.java)
                //intent.putExtra("comanyId",)
                context.startActivity(intent)
            }

            binding.tvIndustry.text = businessData.industryName
            binding.tvCompany.text =  "Company Name"
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
        val businessListData  = businessList[position]
        holder.bind(businessListData)
    }


}