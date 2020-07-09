package com.praszapps.prasangithubdetails
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): Result<T> {
    val response = call()
    if(response.isSuccessful) return Result.Success(response.body()!!)
    return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
}

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}