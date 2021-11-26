package id.elfastudio.rijksmuseum.data.datasource

import id.elfastudio.rijksmuseum.others.Resource
import okio.IOException
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getDataResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: IOException) {
            return error(e.message ?: e.toString())
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(
        message: String,
        networkError: Boolean = true
    ): Resource<T> {
        return Resource.error(
            if (networkError) "Network call has failed for a following reason: $message"
            else message
        )
    }

}