package com.ninositsolution.paging.ui.main

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ninositsolution.paging.databinding.ActivityMainBinding
import com.ninositsolution.paging.ui.base.BaseActivity
import com.ninositsolution.paging.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private val itemAdapter by lazy { ItemAdapter() }

    override fun onBaseCreate(): View {
        enableBaseEventHandler(viewModel)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.itemRecyclerView.hasFixedSize()
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.itemRecyclerView.adapter =
            itemAdapter.withLoadStateHeaderAndFooter(
                header = ItemLoadStateAdapter { itemAdapter.retry() },
                footer = ItemLoadStateAdapter { itemAdapter.retry() }
            )

        lifecycleScope.launchWhenCreated {
            viewModel.getItemsFromAPI().collect {
                itemAdapter.submitData(it)
            }
        }

        itemAdapter.addLoadStateListener {
            when (it.source.refresh) {
                is LoadState.Loading -> showProgressDialog()
                is LoadState.NotLoading -> dismissProgressDialog()
                is LoadState.Error -> toast("error fetching data")
            }
        }

        return binding.root
    }
}