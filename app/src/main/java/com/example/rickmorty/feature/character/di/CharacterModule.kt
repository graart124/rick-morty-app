package com.example.rickmorty.feature.character.di

import android.app.Application
import androidx.room.Room
import com.example.rickmorty.feature.character.data.local.CharacterDatabase
import com.example.rickmorty.feature.character.data.local.Converters
import com.example.rickmorty.feature.character.data.remote.CharacterApi
import com.example.rickmorty.feature.character.data.repository.CharacterRepositoryImp
import com.example.rickmorty.feature.character.data.util.GsonParser
import com.example.rickmorty.feature.character.domain.repository.CharacterRepository
import com.example.rickmorty.feature.character.domain.use_case.*
import com.google.gson.Gson
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
object CharacterModule {

    @Provides
    @Singleton
    fun provideCharactersUseCase(repository: CharacterRepository): CharactersUseCase {
        return CharactersUseCase(
            getCharacter = GetCharacter(repository),
            getCharacters = GetCharacters(repository),
            searchCharacters = SearchCharacters(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(
        db: CharacterDatabase,
        api: CharacterApi
    ): CharacterRepository {
        return CharacterRepositoryImp(db.dao, api)
    }

    @Provides
    @Singleton
    fun provideCharacterDatabase(app: Application): CharacterDatabase {
        return Room.databaseBuilder(
            app, CharacterDatabase::class.java, "character_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideRickMortyApi(retrofit: Retrofit): CharacterApi {
        return retrofit.create(CharacterApi::class.java)
    }

}