package com.example.vipulgoyaltask

import com.example.vipulgoyaltask.core.Resource
import com.example.vipulgoyaltask.domain.model.PortfolioData
import com.example.vipulgoyaltask.domain.repository.PortfolioRepository
import com.example.vipulgoyaltask.domain.usecase.GetPortfolioUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetPortfolioUseCaseTest {
    @Mock
    private lateinit var portfolioRepository: PortfolioRepository
    private lateinit var getPortfolioUseCase: GetPortfolioUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getPortfolioUseCase = GetPortfolioUseCase(portfolioRepository)
    }

    @Test
    fun `invoke returns success when data is not empty`() = runTest {

        val mockData = listOf(
            PortfolioData("AAPL", 10.0, 150.0, 145.0, 148.0)
        )
        `when`(portfolioRepository.getPortfolioList()).thenReturn(mockData)

        val emissions = getPortfolioUseCase().toList()

        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Success)
        val success = emissions[1] as Resource.Success
        assertEquals(mockData, success.data)
    }

    @Test
    fun `invoke returns error when data is empty`() = runTest {
        `when`(portfolioRepository.getPortfolioList()).thenReturn(emptyList())

        val emissions = getPortfolioUseCase().toList()

        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Error)
        val error = emissions[1] as Resource.Error
        assertEquals("No data Found", error.message)
    }
}