package com.ninositsolution.paging.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ninositsolution.paging.R
import com.ninositsolution.paging.databinding.AdapterLoadStateBinding

class ItemLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ItemLoadStateAdapter.ItemLoadStateViewHolder>() {

    class ItemLoadStateViewHolder(private val binding: AdapterLoadStateBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorText.text = loadState.error.localizedMessage
            }
            binding.loader.isVisible = loadState is LoadState.Loading
            binding.retryBtn.isVisible = loadState is LoadState.Error
            binding.errorText.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): ItemLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_load_state, parent, false)
                val binding = AdapterLoadStateBinding.bind(view)
                return ItemLoadStateViewHolder(binding, retry)
            }
        }

    }

    override fun onBindViewHolder(holder: ItemLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ItemLoadStateViewHolder {
        return ItemLoadStateViewHolder.create(parent, retry)
    }
}