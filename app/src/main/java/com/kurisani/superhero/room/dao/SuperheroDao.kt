package com.kurisani.superhero.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kurisani.superhero.room.entity.Superhero

@Dao
interface SuperheroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSuperhero(superhero: Superhero)

    @Query("SELECT * FROM Superhero")
    fun getSuperhero(): List<Superhero>
}