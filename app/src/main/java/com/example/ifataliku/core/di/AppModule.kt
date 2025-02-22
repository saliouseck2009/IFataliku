package com.example.ifataliku.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.ifataliku.data.datasource.local.AppDatabase
import com.example.ifataliku.data.datasource.local.SouvenirInMemoryDataSource
import com.example.ifataliku.data.datasource.local.SouvenirLocalDataSource
import com.example.ifataliku.data.datasource.local.dao.SouvenirDao
import com.example.ifataliku.data.repository.LocationTrackerRepoImpl
import com.example.ifataliku.data.repository.SouvenirRepoImpl
import com.example.ifataliku.domain.repository.LocationTrackerRepo
import com.example.ifataliku.domain.repository.SouvenirRepo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "room_database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSouvenirDao(db: AppDatabase): SouvenirDao {
        return db.souvenirDao()
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun providesLocationTrackerRepo(
        fusedLocationProviderClient: FusedLocationProviderClient,
        @ApplicationContext context: Context
    ): LocationTrackerRepo = LocationTrackerRepoImpl(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = context
    )
    @Singleton
    @Provides
    fun provideSouvenirRepo(dataSource: SouvenirLocalDataSource): SouvenirRepo {
        return SouvenirRepoImpl(dataSource)
    }




//
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(json: Json): Retrofit {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder()
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .addInterceptor(interceptor)
//            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
//                val builder = chain.request().newBuilder()
//                // Log.w("COUNTRY-ID", "${prefs.userCountryId}")
//                // builder.header("Country-Id", "${prefs.userCountryId}").header("User-Id", "${prefs.userId}")
//                return@Interceptor chain.proceed(builder.build())
//            })
//            .build()
//
//        return Retrofit.Builder()
//            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
//            .baseUrl(BuildConfig.apiUrl)
//            .client(client)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
//        return Room
//            .databaseBuilder(
//                context.applicationContext,
//                AppDatabase::class.java,
//                "room_database"
//            )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
}