package id.elfastudio.rijksmuseum.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import id.elfastudio.rijksmuseum.data.entity.CollectionResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun collection(): Response<CollectionResponse>
    suspend fun login(email: String, password: String): Task<AuthResult>
    suspend fun register(email: String, password: String): Task<AuthResult>
}