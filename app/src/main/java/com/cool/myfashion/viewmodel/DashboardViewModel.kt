package com.cool.myfashion.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cool.myfashion.base.BaseViewModel
import com.cool.myfashion.model.CarouselDataMapper
import com.cool.myfashion.model.DashboardContentResult
import com.cool.myfashion.model.ImagesResult
import com.cool.myfashion.network.DashboardRepository
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
    private val carouselContentLiveData = MutableLiveData<CarouselDataMapper>()
    private val detailImageLiveData = MutableLiveData<ImagesResult>()


    fun getDashboardContent(): LiveData<DashboardContentResult> = dashboardContentLiveData
    fun getCarouselContent(): LiveData<CarouselDataMapper> = carouselContentLiveData
    fun getDetailImageContent(): LiveData<ImagesResult> = detailImageLiveData


    fun fetchDashBoardContent() {
        state.postValue(BaseState.Loading)
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getDashboardContentRepo()
            Log.d(this.javaClass.simpleName, "Dashboard Content: $result")
            val dashboardContent = result.get()
            val success = handleResult(result)
            if (success) {
                dashboardContent?.let { content ->
                    dashboardContentLiveData.postValue(content)

                }
            }
        }
    }

    fun fetchCarouselContent(url: String, pos: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getCarouselContentRepo(url)
            Log.d(this.javaClass.simpleName, "Dashboard  Carousel Content: $result")
            val dashboardContent = result.get()
            val success = handleResult(result)
            if (success) {
                dashboardContent?.let { content ->
                    val carouselMapper = CarouselDataMapper(pos, dashboardContent.images)
                    carouselContentLiveData.postValue(carouselMapper)

                }
            }
        }
    }

    fun fetchDetailImageContent() {
        state.postValue(BaseState.Loading)
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getDetailImageRepo()
            Log.d(this.javaClass.simpleName, "Detail Image Content: $result")
            val dashboardContent = result.get()
            val success = handleResult(result)
            if (success) {
                dashboardContent?.let { content ->
                    detailImageLiveData.postValue(content)

                }
            }
        }
    }

}