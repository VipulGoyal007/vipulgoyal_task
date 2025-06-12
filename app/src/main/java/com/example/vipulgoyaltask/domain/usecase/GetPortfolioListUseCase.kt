package com.example.vipulgoyaltask.domain.usecase

import com.example.vipulgoyaltask.domain.model.PortfolioData
import com.example.vipulgoyaltask.domain.repository.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) {
    suspend operator fun invoke(): Flow<List<PortfolioData>> = flow {
        val res = portfolioRepository.getPortfolioList()
        emit(res)
    }
}

