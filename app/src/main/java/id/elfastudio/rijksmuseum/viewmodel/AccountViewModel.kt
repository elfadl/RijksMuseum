package id.elfastudio.rijksmuseum.viewmodel

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elfastudio.rijksmuseum.data.repository.AccountRepository
import id.elfastudio.rijksmuseum.others.Resource
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val _emailValidate = MutableLiveData<Int>()
    val emailValidate: LiveData<Int> = _emailValidate
    private val _passwordValidate = MutableLiveData<Int>()
    val passwordValidate: LiveData<Int> = _passwordValidate
    val cbTnD = MutableLiveData(false)
    val isValid = MediatorLiveData<Boolean>()

    init {
        isValid.value = false
        isValid.addSource(emailValidate) {
            isValid.value = it == EMAIL_VALID
        }
        isValid.addSource(passwordValidate) {
            isValid.value = it == PASSWORD_VALID
        }
    }

    @get:Bindable
    var email = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
            when {
                value.isEmpty() -> _emailValidate.postValue(EMAIL_EMPTY)
                !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> _emailValidate.postValue(
                    EMAIL_NOT_VALID
                )
                else -> _emailValidate.postValue(EMAIL_VALID)
            }
        }

    @get:Bindable
    var password = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
            when {
                value.isEmpty() -> _passwordValidate.postValue(PASSWORD_EMPTY)
                else -> _passwordValidate.postValue(PASSWORD_VALID)
            }
        }

    fun register(): LiveData<Resource<FirebaseUser>> = liveData {
        emit(Resource.loading())
        if (cbTnD.value == false) {
            emit(Resource.error("Checkbox should be checked"))
        } else {
            val response = accountRepository.register(email, password)
            response.data?.let {
                emit(Resource.success(it))
            } ?: kotlin.run { emit(Resource.error(response.message)) }
        }
    }

    fun login(): LiveData<Resource<FirebaseUser>> = liveData {
        emit(Resource.loading())
        val response = accountRepository.login(email, password)
        response.data?.let {
            emit(Resource.success(it))
        } ?: kotlin.run { emit(Resource.error(response.message)) }
    }

    companion object {
        const val EMAIL_VALID = 0
        const val EMAIL_EMPTY = 1
        const val EMAIL_NOT_VALID = 2
        const val PASSWORD_VALID = 3
        const val PASSWORD_EMPTY = 4
    }

}