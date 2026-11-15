package com.example.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "full_price_list")
data class CoinPriceInfoDbModel(
    @PrimaryKey val fromSymbol: String,
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
    val imageUrl: String
)