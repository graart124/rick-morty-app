package com.example.rickmorty.feature.character.data.repository

import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.character.data.local.CharacterDao
import com.example.rickmorty.feature.character.data.remote.CharacterApi
import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.feature.character.domain.repository.CharacterRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

const val PAGE_SIZE = 20

@ActivityScoped
class CharacterRepositoryImp(
    private val dao: CharacterDao,
    private val api: CharacterApi
) : CharacterRepository {

    override fun getCharacters(page: Int): Flow<Resource<List<Character>>> = flow {

        emit(Resource.Loading())
        val characters =
            dao.getCharactersList((page - 1) * PAGE_SIZE + 1, PAGE_SIZE).map { it.toCharacter() }
        emit(Resource.Loading(data = characters))

        try {
            val remoteCharacters = api.getCharactersEntry(page)
            dao.insertCharacters(remoteCharacters.results.map { it.toCharacterEntry() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = characters
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = characters
                )
            )
        }

        val newCharactersInfo =
            dao.getCharactersList((page - 1) * PAGE_SIZE + 1, PAGE_SIZE).map { it.toCharacter() }
        emit(Resource.Success(newCharactersInfo))
    }

    override suspend fun searchCharacters(
        page: Int,
        name: String?,
        gender: String?,
        status: String?
    ): Resource<List<Character>> {
        val response = try {
            api.getFilteredCharactersList(page, name, gender, status)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        dao.insertCharacters(response.results.map { it.toCharacterEntry() })
        return Resource.Success(response.results.map { it.toCharacterEntry().toCharacter() })
    }

    override suspend fun getCharacterById(id: Int): Character {
        return dao.getCharacterById(id)?.toCharacter()
            ?: api.getMultipleCharactersList("[$id]")
                .map { it.toCharacterEntry().toCharacter() }.first()
    }
}