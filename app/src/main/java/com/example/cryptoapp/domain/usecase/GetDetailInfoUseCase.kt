package com.example.cryptoapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository
import jakarta.inject.Inject

class GetDetailInfoUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(fSym: String): LiveData<CoinPriceInfo> =
        repository.getPriceInfoAboutCoin(fSym)
}