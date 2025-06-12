package com.example.vipulgoyaltask.domain.model

data class PortfolioCalculatedData(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPNL: Double,
    val todayPNL: Double
)
