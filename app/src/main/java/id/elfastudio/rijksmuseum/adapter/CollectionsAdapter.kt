package id.elfastudio.rijksmuseum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.elfastudio.rijksmuseum.R
import id.elfastudio.rijksmuseum.data.entity.ArtObjectsItem
import id.elfastudio.rijksmuseum.databinding.ItemArtBinding

class CollectionsAdapter(private val data: List<ArtObjectsItem>) :
    RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val binding: ItemArtBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_art,
            parent,
            false
        )
        return CollectionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class CollectionsViewHolder(private val binding: ItemArtBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: ArtObjectsItem){
                Glide.with(binding.imgArt.context)
                    .load(item.webImage.url)
                    .apply {
                        CenterCrop()
                        RoundedCorners(18)
                    }
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.imgArt)
                binding.tvTitle.text = item.title

                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClicked(item)
                }
            }
    }

    interface OnItemClickListener{
        fun onItemClicked(item: ArtObjectsItem)
    }

}