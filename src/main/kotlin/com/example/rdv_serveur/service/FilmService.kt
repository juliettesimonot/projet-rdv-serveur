package com.example.rdv_serveur.service

import com.example.rdv_serveur.model.*
import com.example.rdv_serveur.repository.*
import com.example.rdv_serveur.utils.RequestUtils
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import kotlin.random.Random

@Service
class FilmService(val filmRep: FilmRepository){

    //ajouter les données en BDD
    fun refreshDataFromAPI(){
        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayFilmsBeans.forEach {
            filmRep.save(it)
        }
    }

    //Chercher les données en bdd et les retourner
    fun loadFilms():List<FilmBean>{
        var arrayFilms = filmRep.findAll()
        return arrayFilms.toList()
    }

    fun loadOneFilm(filmKey:Int):ArrayList<FilmBean>{
        var arrayFilm = arrayListOf<FilmBean>()
        var film = filmRep.findOneFilm(filmKey)
        arrayFilm.add(film)
        return arrayFilm
    }


    fun loadFilmsByDate(date:String):List<FilmBean>{
        var formatter = SimpleDateFormat("yyyy-MM-dd")
        var date = formatter.parse(date)
        var localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        var start = LocalDateTime.of(localDate, LocalTime.MIN)
        var end = LocalDateTime.of(localDate, LocalTime.MAX)
        var films = filmRep.findAllByShowTimeDateHourBetween(start, end)
        return films
    }
}

@Service
class FilmDirectorsService(val filmDirectorRep: FilmDirectorRepository){
    fun refreshDataFromAPI(){
        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd

        newData.arrayFilmDirectorBeans.forEach {
            filmDirectorRep.save(it)
        }
    }

    fun loadFilmDirectorName(filmDirectorId:Int):String?{
        var filmDirector = filmDirectorRep.findFilmDirectorName(filmDirectorId)
        return filmDirector
    }
}

@Service
class MakeService(val makeRep: MakeRepository){
    fun refreshDataFromAPI(){

        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayMakeBeans.forEach {
            makeRep.save(it)
        }
    }

    fun loadFilmDirectorsByFilm(filmId:Int):ArrayList<MakeBean>{
        var arrayFilmDirectors = makeRep.findFilmDirectors(filmId)
        return arrayFilmDirectors
    }
}

@Service
class ActorService(val actorRep: ActorRepository){
    fun refreshDataFromAPI(){


        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayActorBeans.forEach {
            actorRep.save(it)
        }
    }

    fun loadActorName(filmActorId:Int):String{
        var actor = actorRep.findActorName(filmActorId)
        return actor
    }
}

@Service
class PlayService(val playRep: PlayRepository){
    fun refreshDataFromAPI(){
        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayPlayBeans.forEach {
            playRep.save(it)
        }
    }

    fun loadActorsByFilm(filmId:Int):ArrayList<PlayBean>{
        var arrayActors = playRep.findActors(filmId)
        return arrayActors
    }
}

@Service
class GenreService(val genreRep: GenreRepository){
    fun refreshDataFromAPI(){
        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayGenreBeans.forEach {
            genreRep.save(it)
        }
    }

    fun loadGenresName(filmGenreId:Int):String{
        var genre = genreRep.findGenreName(filmGenreId)
        return genre
    }
}


@Service
class HaveService(val haveRep: HaveRepository){
    fun refreshDataFromAPI(){
        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayHaveBeans.forEach {
            haveRep.save(it)
        }
    }

    fun loadGenresByFilm(filmId:Int):ArrayList<HaveBean>{
        var arrayGenres = haveRep.findGenres(filmId)
        return arrayGenres
    }
}


@Service
class CountryService(val countryRep: CountryRepository){
    fun refreshDataFromAPI(){
        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayCountryBeans.forEach {
            countryRep.save(it)
        }
    }

    fun loadCountryName(filmCountryId:Int):String{
        var country = countryRep.findCountryName(filmCountryId)
        return country
    }
}

@Service
class OriginService(val originRep: OriginRepository){
    fun refreshDataFromAPI(){
        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayOriginBeans.forEach {
            originRep.save(it)
        }
    }

    fun loadCountriesByFilm(filmId:Int):ArrayList<OriginBean>{
        var arrayCountries = originRep.findCountries(filmId)
        return arrayCountries
    }
}


@Service
class ShowTimeService(val showTimeRep: ShowTimeRepository){
    fun refreshDataFromAPI(){
        //request new data
        var newData = RequestUtils.loadFilmDetailsTMDB()

        //add/update in bdd
        newData.arrayShowTimes.forEach {
            showTimeRep.save(it)
        }
    }

    fun loadShowTimesByFilm(filmId:Int):ArrayList<ShowTimeBean>{
        var arrayShowTimes = showTimeRep.findShowTimesByFilm(filmId)
        return arrayShowTimes
    }

    fun loadShowTimes():List<ShowTimeBean>{
        var arrayShowTimes = showTimeRep.findAll()
        return arrayShowTimes
    }
}

