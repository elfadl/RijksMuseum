package id.elfastudio.rijksmuseum.data.datasource

import id.elfastudio.rijksmuseum.data.entity.CollectionResponse
import id.elfastudio.rijksmuseum.network.ApiHelper
import id.elfastudio.rijksmuseum.others.Resource
import javax.inject.Inject

class CollectionDataSource @Inject constructor(
    private val apiHelper: ApiHelper
): BaseDataSource() {

    suspend fun collections(): Resource<CollectionResponse> = getDataResult {
        apiHelper.collection()
    }

}