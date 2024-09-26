package com.rndtechnosoft.bconn.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bconn.Model.ReferralMemberData
import com.rndtechnosoft.bconn.databinding.ItemRejectedMemberBinding


class RejectedMemberAdapter (var context: Context, var rejectedMemberList: List<ReferralMemberData>) :
    RecyclerView.Adapter<RejectedMemberAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRejectedMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRejectedMemberBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val memberData: ReferralMemberData = rejectedMemberList.get(position)
        holder.binding.tvName.text = memberData.name
        holder.binding.tvEmail.text = memberData.email
        holder.binding.tvMobile.text = memberData.mobile
        holder.binding.tvStatus.text = memberData.approvedBymember

    }

    override fun getItemCount(): Int {
        return rejectedMemberList.size
    }

}