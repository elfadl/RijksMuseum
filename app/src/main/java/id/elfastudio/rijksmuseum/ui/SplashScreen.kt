package id.elfastudio.rijksmuseum.ui

import android.content.Intent
import android.os.Bundle
import id.elfastudio.rijksmuseum.ui.account.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SplashScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoLogoutPage = false
    }

    override fun onStart() {
        super.onStart()
        runBlocking { delay(1000) }
        val currentUser = auth.currentUser
        startActivity(
            Intent(
                this,
                if (currentUser != null)
                    HomeActivity::class.java
                else
                    LoginActivity::class.java
            )
        )
        finish()
    }

}