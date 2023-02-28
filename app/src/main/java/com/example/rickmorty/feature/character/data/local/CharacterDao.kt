package com.example.rickmorty.feature.character.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.feature.character.data.local.entity.CharacterEntity


@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters:List<CharacterEntity>)

    @Query("SELECT *FROM characterentity WHERE id >= :start LIMIT :limit")
    suspend fun getCharactersList(start:Int,limit:Int):List<CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE id = :id")
    suspend fun getCharacterById(id:Int):CharacterEntity?
}