package com.example.coinranking.presentation.main

import com.airbnb.epoxy.EpoxyController
import com.example.coinranking.data.GetCoinResponseModel

class Controller : EpoxyController() {

    private var coinModel: GetCoinResponseModel? = null

    fun setCoinModel(coinModel: GetCoinResponseModel?) {
        this.coinModel = coinModel
        requestModelBuild()
    }

    override fun buildModels() {
        ItemListCoinViewEpoxyModel_()
            .id("Coin")
            .addTo(this)
    }
}