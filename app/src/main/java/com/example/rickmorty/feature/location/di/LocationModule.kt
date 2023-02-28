package com.example.rickmorty.feature.location.di

import android.app.Application
import androidx.room.Room
import com.example.rickmorty.feature.character.data.remote.CharacterApi
import com.example.rickmorty.feature.location.data.local.LocationDatabase
import com.example.rickmorty.feature.location.data.remote.LocationApi
import com.example.rickmorty.feature.location.data.repository.LocationRepositoryImp
import com.example.rickmorty.feature.location.domain.repository.LocationRepository
import com.example.rickmorty.feature.location.domain.use_case.GetLocation
import com.example.rickmorty.feature.location.domain.use_case.GetLocations
import com.example.rickmorty.feature.location.domain.use_case.LocationUseCase
import com.example.rickmorty.feature.location.domain.use_case.SearchLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationUseCase(repository: LocationRepository):LocationUseCase{
        return LocationUseCase(
            getLocation = GetLocation(repository),
            searchLocation = SearchLocation(repository),
            getLocations = GetLocations(repository)
        )
    }

    @Provides
    @Singleton
    fun provideLocationRepository(api:LocationApi,db:LocationDatabase,characterApi:CharacterApi):LocationRepository{
        return LocationRepositoryImp(api,db.dao,characterApi)
    }

    @Provides
    @Singleton
    fun provideLocationApi(retrofit: Retrofit):LocationApi{
        return retrofit.create(LocationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationDatabase(app:Application):LocationDatabase{
        return Room.databaseBuilder(
            app,
            LocationDatabase::class.java,
            "location_db"
        ).build()
    }
}