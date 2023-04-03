package com.example.populationquiz.sqlite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelPogledaTakmicara(private val aplikacija: Application) : AndroidViewModel(aplikacija)
{
    val takmicari: LiveData<List<Takmicar>>
    private val rep: RepozitorijumTakmicara

    init
    {
        val takmicarDao = Baza.baza(this.aplikacija).takmicarDao()
        this.rep = RepozitorijumTakmicara(takmicarDao)
        this.takmicari = this.rep.sviTakmicari
    }

    fun ubaciTakmicara(t: Takmicar) = viewModelScope.launch(Dispatchers.IO) { rep.ubaciTakmicara(t) }

    fun azurirajTakmicara(t: Takmicar) = viewModelScope.launch(Dispatchers.IO) { rep.azurirajTakmicara(t) }
}