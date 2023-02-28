package com.example.rickmorty.feature.location.data.local

import androidx.room.*
import com.example.rickmorty.feature.location.data.local.entity.LocationEntity


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations:List<LocationEntity>)

    @Query("SELECT *FROM location_items WHERE id >= :start LIMIT :limit")
    suspend fun getLocationsList(start:Int,limit:Int):List<LocationEntity>

    @Query("SELECT * FROM location_items WHERE id = :id")
    suspend fun getLocationById(id:Int): LocationEntity?

}

@Database(
    entities = [LocationEntity::class],
    version = 1
)
abstract class LocationDatabase:RoomDatabase(){
    abstract val dao:LocationDao
}