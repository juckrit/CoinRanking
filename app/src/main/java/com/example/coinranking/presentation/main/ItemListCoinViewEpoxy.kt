package com.example.coinranking.presentation.main

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.Glide
import com.example.coinranking.R

@ModelView(defaultLayout = R.layout.item_list_coin, fullSpan = true)
class ItemListCoinViewEpoxy @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @ModelProp
    fun setImageUrl(imageUrl: String?) {
//        Glide.with(iv)
//            .load(imageUrl)
//            .placeholder(R.color.black)
//            .into(iv)
    }

//    @CallbackProp
//    fun clickListener(listener: OnClickListener?) {
//        setOnClickListener(listener)
//    }
}