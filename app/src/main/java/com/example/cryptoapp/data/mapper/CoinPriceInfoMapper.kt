package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.api.dto.CoinGeckoDto
import com.example.cryptoapp.data.database.CoinPriceInfoDbModel
import com.example.cryptoapp.domain.CoinPriceInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinPriceInfoMapper {

    fun mapDtoToDomain(dto: CoinGeckoDto): CoinPriceInfo {
        return CoinPriceInfo(
            fromSymbol = dto.symbol.uppercase(),
            toSymbol = "USD",
            price = dto.currentPrice?.toString() ?: "",
            lastUpdate = 0L,
            lastVolume = dto.totalVolume?.toString() ?: "",
            lastVolumeTo = "",
            lastTradeId = "",
            volumeDay = "",
            volumeDayTo = "",
            volume24Hour = "",
            volume24HourTo = "",
            openDay = "",
            highDay = "",
            lowDay = "",
            open24Hour = "",
            high24Hour = "",
            low24Hour = "",
            lastMarket = "",
            imageUrl = dto.image,
            formattedTime = formatCurrentTime(),
            fullImageUrl = dto.image
        )
    }

    fun mapDbToDomain(db: CoinPriceInfoDbModel): CoinPriceInfo {
        return CoinPriceInfo(
            fromSymbol = db.fromSymbol,
            toSymbol = db.toSymbol,
            price = db.price,
            lastUpdate = db.lastUpdate,
            lastVolume = db.lastVolume,
            lastVolumeTo = db.lastVolumeTo,
            lastTradeId = db.lastTradeId,
            volumeDay = db.volumeDay,
            volumeDayTo = db.volumeDayTo,
            volume24Hour = db.volume24Hour,
            volume24HourTo = db.volume24HourTo,
            openDay = db.openDay,
            highDay = db.highDay,
            lowDay = db.lowDay,
            open24Hour = db.open24Hour,
            high24Hour = db.high24Hour,
            low24Hour = db.low24Hour,
            lastMarket = db.lastMarket,
            imageUrl = db.imageUrl,
            formattedTime = formatTime(db.lastUpdate),
            fullImageUrl = db.imageUrl
        )
    }

    fun mapDomainToDb(domain: CoinPriceInfo): CoinPriceInfoDbModel {
        return CoinPriceInfoDbModel(
            fromSymbol = domain.fromSymbol,
            toSymbol = domain.toSymbol,
            price = domain.price,
            lastUpdate = domain.lastUpdate,
            lastVolume = domain.lastVolume,
            lastVolumeTo = domain.lastVolumeTo,
            lastTradeId = domain.lastTradeId,
            volumeDay = domain.volumeDay,
            volumeDayTo = domain.volumeDayTo,
            volume24Hour = domain.volume24Hour,
            volume24HourTo = domain.volume24HourTo,
            openDay = domain.openDay,
            highDay = domain.highDay,
            lowDay = domain.lowDay,
            open24Hour = domain.open24Hour,
            high24Hour = domain.high24Hour,
            low24Hour = domain.low24Hour,
            lastMarket = domain.lastMarket,
            imageUrl = domain.imageUrl
        )
    }

    private fun formatCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date())
    }

    private fun formatTime(timestamp: Long?): String {
        if (timestamp == null || timestamp == 0L) return formatCurrentTime()
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}