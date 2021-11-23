package com.cool.myfashion.base


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.cool.myfashion.network.ErrorResult
import com.cool.myfashion.network.Success
import com.cool.myfashion.network.Result


/**
 * Created by rahul.p
 *
 */

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val error = MutableLiveData<ErrorResult<*>>()
    val state = MutableLiveData<BaseState>()
    fun <T> handleResult(
        result: Result<T>
    ) = when (result) {
        is Success -> {
            state.postValue(BaseState.Success)
            true
        }
        is ErrorResult -> {
            error.postValue(result)
            state.postValue(BaseState.Error)
            false
        }
    }

    enum class BaseState {
        Error, Loading, Success
    }

    fun log(message: String) = Log.d(this.javaClass.simpleName, message)
}