package com.example.rickmorty.feature.location.data.repository

import com.example.rickmorty.core.util.Resource
import com.example.rickmorty.feature.character.data.remote.CharacterApi
import com.example.rickmorty.feature.character.domain.models.Character
import com.example.rickmorty.feature.location.data.local.LocationDao
import com.example.rickmorty.feature.location.data.remote.LocationApi
import com.example.rickmorty.feature.location.domain.models.Location
import com.example.rickmorty.feature.location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


private const val PAGE_SIZE = 20

class LocationRepositoryImp(
    private val api: LocationApi,
    private val dao: LocationDao,
    private val characterApi: CharacterApi
) : LocationRepository {

    override fun getLocations(page: Int): Flow<Resource<List<Location>>> = flow {
        emit(Resource.Loading())

        val locations =
            dao.getLocationsList((page - 1) * PAGE_SIZE + 1, PAGE_SIZE).map { it.toLocation() }
        emit(Resource.Loading(locations))

        try {
            val remoteLocations = api.getLocationEntry(page).results.map { it.toLocationEntity() }
            dao.insertLocations(remoteLocations)
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = locations
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = locations
                )
            )
        }

        val newLocations = dao.getLocationsList((page - 1) * PAGE_SIZE + 1, PAGE_SIZE)
        emit(Resource.Success(newLocations.map { it.toLocation() }))
    }

    override suspend fun searchLocations(page: Int, name: String): Resource<List<Location>> {
        val response = try {
            api.getSearchedLocationEntry(page, name)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        dao.insertLocations(response.results.map { it.toLocationEntity() })
        return Resource.Success(response.results.map { it.toLocationEntity().toLocation() })
    }

    override suspend fun getLocationById(id: Int): Location {
        var locationEntity = dao.getLocationById(id)

        if (locationEntity == null) {
            locationEntity = api.getLocationById(id).toLocationEntity()
        }

        val location = locationEntity.toLocation()
        location.residents = getCharactersByIds(locationEntity.charactersIdsForRequest)
        return location
    }

    private suspend fun getCharactersByIds(charactersIdsForRequest: String): List<Character> {
        if(charactersIdsForRequest == "[]") return emptyList()
        return characterApi.getMultipleCharactersList(charactersIdsForRequest).map { it.toCharacterEntry().toCharacter() }
    }

}