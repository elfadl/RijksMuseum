package id.elfastudio.rijksmuseum.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.elfastudio.rijksmuseum.data.datasource.CollectionDataSource
import id.elfastudio.rijksmuseum.data.entity.CollectionResponse
import id.elfastudio.rijksmuseum.others.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CollectionRepository @Inject constructor(
    private val collectionDataSource: CollectionDataSource
) {

    fun collection(): LiveData<Resource<CollectionResponse>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val response = collectionDataSource.collections()
        response.data?.let {
            emit(Resource.success(it))
        } ?: kotlin.run { emit(Resource.error(response.message)) }
    }

}