package com.example.vipulgoyaltask.data.repository

import android.util.Log
import com.example.vipulgoyaltask.data.api.PortfolioApi
import com.example.vipulgoyaltask.data.local.Constants
import com.example.vipulgoyaltask.data.local.dao.PortfolioDao
import com.example.vipulgoyaltask.data.local.entity.mapToPortfolioData
import com.example.vipulgoyaltask.domain.model.PortfolioData
import com.example.vipulgoyaltask.domain.repository.PortfolioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val portfolioApi: PortfolioApi,
    private val portfolioDao: PortfolioDao
) : PortfolioRepository {

    override suspend fun getPortfolioList(): List<PortfolioData> {
        return try {
            val apiResponce = portfolioApi.getPortfolioList().data
            val dbInsertRequestData = apiResponce.mapToPortfolioEntity()
            Log.d("case1:::", "yess")
            /*Upsert data into local db*/
            portfolioDao.insertPortfolioListToDb(dbInsertRequestData)
            dbInsertRequestData.map { it.mapToPortfolioData() }

        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.IO) {
                portfolioDao.getPortfolioListFromDb().map { it.mapToPortfolioData() }
            }
        }
    }

}
