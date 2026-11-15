package com.example.cryptoapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.CoinPriceInfo

interface CoinRepository {
    fun getPriceList(): LiveData<List<CoinPriceInfo>>
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinPriceInfo>
    suspend fun loadData()
}