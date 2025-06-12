package com.example.vipulgoyaltask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vipulgoyaltask.data.local.Constants
import com.example.vipulgoyaltask.domain.model.PortfolioData
import javax.annotation.Nonnull

@Entity(tableName = Constants.PORTFOLIO_TABLE_NAME)
data class PortfolioEntity(
    @Nonnull
    @PrimaryKey val symbol: String,
    val quantity: Double,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)



fun PortfolioEntity.mapToPortfolioData(): PortfolioData {
    return PortfolioData(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
}