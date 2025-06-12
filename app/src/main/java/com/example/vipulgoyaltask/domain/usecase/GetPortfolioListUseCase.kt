package com.example.vipulgoyaltask.domain.usecase

import com.example.vipulgoyaltask.core.Resource
import javax.inject.Inject
import com.example.vipulgoyaltask.domain.model.PortfolioData
import com.example.vipulgoyaltask.domain.repository.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<PortfolioData>>> = flow {
        emit(Resource.Loading())
        val data = portfolioRepository.getPortfolioList()
        if (data.isNotEmpty())
            emit(Resource.Success(portfolioRepository.getPortfolioList()))
        else
            emit(Resource.Error("No data Found", null, 0))
    }
}

