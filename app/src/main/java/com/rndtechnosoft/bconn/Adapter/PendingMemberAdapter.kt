package com.rndtechnosoft.bconn.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bconn.Model.ReferralMemberData
import com.rndtechnosoft.bconn.databinding.ItemPendingMemberBinding

class PendingMemberAdapter(
    var context: Context,
    var pendingMemberList: MutableList<ReferralMemberData>,
    var listener: itemButtonClickListener
) :
    RecyclerView.Adapter<PendingMemberAdapter.ViewHolder>() {

    interface itemButtonClickListener {
        fun approveMemberBtn(position: Int, id: String)
        fun rejectMemberBtn(position: Int, id: String)
    }


    inner class ViewHolder(val binding: ItemPendingMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPendingMemberBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val memberData: ReferralMemberData = pendingMemberList.get(position)

        holder.binding.tvName.text = memberData.name
        holder.binding.tvEmail.text = memberData.email
        holder.binding.tvMobile.text = memberData.mobile
        holder.binding.tvStatus.text = memberData.approvedBymember

        holder.binding.btnAccept.setOnClickListener {

            listener.approveMemberBtn(position, memberData._id)

        }

        holder.binding.btnCancel.setOnClickListener {
            listener.rejectMemberBtn(position, memberData._id)
        }

    }

    override fun getItemCount(): Int {
        return pendingMemberList.size
    }

}