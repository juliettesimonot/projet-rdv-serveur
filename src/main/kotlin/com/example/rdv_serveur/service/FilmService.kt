package com.example.rdv_serveur.service

import com.example.rdv_serveur.model.FilmBean
import com.example.rdv_serveur.repository.FilmRepository
import com.example.rdv_serveur.utils.RequestUtils
import org.springframework.stereotype.Service

@Service
class FilmService(val filmRep: FilmRepository) {
    //ajouter les données en BDD
    fun refreshDataFromAPI(){
        //delete + rajout en bdd
        filmRep.deleteAll()
        var newData = RequestUtils.loadFilmfromOMDB()
        newData.forEach {
            filmRep.save(it)
        }
        println(newData)

    }

    //Chercher les données en bdd et les retourner
    fun loadFilms():List<FilmBean>{
        var arrayFilms = filmRep.findAll()
        return arrayFilms.toList()
    }

}