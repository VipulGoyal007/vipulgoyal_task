package com.example.vipulgoyaltask.data.api

import com.example.vipulgoyaltask.data.model.PortfolioResponse
import retrofit2.http.GET


interface PortfolioApi {

    @GET("/")
    suspend fun getPortfolioList(): PortfolioResponse
}
