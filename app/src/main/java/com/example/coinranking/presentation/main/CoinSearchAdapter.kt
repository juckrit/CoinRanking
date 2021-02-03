package com.example.coinranking.presentation.main


import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.coinranking.data.CoinCoinsModel
import com.example.coinranking.databinding.ItemListCoinBinding
import com.example.coinranking.presentation.helper.GlideApp
import com.example.coinranking.presentation.helper.SvgSoftwareLayerSetter
import org.xml.sax.XMLReader


class CoinSearchAdapter(
    private val mContext: Context

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(mContext)
    private lateinit var dataList: List<CoinCoinsModel>

    fun setDataList(newList: List<CoinCoinsModel>) {
        dataList = newList
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ItemListCoinBinding.inflate(inflater, parent, false)
        return CoinViewHolder(holder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        dataList[position].let { (holder as CoinViewHolder).binding(it) }
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
            val desc = model.description
            desc?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.tvDesc.text = Html.fromHtml(desc, null, MyTagHandler())

                } else {
                    binding.tvDesc.text = Html.fromHtml(model.description, null, MyTagHandler())
                }
            }

        }
    }

    class MyTagHandler : Html.TagHandler {
        var first = true
        var parent: String? = null
        var index = 1
        override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader?) {
            if (tag == "ul") {
                parent = "ul"
            } else if (tag == "ol") {
                parent = "ol"
            }
            if (tag == "li") {
                if (parent == "ul") {
                    first = if (first) {
                        output.append("\n\t•")
                        false
                    } else {
                        true
                    }
                } else {
                    if (first) {
                        output.append("\n\t$index. ")
                        first = false
                        index++
                    } else {
                        first = true
                    }
                }
            }
        }
    }


//    inner class SeparatorViewHolder(private val binding: ItemListCoinSeparatorBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun binding(model: CoinCoinsModel) {
//            if (model.iconType == "pixel") {
//                Glide.with(binding.root.context)
//                    .load(model.iconUrl)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(binding.iv)
//            } else {
//                val requestBuilder = GlideApp.with(binding.root.context)
//                    .`as`(PictureDrawable::class.java)
//                    .transition(withCrossFade())
//                    .listener(SvgSoftwareLayerSetter())
//
//                model.iconUrl?.let {
//                    requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .load(Uri.parse(model.iconUrl))
//                        .into(binding.iv)
//                }
//
//            }
//            binding.tvName.text = model.name
//        }
//    }




}