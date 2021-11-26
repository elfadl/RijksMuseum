package id.elfastudio.rijksmuseum.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    protected lateinit var auth: FirebaseAuth
    protected var autoLogoutPage = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser == null && autoLogoutPage){
            finishAffinity()
            startActivity(Intent(this, SplashScreen::class.java))
        }
    }

}