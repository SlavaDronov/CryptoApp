package com.example.cryptoapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.CoinPriceInfo
import com.example.cryptoapp.domain.usecase.GetDetailInfoUseCase
import com.example.cryptoapp.domain.usecase.GetPriceListUseCase
import com.example.cryptoapp.domain.usecase.LoadDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val getPriceListUseCase: GetPriceListUseCase,
    private val getDetailInfoUseCase: GetDetailInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val priceList: LiveData<List<CoinPriceInfo>> = getPriceListUseCase()

    init {
        viewModelScope.launch {
            try {
                loadDataUseCase()
            } catch (e: Exception) {
                Log.e("CoinViewModel", "loadData failed: ${e.message}", e)
            }
        }
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> =
        getDetailInfoUseCase(fSym)
}