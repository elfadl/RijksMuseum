package id.elfastudio.rijksmuseum.network

import id.elfastudio.rijksmuseum.BuildConfig
import id.elfastudio.rijksmuseum.data.entity.CollectionResponse
import id.elfastudio.rijksmuseum.others.Url
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("${Url.COLLECTION}?key=${BuildConfig.API_KEY}")
    suspend fun collection(): Response<CollectionResponse>

}