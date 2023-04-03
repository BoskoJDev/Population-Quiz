package com.example.populationquiz.sqlite

class RepozitorijumTakmicara(private val takmicarDao: TakmicarDao)
{
    val sviTakmicari = this.takmicarDao.izvuciSveTakmicare()

    suspend fun ubaciTakmicara(takmicar: Takmicar) = this.takmicarDao.ubaciTakmicara(takmicar)

    suspend fun azurirajTakmicara(t: Takmicar) = this.takmicarDao.azurirajTakmicara(t)
}