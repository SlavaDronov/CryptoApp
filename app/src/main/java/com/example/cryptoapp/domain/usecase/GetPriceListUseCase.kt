package com.example.cryptoapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository
import jakarta.inject.Inject

class GetPriceListUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): LiveData<List<CoinPriceInfo>> = repository.getPriceList()
}