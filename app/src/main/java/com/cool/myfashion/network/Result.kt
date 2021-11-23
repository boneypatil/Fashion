package com.cool.myfashion.network

import android.os.Bundle

/**
 * Created by rahul.p
 *
 */
sealed class Result<T> {
    open fun get(): T? = null
}

data class Success<T>(val data: T) : Result<T>() {
    override fun get(): T? = data
}

data class ErrorResult<T>(
    val errorCode: Int,
    var errorMessage: String,
    var bundle: Bundle? = null
) : Result<T>()

object ErrorCodes {
    const val label = "ErrorCode"
    const val ERROR_WRONG_CURRENT_PASSWORD = 457
    const val ERROR_UNAUTHORIZED = 401
    const val ERROR_TIMEOUT = 408
    const val ERROR_ATTEMPTED_LOG_IN = 456
    const val ERROR_SERVER_ERROR = 500
    const val ERROR_INVALID_INVITE_CODE = 505
    const val ERROR_NO_CONNECTION = 603
    const val ERROR_UNKNOWN = 604
    const val ERROR_CUSTOM_IMAGE_LOADING = 1020

    const val ERROR_CUSTOM_ASSET_LOADING = 1021
    const val ERROR_CONVERSATION_SESSION_EXPIRED = 404
    const val ERROR_CONVERSATION_SESSION_NEED_LOGOUT = 479

}
object TokenCodes {
    const val label = "RefreshCode"
    const val TOKEN_VALID = 205
    const val REFRESH_ERROR = 505
    const val REFRESH_LOGOUT = 405
}
