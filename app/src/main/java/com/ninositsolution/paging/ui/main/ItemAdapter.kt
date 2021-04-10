package com.ninositsolution.paging.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ninositsolution.paging.data.models.User
import com.ninositsolution.paging.databinding.AdapterItemBinding

class ItemAdapter : PagingDataAdapter<User, ItemAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    class ItemViewHolder(val binding: AdapterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.user = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldUser: User, newUser: User): Boolean {
                return oldUser.id == newUser.id
            }

            override fun areContentsTheSame(oldUser: User, newUser: User): Boolean {
                return oldUser == newUser
            }
        }
    }
}