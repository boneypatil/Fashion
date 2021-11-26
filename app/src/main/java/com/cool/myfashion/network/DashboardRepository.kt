package com.cool.myfashion.network

import com.cool.myfashion.base.BaseAuthenticatedRepository
import com.cool.myfashion.base.DetectConnection
import com.cool.myfashion.model.CarouselResult
import com.cool.myfashion.model.DashboardContentResult
import com.cool.myfashion.model.Images


/**
 * Created by rahul,p
 *
 */
class DashboardRepository(
    private val service: DashboardService,
    connectionUtil: DetectConnection
) : BaseAuthenticatedRepository(connectionUtil) {

    suspend fun getDashboardContentRepo(): Result<DashboardContentResult> =
        executeForResponse(
            { it },
            { service.getDashboardContentAsync() }
        )
    suspend fun getCarouselContentRepo(url:String): Result<CarouselResult> =
        executeForResponse(
            { it },
            { service.getCarouselContentAsync(url) }
        )
}