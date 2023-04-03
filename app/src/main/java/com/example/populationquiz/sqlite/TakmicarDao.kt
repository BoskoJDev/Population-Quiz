package com.example.populationquiz.sqlite

import androidx.room.*

@Dao
interface TakmicarDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun ubaciTakmicara(takmicar: Takmicar)

    @Update
    suspend fun azurirajTakmicara(takmicar: Takmicar)

    @Query("SELECT * FROM takmicari")
    fun izvuciSveTakmicare(): androidx.lifecycle.LiveData<List<Takmicar>>
}