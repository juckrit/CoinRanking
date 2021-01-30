package com.example.coinranking.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetCoinResponseModel(
    @SerializedName("status") @Expose val status: String,
    @SerializedName("data") @Expose val data: CoinDataModel
    )

data class CoinDataModel(
    @SerializedName("stats") @Expose val stats: CoinStatModel,
    @SerializedName("base") @Expose val base: CoinBaseModel,
    @SerializedName("coins") @Expose val coins:List<CoinCoinsModel>

    )

data class CoinStatModel(
    @SerializedName("total") @Expose val total: Int,
    @SerializedName("offset") @Expose val offset: Int,
    @SerializedName("limit") @Expose val limit: Int,
    @SerializedName("order") @Expose val order: String,
    @SerializedName("base") @Expose val base: String,
    @SerializedName("totalMarkets") @Expose val totalMarkets: Int,
    @SerializedName("totalExchanges") @Expose val totalExchanges: Int,
    @SerializedName("totalMarketCap") @Expose val totalMarketCap: Double,
    @SerializedName("total24hVolume") @Expose val total24hVolume: Double
)

data class CoinBaseModel(
    @SerializedName("symbol") @Expose val symbol: String,
    @SerializedName("sign") @Expose val sign: String
)

data class CoinCoinsModel(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("slug") @Expose val slug: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("description") @Expose val description: String,
    @SerializedName("iconUrl") @Expose val iconUrl: String,
    @SerializedName("iconType") @Expose val iconType: String

    )
