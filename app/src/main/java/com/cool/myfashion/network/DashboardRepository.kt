package com.cool.myfashion.network

import com.cool.myfashion.base.BaseAuthenticatedRepository
import com.cool.myfashion.base.DetectConnection
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

}