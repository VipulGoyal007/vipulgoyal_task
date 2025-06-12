package com.example.vipulgoyaltask.domain.repository

import com.example.vipulgoyaltask.domain.model.PortfolioData


interface PortfolioRepository {
    suspend fun getPortfolioList(): List<PortfolioData>

}