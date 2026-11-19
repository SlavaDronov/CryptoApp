package com.example.cryptoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptoapp.api.ApiService
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mapper.CoinPriceInfoMapper
import com.example.cryptoapp.domain.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val dao: CoinPriceInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinPriceInfoMapper
) : CoinRepository {

    override fun getPriceList(): LiveData<List<CoinPriceInfo>> =
        dao.getPriceList().map { list -> list.map { mapper.mapDbToDomain(it) } }

    override fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinPriceInfo> =
        dao.getPriceInfoAboutCoin(fSym).map { mapper.mapDbToDomain(it) }

    override suspend fun loadData() {
        val coins = apiService.getCoinsMarkets(perPage = 50)
        if (coins.isEmpty()) return
        val domainList = coins.map { mapper.mapDtoToDomain(it) }
        dao.insertPriceList(domainList.map { mapper.mapDomainToDb(it) })
    }
}