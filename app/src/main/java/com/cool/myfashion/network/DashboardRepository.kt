package com.cool.myfashion.network

import com.cool.myfashion.base.BaseAuthenticatedRepository
import com.cool.myfashion.base.DetectConnection
import com.cool.myfashion.model.ImagesResult
import com.cool.myfashion.model.DashboardContentResult


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
    suspend fun getCarouselContentRepo(url:String): Result<ImagesResult> =
        executeForResponse(
            { it },
            { service.getCarouselContentAsync(url) }
        )

    suspend fun getDetailImageRepo(): Result<ImagesResult> =
        executeForResponse(
            { it },
            { service.getDetailImageContentAsync() }
        )
}