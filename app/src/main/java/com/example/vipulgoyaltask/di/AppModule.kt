package com.example.vipulgoyaltask.di

import android.content.Context
import androidx.room.Room
import com.example.vipulgoyaltask.data.api.PortfolioApi
import com.example.vipulgoyaltask.data.local.AppDatabase
import com.example.vipulgoyaltask.data.local.dao.PortfolioDao
import com.example.vipulgoyaltask.data.repository.PortfolioRepositoryImpl
import com.example.vipulgoyaltask.domain.repository.PortfolioRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.vipulgoyaltask.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePortfolioApi(retrofit: Retrofit): PortfolioApi {
        return retrofit.create(PortfolioApi::class.java)
    }

    @Provides
    @Singleton
    fun providePortfolioDao(db: AppDatabase): PortfolioDao = db.portfolioDao()

    @Provides
    @Singleton
    fun providePortfolioRepository(api: PortfolioApi,dao: PortfolioDao): PortfolioRepository {
        return PortfolioRepositoryImpl(api,dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "holdings_db"
        ).build()
    }
}