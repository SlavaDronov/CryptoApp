package com.example.cryptoapp.api

import com.example.cryptoapp.BuildConfig
import com.example.cryptoapp.api.dto.CoinGeckoDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("coins/markets")
    suspend fun getCoinsMarkets(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1,
        @Header("x-cg-demo-api-key") apiKey: String = BuildConfig.COINGECKO_API_KEY
    ): List<CoinGeckoDto>
}