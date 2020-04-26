package com.kurisani.superhero.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kurisani.superhero.room.dao.SuperheroDao
import com.kurisani.superhero.room.database.SuperheroDatabase.Companion.VERSION
import com.kurisani.superhero.room.entity.Superhero

@Database(
    entities = [Superhero::class], version = VERSION
)
abstract class SuperheroDatabase : RoomDatabase() {


    abstract fun superheroDao(): SuperheroDao

    companion object {
        private const val DATABASE_NAME = "mysuperheros"
        const val VERSION = 1

        var INSTANCE: SuperheroDatabase? = null

        fun getAppDataBase(context: Context): SuperheroDatabase? {
            if (INSTANCE == null) {
                synchronized(SuperheroDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SuperheroDatabase::class.java,
                        DATABASE_NAME
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}
