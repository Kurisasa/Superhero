package com.kurisani.superhero.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Superhero (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val heroId: Int,
    val name: String,
    val intelligence: Int? = null,
    val strength: Int? = null,
    val speed: Int? = null,
    val durability: Int? = null,
    val power: Int? = null,
    val combat: Int? = null,
    val gender: String? = null,
    val race: String? = null,
    val height: String? = null,
    val weight: String? = null,
    val eyeColor: String? = null,
    val hairColor: String? = null,
    val fullName: String? = null,
    val alterEgos: String? = null,
    val aliases: String? = null,
    val placeOfBirth: String? = null,
    val firstAppearance: String? = null,
    val publisher: String? = null,
    val alignment: String? = null,
    val groupAffiliation: String? = null,
    val relatives: String? = null,
    val imageUrl: String? = null,
    val occupation: String? = null,
    val base: String? = null
)