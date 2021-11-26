package id.elfastudio.rijksmuseum.ui.home

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elfastudio.rijksmuseum.data.repository.CollectionRepository
import id.elfastudio.rijksmuseum.others.RefreshableLiveData
import id.elfastudio.rijksmuseum.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository
) : BaseViewModel() {

    val collections = RefreshableLiveData{ collectionRepository.collection() }

    val error = MutableLiveData(false)
    val empty = MutableLiveData(false)
    val message = MutableLiveData("")

    fun refresh(){
        collections.refresh()
    }

}