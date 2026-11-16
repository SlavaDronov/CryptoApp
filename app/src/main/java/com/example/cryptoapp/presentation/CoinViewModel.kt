package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.CoinPriceInfo
import com.example.cryptoapp.domain.usecase.GetDetailInfoUseCase
import com.example.cryptoapp.domain.usecase.GetPriceListUseCase
import com.example.cryptoapp.domain.usecase.LoadDataUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(
        AppDatabase.getInstance(application).coinPriceInfoDao()
    )
    private val getPriceListUseCase = GetPriceListUseCase(repository)
    private val getDetailInfoUseCase = GetDetailInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val priceList: LiveData<List<CoinPriceInfo>> = getPriceListUseCase()

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> =
        getDetailInfoUseCase(fSym)
}