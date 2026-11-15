package com.example.cryptoapp.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptoapp.api.ApiFactory
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mapper.CoinPriceInfoMapper
import com.example.cryptoapp.domain.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository
import com.google.gson.Gson

class CoinRepositoryImpl(
    private val dao: CoinPriceInfoDao,
    private val mapper: CoinPriceInfoMapper = CoinPriceInfoMapper()
) : CoinRepository {

    override fun getPriceList(): LiveData<List<CoinPriceInfo>> =
        dao.getPriceList().map { list -> list.map { mapper.mapDbToDomain(it) } }

    override fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinPriceInfo> =
        dao.getPriceInfoAboutCoin(fSym).map { mapper.mapDbToDomain(it) }

    override suspend fun loadData() {
        try {
            Log.d(TAG, "loadData() started")

            val coins = ApiFactory.apiService.getCoinsMarkets(perPage = 50)
            Log.d(TAG, "received ${coins.size} coins from CoinGecko")

            if (coins.isEmpty()) {
                Log.w(TAG, "coins list is empty, aborting")
                return
            }

            val domainList = coins.map { mapper.mapDtoToDomain(it) }
            Log.d(TAG, "mapped ${domainList.size} items to domain")

            dao.insertPriceList(domainList.map { mapper.mapDomainToDb(it) })
            Log.d(TAG, "inserted ${domainList.size} items into database")

        } catch (e: Exception) {
            Log.e(TAG, "loadData() failed: ${e.message}", e)
        }
    }
}