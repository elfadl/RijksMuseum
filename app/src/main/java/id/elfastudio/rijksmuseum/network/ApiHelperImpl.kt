package id.elfastudio.rijksmuseum.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.elfastudio.rijksmuseum.data.entity.CollectionResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun collection(): Response<CollectionResponse> =
        apiService.collection()

    override suspend fun login(email: String, password: String): Task<AuthResult> =
        Firebase.auth.signInWithEmailAndPassword(email, password)

    override suspend fun register(email: String, password: String): Task<AuthResult> =
        Firebase.auth.createUserWithEmailAndPassword(email, password)

}