package com.kmm.dicodingsecondsubmission.utlity

sealed class StateHandler<T> (
    val data:T? = null,
    val message:String? = null
){
    class Success<T>(data: T?):StateHandler<T>(data)
    class Error<T>(message: String?,data: T?=null):StateHandler<T>(data,message)
    class Loading<Nothing>:StateHandler<Nothing>()
}