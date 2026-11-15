package com.example.cryptoapp.domain

data class CoinPriceInfo(
    val fromSymbol: String,
    val toSymbol: String,
    val price: String,
    val lastUpdate: Long,
    val lastVolume: String,
    val lastVolumeTo: String,
    val lastTradeId: String,
    val volumeDay: String,
    val volumeDayTo: String,
    val volume24Hour: String,
    val volume24HourTo: String,
    val openDay: String,
    val highDay: String,
    val lowDay: String,
    val open24Hour: String,
    val high24Hour: String,
    val low24Hour: String,
    val lastMarket: String,
    val imageUrl: String,
    val formattedTime: String,
    val fullImageUrl: String
)