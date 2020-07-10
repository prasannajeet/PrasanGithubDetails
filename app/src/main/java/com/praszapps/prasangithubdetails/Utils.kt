package com.praszapps.prasangithubdetails
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(
    messageInCaseOfError: String,
    call: suspend () -> Response<T>
): APICallResult<T> {
    val response = call()
    if (response.isSuccessful) return APICallResult.OnSuccessResponse(response.body()!!)
    return APICallResult.OnErrorResponse(IOException("Error Occurred during getting safe Api result, Custom ERROR - $messageInCaseOfError"))
}


sealed class APICallResult<out T: Any> {
    data class OnSuccessResponse<out T : Any>(val data: T) : APICallResult<T>()
    data class OnErrorResponse(val exception: Exception) : APICallResult<Nothing>()
}

/**
 * Lets the UI act on a controlled bound of states that can be defined here
 * @author Prasan
 * @since 1.0
 */
sealed class UIState<out T: Any> {
    object Loading: UIState<Nothing>()
    data class OnOperationSuccess<out T: Any>(val output: T): UIState<T>()
    data class OnOperationFailed(val exception: Exception): UIState<Nothing>()
}