package id.elfastudio.rijksmuseum.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import id.elfastudio.rijksmuseum.R
import id.elfastudio.rijksmuseum.databinding.FragmentProfileBinding
import id.elfastudio.rijksmuseum.ui.SplashScreen

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.auth.currentUser?.let {
            context?.let { ctx ->
                Glide.with(ctx)
                    .load(it.photoUrl)
                    .apply {
                        CircleCrop()
                    }
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.imgProfile)
            }
            binding.tvEmail.text = it.email
        }
        binding.btnLogout.setOnClickListener {
            confirmLogout()
        }
    }

    private fun confirmLogout() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.title_logout_confirmation))
                .setMessage(getString(R.string.msg_logout_confirmation))
                .setPositiveButton(
                    getString(R.string.action_yes)
                ) { dialog, _ ->
                    run {
                        Firebase.auth.signOut()
                        dialog.dismiss()
                        it.finishAffinity()
                        startActivity(Intent(it, SplashScreen::class.java))
                    }
                }
                .setNegativeButton(getString(R.string.action_no), null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}