package com.example.populationquiz.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Takmicar::class], version = 3, exportSchema = true)
abstract class Baza : RoomDatabase()
{
    abstract fun takmicarDao(): TakmicarDao

    companion object
    {
        @Volatile
        private var INSTANCA: Baza? = null

        fun baza(kontekst: Context): Baza
        {
            if (this.INSTANCA != null)
                return this.INSTANCA as Baza

            synchronized(this) {
                this.INSTANCA = Room.databaseBuilder(
                    kontekst.applicationContext,
                    Baza::class.java,
                    "baza"
                ).createFromAsset("baza.db").build()

                return this.INSTANCA as Baza
            }
        }
    }
}