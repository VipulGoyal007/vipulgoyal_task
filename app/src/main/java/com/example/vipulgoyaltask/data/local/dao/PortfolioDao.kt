package com.example.vipulgoyaltask.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.vipulgoyaltask.data.local.Constants
import com.example.vipulgoyaltask.data.local.entity.PortfolioEntity

@Dao
interface PortfolioDao {
    @Upsert
    suspend fun insertPortfolioListToDb(holdings: List<PortfolioEntity>)

    @Query("SELECT * FROM ${Constants.PORTFOLIO_TABLE_NAME}")
    fun getPortfolioListFromDb():List<PortfolioEntity>
}