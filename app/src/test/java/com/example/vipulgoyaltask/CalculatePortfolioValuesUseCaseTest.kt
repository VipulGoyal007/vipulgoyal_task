package com.example.vipulgoyaltask


import com.example.vipulgoyaltask.domain.model.PortfolioData
import com.example.vipulgoyaltask.domain.usecase.CalculatePortfolioValuesUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatePortfolioValuesUseCaseTest {

    private lateinit var useCase: CalculatePortfolioValuesUseCase

    @Before
    fun setup() {
        useCase = CalculatePortfolioValuesUseCase()
    }

    @Test
    fun `calculate portfolio values with empty data`() {
        val data = emptyList<PortfolioData>()
        val result = useCase.calculatePortfolioValues(data)

        assertEquals(0.0, result.currentValue, 0.001)
        assertEquals(0.0, result.totalInvestment, 0.001)
        assertEquals(0.0, result.totalPNL, 0.001)
        assertEquals(0.0, result.todayPNL, 0.001)
    }

    @Test
    fun `calculate portfolio values with single stock`() {
        val data = listOf(
            PortfolioData(
                symbol = "test1",
                ltp = 105.0,
                close = 100.0,
                avgPrice = 95.0,
                quantity = 10.0
            )
        )
        val result = useCase.calculatePortfolioValues(data)
        assertEquals(1050.0, result.currentValue, 0.001)
        assertEquals(950.0, result.totalInvestment, 0.001)
        assertEquals(100.0, result.totalPNL, 0.001)
        assertEquals(-50.0, result.todayPNL, 0.001)
    }

    @Test
    fun `calculate portfolio values with multiple stocks`() {
        val data = listOf(
            PortfolioData( symbol = "test1",ltp = 100.0, close = 110.0, avgPrice = 90.0, quantity = 5.0),
            PortfolioData( symbol = "test2",ltp = 200.0, close = 190.0, avgPrice = 180.0, quantity = 2.0)
        )

        val result = useCase.calculatePortfolioValues(data)

        val expectedCurrentValue = (100.0 * 5) + (200.0 * 2) // 500 + 400 = 900
        val expectedInvestment = (90.0 * 5) + (180.0 * 2) // 450 + 360 = 810
        val expectedPNL = expectedCurrentValue - expectedInvestment // 90
        val expectedTodayPNL = ((110.0 - 100.0) * 5) + ((190.0 - 200.0) * 2) // 50 - 20 = 30

        assertEquals(expectedCurrentValue, result.currentValue, 0.001)
        assertEquals(expectedInvestment, result.totalInvestment, 0.001)
        assertEquals(expectedPNL, result.totalPNL, 0.001)
        assertEquals(expectedTodayPNL, result.todayPNL, 0.001)
    }

    @Test
    fun `calculate portfolio values with negative quantity or prices`() {
        val data = listOf(
            PortfolioData(symbol = "test1",ltp = -100.0, close = 110.0, avgPrice = -90.0, quantity = -5.0)
        )

        val result = useCase.calculatePortfolioValues(data)

        val expectedCurrentValue = -100.0 * -5.0 // 500
        val expectedInvestment = -90.0 * -5.0 // 450
        val expectedPNL = expectedCurrentValue - expectedInvestment // 50
        val expectedTodayPNL = (110.0 - -100.0) * -5.0 // -1050

        assertEquals(expectedCurrentValue, result.currentValue, 0.001)
        assertEquals(expectedInvestment, result.totalInvestment, 0.001)
        assertEquals(expectedPNL, result.totalPNL, 0.001)
        assertEquals(expectedTodayPNL, result.todayPNL, 0.001)
    }

}