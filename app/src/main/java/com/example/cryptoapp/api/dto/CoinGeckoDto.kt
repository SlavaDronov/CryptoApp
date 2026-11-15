package com.example.cryptoapp.api.dto

import com.google.gson.annotations.SerializedName

data class CoinGeckoDto(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("current_price") val currentPrice: Double?,
    @SerializedName("market_cap") val marketCap: Long?,
    @SerializedName("total_volume") val totalVolume: Long?,
    @SerializedName("last_updated") val lastUpdated: String?
)