package com.cool.myfashion.base

import android.util.Log
import com.cool.myfashion.network.ErrorCodes
import com.cool.myfashion.network.ErrorResult
import com.cool.myfashion.network.Success
import com.cool.myfashion.network.Result
import kotlinx.coroutines.Deferred
import retrofit2.HttpException

/**
 * Created by rahul.p
 *
 */
abstract class BaseAuthenticatedRepository(
    private val connectionUtil: DetectConnection
) {

    suspend fun <T, U> executeForResponse(
        mapper: (T) -> U,
        request: suspend () -> Deferred<T>
    ): Result<U> {
        return try {
            val response = request().await()
            Success(mapper(response))
        } catch (e: HttpException) {
            logit("Exception code: ${e.code()}\nmessage: ${e.message()}\netc: $e")
            when (e.code()) {
                ErrorCodes.ERROR_UNAUTHORIZED -> ErrorResult(e.code(), "Please check your credentials")
                else -> ErrorResult(e.code(), "Something went wrong")
            }
        } catch (e: Exception) {
            logit("Exception message: ${e.message}")
            e.printStackTrace()
            connectionUtil.getErrorMessage(e)

        }
    }


    suspend fun <T> executeForResponse(request: Deferred<T>): Result<T> {
        return try {

            val response = request.await()
            logit("Login Request Response: $response")
            Success(response)
        } catch (e: HttpException) {
            logit("Login Request Error Response: ${e.message()}")
            ErrorResult(e.code(), e.message())
        } catch (e: Exception) {
            logit("Login Request Exception: ${e.message}")
            connectionUtil.getErrorMessage()
        }
    }




    private fun logit(message: String) {
        Log.d(this.javaClass.simpleName, message)
    }
}
