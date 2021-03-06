package com.cool.myfashion.network

import com.cool.myfashion.model.ImagesResult
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

    @GET(value = "/{url}")
    fun getCarouselContentAsync(@Path(value = "url") url: String): Deferred<ImagesResult>

    @GET(value = "/list")
    fun getDetailImageContentAsync(): Deferred<ImagesResult>

}