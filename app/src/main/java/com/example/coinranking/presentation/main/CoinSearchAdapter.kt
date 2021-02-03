package com.example.coinranking.presentation.main


import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Build
import android.text.Html
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


class CoinSearchAdapter(
    private val mContext: Context

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(mContext)
    private val TYPE_SEPARATOR = 1
    private val TYPE_NORMAL = 2
    private lateinit var dataList:List<CoinCoinsModel>

    fun setDataList(newList: List<CoinCoinsModel>){
        dataList = newList
    }

    override fun getItemCount(): Int {
        return  dataList.size
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
                dataList[position].let { (holder as SeparatorViewHolder).binding(it) }
            }
            else -> {
                dataList[position].let { (holder as CoinViewHolder).binding(it) }
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvDesc.setText(Html.fromHtml(model.description, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvDesc.setText(Html.fromHtml(model.description));
            }
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