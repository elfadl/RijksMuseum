package id.elfastudio.rijksmuseum.ui.account

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.elfastudio.rijksmuseum.R
import id.elfastudio.rijksmuseum.databinding.ActivityLoginBinding
import id.elfastudio.rijksmuseum.others.Status
import id.elfastudio.rijksmuseum.others.getColorCompat
import id.elfastudio.rijksmuseum.others.hide
import id.elfastudio.rijksmuseum.others.show
import id.elfastudio.rijksmuseum.ui.BaseActivity
import id.elfastudio.rijksmuseum.ui.HomeActivity
import id.elfastudio.rijksmuseum.viewmodel.AccountViewModel

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewmodel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        autoLogoutPage = false

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
        val msgNotHaveAccount = getString(R.string.msg_not_have_account)
        val msgRegister = getString(R.string.register)
        val msg = "$msgNotHaveAccount $msgRegister"

        val msgSpanned = SpannableString(msg)
        msgSpanned.setSpan(
            ForegroundColorSpan(getColorCompat(R.color.color_register)),
            msg.length - msgRegister.length,
            msg.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvNotHaveAccount.setText(msgSpanned, TextView.BufferType.SPANNABLE)
        binding.tvNotHaveAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            if (isValid())
                viewmodel.login().observe(this) {
                    when (it.status) {
                        Status.LOADING -> showLoading()
                        Status.SUCCESS -> {
                            hideLoading()
                            it.data?.let {
                                startActivity(Intent(this, HomeActivity::class.java))
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

    private fun showLoading() {
        binding.btnLogin.hide()
        binding.tvNotHaveAccount.hide()
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.btnLogin.show()
        binding.tvNotHaveAccount.show()
        binding.loading.hide()
    }
}