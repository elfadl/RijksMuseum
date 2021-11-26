package id.elfastudio.rijksmuseum.data.repository

import id.elfastudio.rijksmuseum.data.datasource.AccountDataSource
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDataSource: AccountDataSource
) {

    suspend fun register(email: String, password: String) =
        accountDataSource.register(email, password)

    suspend fun login(email: String, password: String) =
        accountDataSource.login(email, password)

}