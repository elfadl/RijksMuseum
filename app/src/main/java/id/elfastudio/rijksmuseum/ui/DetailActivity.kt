package id.elfastudio.rijksmuseum.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import id.elfastudio.rijksmuseum.R
import id.elfastudio.rijksmuseum.data.entity.ArtObjectsItem
import id.elfastudio.rijksmuseum.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail)

        intent.getParcelableExtra<ArtObjectsItem>(EXTRA_DATA)?.let {
            Log.i("CHECK", "onCreate: ${it.webImage.url} ${it.longTitle}")
            Glide.with(this)
                .load(it.webImage.url)
                .apply {
                    CenterCrop()
                }
                .placeholder(R.mipmap.ic_launcher_round)
                .into(binding.imgArt)
            binding.tvTitle.text = it.longTitle
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}