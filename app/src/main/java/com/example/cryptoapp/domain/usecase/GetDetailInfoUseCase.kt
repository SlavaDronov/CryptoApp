package com.example.cryptoapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository

class GetDetailInfoUseCase(private val repository: CoinRepository) {
    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> =
        repository.getPriceInfoAboutCoin(fSym)
}