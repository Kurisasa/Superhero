package com.kurisani.superhero.module.home

import com.kurisani.superhero.manager.interfaces.SuperheroManager
import com.kurisani.superhero.model.Results
import com.kurisani.superhero.room.dao.SuperheroDao
import com.kurisani.superhero.room.entity.Superhero
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeModelImpl @Inject constructor(
    private val superheroManager: SuperheroManager,
    private val superheroDao: SuperheroDao
) : HomeContract.HomeModel {

    override fun fetchHero(heroName: String): Single<Results> {
       return superheroManager.getSuperHero(heroName)
    }

    override fun saveSuperhero(heroId: Int, name: String,intelligence: Int, strength: Int,speed: Int,durability: Int,power: Int,
                               combat: Int,gender: String,race: String,height: String, weight: String,eyeColor: String,
                               hairColor: String,fullName: String,alterEgos: String, aliases: String,placeOfBirth: String,
                               firstAppearance: String,publisher: String,alignment: String, groupAffiliation: String,relatives: String,
                               imageUrl: String,occupation: String, base: String) {

        Observable.fromCallable {
            val superHero = Superhero(heroId = heroId, name = name, intelligence = intelligence, strength = strength, speed = speed,
                durability = durability, power = power, combat = combat, gender = gender, race = race, height = height,
                weight = weight, eyeColor = eyeColor, hairColor = hairColor, fullName = fullName ,alterEgos = alterEgos, aliases = aliases,
                placeOfBirth = placeOfBirth, firstAppearance = firstAppearance, publisher = publisher, alignment = alignment,
                groupAffiliation = groupAffiliation, relatives = relatives, imageUrl = imageUrl, occupation = occupation, base = base)
            superheroDao.insertSuperhero(superHero)
        }.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun getDatabaseList(): List<Superhero>? {
       return superheroDao.getSuperhero()
    }
}
