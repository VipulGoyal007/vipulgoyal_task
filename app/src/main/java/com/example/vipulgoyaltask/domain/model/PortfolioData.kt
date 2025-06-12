package com.example.vipulgoyaltask.domain.model

data class PortfolioData(
    val symbol: String,
    val quantity: Double,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
){
    fun getPL(): Double {
        return (close - ltp) * (quantity)
    }
}