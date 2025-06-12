package com.example.vipulgoyaltask.data.model

import com.example.vipulgoyaltask.data.local.entity.PortfolioEntity

data class PortfolioDataModel(
    val userHolding: List<PortfolioDto>
){
    fun mapToPortfolioEntity(): List<PortfolioEntity> {
       return userHolding.map { item -> with(item) { PortfolioEntity(symbol, quantity, ltp, avgPrice, close) } }
    }
}

data class PortfolioDto(
    val symbol: String,
    val quantity: Double,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)
