package id.elfastudio.rijksmuseum.ui.account

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import id.elfastudio.rijksmuseum.R
import id.elfastudio.rijksmuseum.databinding.ActivityRegisterBinding
import id.elfastudio.rijksmuseum.others.Status
import id.elfastudio.rijksmuseum.others.hide
import id.elfastudio.rijksmuseum.others.show
import id.elfastudio.rijksmuseum.ui.BaseActivity
import id.elfastudio.rijksmuseum.viewmodel.AccountViewModel

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewmodel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewmodel = viewmodel
        autoLogoutPage = false

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setObserver()
        init()
    }

    private fun setObserver() {
        viewmodel.emailValidate.observe(this) {
            validateEmail(it)
        }
        viewmodel.passwordValidate.observe(this) {
            validatePassword(it)
        }
    }

    private fun validatePassword(it: Int?) {
        when (it) {
            AccountViewModel.PASSWORD_EMPTY -> binding.tilPassword.error =
                getString(R.string.err_empty_password)
            AccountViewModel.PASSWORD_VALID ->
                binding.tilPassword.error = null
            else -> binding.tilPassword.error =
                getString(R.string.err_empty_password)
        }
    }

    private fun validateEmail(it: Int?) {
        when (it) {
            AccountViewModel.EMAIL_EMPTY -> binding.tilEmail.error =
                getString(R.string.err_empty_email)
            AccountViewModel.EMAIL_NOT_VALID -> binding.tilEmail.error =
                getString(R.string.err_not_valid_email)
            AccountViewModel.EMAIL_VALID ->
                binding.tilEmail.error = null
            else -> binding.tilEmail.error =
                getString(R.string.err_empty_email)
        }
    }

    private fun isValid(): Boolean {
        if (viewmodel.emailValidate.value != AccountViewModel.EMAIL_VALID) {
            validateEmail(viewmodel.emailValidate.value)
            return false
        }
        if (viewmodel.passwordValidate.value != AccountViewModel.PASSWORD_VALID) {
            validatePassword(viewmodel.passwordValidate.value)
            return false
        }
        return true
    }

    private fun init() {
        binding.btnRegister.setOnClickListener {
            if (isValid())
                viewmodel.register().observe(this) {
                    when (it.status) {
                        Status.LOADING -> showLoading()
                        Status.SUCCESS -> {
                            hideLoading()
                            it.data?.let {
                                Firebase.auth.signOut() // to sign out because after register successful it will auto log in
                                Toast.makeText(
                                    this,
                                    getString(R.string.msg_registration_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        }
                        Status.ERROR -> {
                            hideLoading()
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading() {
        binding.btnRegister.hide()
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.btnRegister.show()
        binding.loading.hide()
    }
}