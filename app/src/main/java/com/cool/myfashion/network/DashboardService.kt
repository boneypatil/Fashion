package com.cool.myfashion.network

import com.cool.myfashion.model.DashboardContentResult
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * Created by rahul.p
 *
 */
interface DashboardService {
    @GET(value = "/content")
    fun getDashboardContentAsync(): Deferred<DashboardContentResult>

}