package com.example.vipulgoyaltask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vipulgoyaltask.data.local.dao.PortfolioDao
import com.example.vipulgoyaltask.data.local.entity.PortfolioEntity

@Database(entities = [PortfolioEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
}