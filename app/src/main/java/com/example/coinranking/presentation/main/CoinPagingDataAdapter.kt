package com.example.coinranking.presentation.main


import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.databinding.ItemListCoinBinding
import com.example.coinranking.databinding.ItemListCoinSeparatorBinding
import com.example.coinranking.presentation.helper.GlideApp
import com.example.coinranking.presentation.helper.SvgSoftwareLayerSetter


class CoinPagingDataAdapter(
    private val mContext: Context
) :
    PagingDataAdapter<CoinCoinsModel, RecyclerView.ViewHolder>(MyCoinsComparator) {

    private val inflater = LayoutInflater.from(mContext)
    private val TYPE_SEPARATOR = 1
    private val TYPE_NORMAL = 2

    object MyCoinsComparator : DiffUtil.ItemCallback<CoinCoinsModel>() {
        override fun areItemsTheSame(oldItem: CoinCoinsModel, newItem: CoinCoinsModel): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoinCoinsModel, newItem: CoinCoinsModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position % 5 == 4) {
            return TYPE_SEPARATOR
        } else {
            return TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL) {
            val holder = ItemListCoinBinding.inflate(inflater, parent, false)
            CoinViewHolder(holder)
        } else {
            val holder = ItemListCoinSeparatorBinding.inflate(inflater, parent, false)
            SeparatorViewHolder(holder)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_SEPARATOR -> {
                getItem(position)?.let { (holder as SeparatorViewHolder).binding(it) }
            }
            else -> {
                getItem(position)?.let { (holder as CoinViewHolder).binding(it) }
            }
        }
    }

    inner class CoinViewHolder(private val binding: ItemListCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(model: CoinCoinsModel) {
            if (model.iconType == "pixel") {
                Glide.with(binding.root.context)
                    .load(model.iconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.iv)
            } else {

                val requestBuilder = GlideApp.with(binding.root.context)
                    .`as`(PictureDrawable::class.java)
                    .transition(withCrossFade())
                    .listener(SvgSoftwareLayerSetter())

                model.iconUrl?.let {
                    requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                        .load(Uri.parse(model.iconUrl))
                        .into(binding.iv)
                }
            }
            binding.tvName.text = model.name
            binding.tvDesc.text = model.description
        }
    }

    inner class SeparatorViewHolder(private val binding: ItemListCoinSeparatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(model: CoinCoinsModel) {
            if (model.iconType == "pixel") {
                Glide.with(binding.root.context)
                    .load(model.iconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.iv)
            } else {
                val requestBuilder = GlideApp.with(binding.root.context)
                    .`as`(PictureDrawable::class.java)
                    .transition(withCrossFade())
                    .listener(SvgSoftwareLayerSetter())

                model.iconUrl?.let {
                    requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                        .load(Uri.parse(model.iconUrl))
                        .into(binding.iv)
                }

            }
            binding.tvName.text = model.name
        }
    }

}