package com.example.coinranking.presentation.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.databinding.ItemListCoinBinding
import com.example.coinranking.databinding.ItemListCoinSeparatorBinding

class CoinPagingDataAdapter(private val mContext: Context, private val lifecycle: LifecycleOwner) :
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
//        return if (position == 0) {
//            TYPE_SEPARATOR
//        } else {
//            TYPE_NORMAL
//        }
        return TYPE_NORMAL
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
                (holder as SeparatorViewHolder)
            }
            else -> {
                getItem(position)?.let { (holder as CoinViewHolder).binding(it) }
            }
        }
    }

    inner class CoinViewHolder(private val binding: ItemListCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(model: CoinCoinsModel) {

            Glide.with(binding.root.context)
                .load(model.iconUrl)
                .into(binding.iv)
            binding.tvName.text = model.name
            binding.tvDesc.text = model.description
        }
    }

    inner class SeparatorViewHolder(private val binding: ItemListCoinSeparatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding() {

        }
    }

}