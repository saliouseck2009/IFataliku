package com.example.ifataliku.core.di

import com.example.ifataliku.data.datasource.local.AppDatabase
import com.example.ifataliku.data.datasource.local.SouvenirInMemoryDataSource
import com.example.ifataliku.data.datasource.local.SouvenirLocalDataSource
import com.example.ifataliku.data.datasource.local.SouvenirRoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideRoomDataSource(appDatabase: AppDatabase): SouvenirLocalDataSource {
        return SouvenirRoomDataSource(appDatabase)
    }

//    @Provides
//    @Singleton
//    fun provideInMemoryDataSource(): SouvenirLocalDataSource {
//        return SouvenirInMemoryDataSource()
//    }

}