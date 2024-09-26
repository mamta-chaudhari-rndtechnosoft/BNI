package com.rndtechnosoft.bconn.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bconn.Model.MemberData
import com.rndtechnosoft.bconn.Model.ReferralMemberData
import com.rndtechnosoft.bconn.databinding.ItemApproveMemberBinding

class ApproveMemberAdapter(var context: Context, var approveMemberList: List<ReferralMemberData>) :
    RecyclerView.Adapter<ApproveMemberAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemApproveMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemApproveMemberBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val memberData: ReferralMemberData = approveMemberList.get(position)

        holder.binding.tvName.text = memberData.name
        holder.binding.tvEmail.text = memberData.email
        holder.binding.tvMobile.text = memberData.mobile
        holder.binding.tvStatus.text = memberData.approvedBymember

    }

    override fun getItemCount(): Int {
        return approveMemberList.size
    }

}