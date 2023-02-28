package com.example.rickmorty.feature.character.domain.models

data class SearchCharactersQuery(
    val name:String,
    val status: Status,
    val gender:Gender
){
    fun isDefaultQuery():Boolean{
        return name=="" && status is Status.All && gender is Gender.All
    }

    fun isEqual(searchCharactersQuery: SearchCharactersQuery):Boolean{
        return  searchCharactersQuery.name == name
                && status.value == searchCharactersQuery.status.value
                && gender.value ==  searchCharactersQuery.gender.value
    }
}

sealed class Status(val value:String){
    object Alive:Status("alive")
    object Dead:Status("dead")
    object Unknown:Status("unknown")
    object All:Status("")
}

sealed class Gender(val value: String){
    object Male:Gender("male")
    object Female:Gender("female")
    object Genderless:Gender("genderless")
    object Unknown:Gender("unknown")
    object All:Gender("")
}

