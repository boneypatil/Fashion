package com.cool.myfashion.base


import androidx.fragment.app.Fragment
import com.cool.myfashion.model.Images
import com.cool.myfashion.network.ErrorResult
import com.cool.myfashion.utils.toast

abstract class BaseDashboardFragment : Fragment() {

    private var dashboardListener: DashboardListener? = null


    protected fun onImageSelected(image: Images) {
        dashboardListener?.onImageSelected(image)
    }

    fun setListener(listener: DashboardListener) {
        this.dashboardListener = listener
    }

    interface DashboardListener {
        fun onImageSelected(image: Images)
    }

    protected fun handleError(errorResult: ErrorResult<*>) {
        val message = errorResult.errorMessage
        if (message.isNotEmpty())
            context.toast(message)
    }

}