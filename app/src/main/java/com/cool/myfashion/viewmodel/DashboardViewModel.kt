package com.cool.myfashion.viewmodel


import com.cool.myfashion.network.DashboardRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cool.myfashion.base.BaseViewModel
import com.cool.myfashion.model.DashboardContentResult

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by rahul.p
 *
 */
class DashboardViewModel(
    val repo: DashboardRepository,
    application: Application
) : BaseViewModel(application) {

    private val dashboardContentLiveData = MutableLiveData<DashboardContentResult>()


    fun getSubscriptions(): LiveData<DashboardContentResult> = dashboardContentLiveData


    fun fetchDashBoardContent() {
        state.postValue(BaseState.Loading)
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getDashboardContentRepo()
            Log.d(this.javaClass.simpleName, "Dashboard Content: $result")
            val dashboardContent = result.get()
            val success = handleResult(result)
            if (success) {
                dashboardContent?.let { content->
                    dashboardContentLiveData.postValue(content)

                }
            }
        }
    }


}