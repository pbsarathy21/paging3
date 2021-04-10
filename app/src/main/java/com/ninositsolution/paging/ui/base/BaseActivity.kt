package com.ninositsolution.paging.ui.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.ninositsolution.paging.R
import com.ninositsolution.paging.utils.snackBar
import com.ninositsolution.paging.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private var view: View? = null
    private var progressDialog: ACProgressFlower? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = onBaseCreate()
        setContentView(view)
    }

    protected abstract fun onBaseCreate(): View

    protected fun enableBaseEventHandler(viewModel: BaseViewModel) {
        lifecycleScope.launchWhenStarted {
            viewModel.baseEventHandler.collect {
                when (it) {
                    is BaseViewModel.BaseEventHandler.StartLoading -> showProgressDialog()
                    is BaseViewModel.BaseEventHandler.StopLoading -> dismissProgressDialog()
                    is BaseViewModel.BaseEventHandler.NotifyEvent -> {
                        Timber.i("NotifyEvent called : ${it.message}")
                        toast(it.message)
                    }
                    is BaseViewModel.BaseEventHandler.BaseErrorEvent -> {
                        Timber.i("BaseErrorEvent called : ${it.message}")
                        toast(it.message)
                    }
                    is BaseViewModel.BaseEventHandler.ValidationError -> {
                        view?.snackBar(it.message)
                    }
                }
            }
        }
    }

    protected fun showProgressDialog(msg: String = "Please wait...") {
        runOnUiThread {
            if (progressDialog != null) {
                progressDialog?.dismiss()
                progressDialog = null
            }

            progressDialog = initializeProgressDialog(msg)
            progressDialog?.show()
        }
    }

    protected fun dismissProgressDialog() {
        runOnUiThread {
            progressDialog?.dismiss()
        }
    }

    private fun initializeProgressDialog(msg: String): ACProgressFlower {
        return ACProgressFlower.Builder(this)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(Color.WHITE)
            .bgColor(ContextCompat.getColor(this, R.color.purple_200))
            .text(msg)
            .fadeColor(Color.DKGRAY)
            .build()
    }
}