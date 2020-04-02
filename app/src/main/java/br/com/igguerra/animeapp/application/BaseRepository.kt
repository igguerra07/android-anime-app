package br.com.igguerra.animeapp.application

import android.util.Log
import br.com.igguerra.animeapp.network.ErrorType
import br.com.igguerra.animeapp.network.Outcome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

open class BaseRepository {

    suspend inline fun <T> safeApiCall(crossinline responseFunction: suspend () -> T): Outcome<T>? {
        var errorMsg = ""
        return try {
            val response = withContext(Dispatchers.IO) { responseFunction.invoke() }
            Outcome.success(response)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
                errorMsg = when (e) {
                    is HttpException -> {
                        val body = e.response()?.errorBody()
                        getErrorMessage(body)
                    }
                    is SocketTimeoutException -> getErrorMessage(ErrorType.TIMEOUT)
                    is IOException -> getErrorMessage(ErrorType.NETWORK)
                    else -> getErrorMessage(ErrorType.UNKNOWN)
                }
            }
            Outcome.error(errorMsg)
        }
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }

    fun getErrorMessage(errorType: ErrorType): String {
        return when (errorType) {
            ErrorType.TIMEOUT -> "Tivemos um problema com a sua conexão. Foi foi detectado lentidão na sua conexão de internet, por favor tente mais tarde ."
            ErrorType.NETWORK -> "Tivemos um problema com a sua conexão. Verifique se você está conectado à internet e tente novamente."
            ErrorType.UNKNOWN -> "Something wrong happened UNKNOWN"
        }
    }

    companion object {
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }
}