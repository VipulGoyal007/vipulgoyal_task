package com.example.vipulgoyaltask.data.repository

import com.example.vipulgoyaltask.core.NetworkUtil
import com.example.vipulgoyaltask.data.api.PortfolioApi
import com.example.vipulgoyaltask.data.local.dao.PortfolioDao
import com.example.vipulgoyaltask.data.local.entity.mapToPortfolioData
import com.example.vipulgoyaltask.domain.model.PortfolioData
import com.example.vipulgoyaltask.domain.repository.PortfolioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val portfolioApi: PortfolioApi,
    private val portfolioDao: PortfolioDao,
   private val networkUtil:NetworkUtil
) : PortfolioRepository {

    override suspend fun getPortfolioList(): List<PortfolioData> {
        if (!networkUtil.getConnectivityStatus()) {
            return  getPortfolioFromDb()
        }
        return try {
            val apiResponce = portfolioApi.getPortfolioList().data
            val dbInsertRequestData = apiResponce.mapToPortfolioEntity()

            /*Upsert data into local db*/
            portfolioDao.insertPortfolioListToDb(dbInsertRequestData)
            dbInsertRequestData.map { it.mapToPortfolioData() }

        } catch (e: Exception) {
            getPortfolioFromDb()
        }
    }

    private suspend fun getPortfolioFromDb(): List<PortfolioData> = withContext(Dispatchers.IO) {
        portfolioDao.getPortfolioListFromDb().map { it.mapToPortfolioData() }
    }

}
