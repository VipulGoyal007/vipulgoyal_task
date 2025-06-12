package com.example.vipulgoyaltask.domain.usecase

import com.example.vipulgoyaltask.domain.model.PortfolioCalculatedData
import com.example.vipulgoyaltask.domain.model.PortfolioData
import javax.inject.Inject

class CalculatePortfolioValuesUseCase @Inject constructor() {

    fun calculatePortfolioValues(data: List<PortfolioData>): PortfolioCalculatedData {
        var totalInvestment =0.0
        var currentValue =0.0
        var todayPNL =0.0

        data.forEach {
            totalInvestment=totalInvestment+it.avgPrice * it.quantity
            currentValue=currentValue+it.ltp * it.quantity
            todayPNL=todayPNL+(it.close - it.ltp) * it.quantity
        }
        val totalPNL = currentValue - totalInvestment

        return PortfolioCalculatedData(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPNL = totalPNL,
            todayPNL = todayPNL
        )
    }
}