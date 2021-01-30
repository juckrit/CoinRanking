package com.example.coinranking.presentation.main

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.databinding.ItemListCoinBinding
import com.example.coinranking.databinding.ItemListCoinSeparatorBinding
import java.util.*


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
//        if (position > 0) {
//            if (position % 4 == 0) {
//                return TYPE_SEPARATOR
//            } else {
//                return TYPE_NORMAL
//            }
//        } else {
//            return TYPE_NORMAL
//        }

        if (position % 5 == 4) {
            return TYPE_SEPARATOR
        } else {
            return TYPE_NORMAL
        }

//        return TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL) {
//            val holder = HeaderViewHolder(inflater.inflate(R.layout.item_list_coin_v2_my_coin_type_header, parent, false))
            val holder = ItemListCoinBinding.inflate(inflater, parent, false)
            CoinViewHolder(holder)
        } else {
//            val holder = HistoryViewHolder(inflater.inflate(R.layout.item_list_coin_v2_my_coin_type_history, parent, false))
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

    private fun ImageView.loadSvgOrOthers(myUrl: String?) {
        myUrl?.let {
            if (it.toLowerCase(Locale.ENGLISH).endsWith("svg")) {
                val imageLoader = ImageLoader.Builder(this.context)
                    .componentRegistry {
                        add(SvgDecoder(this@loadSvgOrOthers.context))
                    }
                    .build()
                val request = LoadRequest.Builder(this.context)
                    .data(it)
                    .target(this)
                    .build()
                imageLoader.execute(request)
            } else {
                this.load(myUrl)
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
                val uri: Uri =
                    Uri.parse(model.iconUrl)


                binding.iv.loadSvgOrOthers(model.iconUrl)


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
                val uri: Uri =
                    Uri.parse(model.iconUrl)


                binding.iv.loadSvgOrOthers(model.iconUrl)


            }
            binding.tvName.text = model.name
        }
    }

}