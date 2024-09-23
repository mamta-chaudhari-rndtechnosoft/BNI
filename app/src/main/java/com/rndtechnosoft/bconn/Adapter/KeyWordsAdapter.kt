package com.rndtechnosoft.bconn.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rndtechnosoft.bconn.databinding.ItemKeywordsBinding

class KeyWordsAdapter(private val context: Context, private val list: MutableList<String>) :
    RecyclerView.Adapter<KeyWordsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemKeywordsBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyWordsAdapter.ViewHolder {
        val view = ItemKeywordsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: KeyWordsAdapter.ViewHolder, position: Int) {
        val keyword = list[position]
        holder.binding.tvKeyword.text = keyword
        holder.binding.imgDelete.setOnClickListener {
            //list.removeAt(position)
            //notifyItemRemoved(position)
            if (position < list.size) {
                list.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, list.size)

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun addKeyword(keyword: String) {
        list.add(keyword)
        notifyItemInserted(list.size - 1)
    }

}