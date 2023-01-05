package com.example.rdv_serveur.controller

import com.example.rdv_serveur.model.FilmAPIBean
import com.example.rdv_serveur.model.FilmBean
import com.example.rdv_serveur.model.ShowTimeBean
import com.example.rdv_serveur.service.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import javax.servlet.http.HttpServletResponse
import kotlin.random.Random


@RestController
class FilmController(val filmService: FilmService, val filmDirectorService : FilmDirectorsService, val makeService: MakeService, val actorService: ActorService, val playService: PlayService, val genreService: GenreService, val haveService: HaveService, val countryService: CountryService, val originService: OriginService, val showTimeService: ShowTimeService) {

    //http://localhost:8080/getAllFilms
    @GetMapping("/getAllFilms")
    fun getAllFilms(response: HttpServletResponse) : Any {
        println("/getAllFilms")
        try {
            return transformFilmAPIBean(1, 0)

        } catch (e: Exception) {
            e.printStackTrace()
            return e
        }
    }


    //http://localhost:8080/getFilmsByDate?date=2023-01-05
    @GetMapping("/getFilmsByDate")
    fun getFilmsByDate(@RequestParam(value = "date") date: String) : Any {
        println("/getFilmsByDate")
        try {
            return transformFilmApiBeanByDate(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return e
        }
    }


    //http://localhost:8080/getOneFilm?filmKey=130
    @GetMapping("/getOneFilm")
    fun getOneFilm(@RequestParam(value = "filmKey") filmKey: Int): Any {
        println("/getAllFilms")
        try {
            return transformFilmAPIBean(2, filmKey)

        } catch (e: Exception) {
            e.printStackTrace()
            return e
        }
    }


    //http://localhost:8080/getShowTimesOneFilm?filmKey=130
    @GetMapping("/getShowTimesOneFilm")
    fun getShowTimesOneFilm(@RequestParam(value = "filmKey") filmKey: Int): Any {
        println("/getShowTimesOneFilm")
        try {
            return showTimeService.loadShowTimesByFilm(filmKey)

        } catch (e: Exception) {
            e.printStackTrace()
            return e
        }
    }

    fun transformFilmApiBeanByDate(date: String):List<FilmAPIBean>{
        var arrayShowTimes = showTimeService.loadShowTimes()
        var arrayFilmAPIBean = arrayListOf<FilmAPIBean>()
        arrayShowTimes.forEach {
            var formatDateTime = it.show_time_date_hour?.toLocalDate().toString()
            if(formatDateTime==date){
                arrayFilmAPIBean.add(transformFilmAPIBean(2, it.film_key?:0)[0])
            }
        }
        return arrayFilmAPIBean.distinct()
    }


    fun transformFilmAPIBean(param : Int, filmKey: Int ):ArrayList<FilmAPIBean>{
            var arrayFilms  =  arrayListOf<FilmBean>().toList()
            var arrayFilmsAPI = arrayListOf<FilmAPIBean>()
            if(param ==1 ){
                arrayFilms = filmService.loadFilms()
            }else if (param ==2){
                arrayFilms = filmService.loadOneFilm(filmKey)
            }


            arrayFilms.forEach{
//                val currentDate = LocalDateTime.now()
//                var hour = Random.nextLong(23)+6
//                var hour1 = Random.nextLong(23)+6
//                var hour2 = Random.nextLong(23)+6
//                //random date
//                val randomDate = currentDate.plusDays(Random.nextLong(15)).plusHours(hour)
//                val randomDate2 = currentDate.plusDays(Random.nextLong(15)).plusHours(hour1)
//                val randomDate3 = currentDate.plusDays(Random.nextLong(15)).plusHours(hour2)
//                val arrayRandomDate = arrayListOf<LocalDateTime>(randomDate, randomDate2, randomDate3)


                //film directors
                var filmDirectorsKey = makeService.loadFilmDirectorsByFilm(it.film_key?:0)
                var arrayFilmDirectors = arrayListOf<String>()

                filmDirectorsKey.forEach {
                   var filmDirector = filmDirectorService.loadFilmDirectorName(it.film_director_key?:0)
                    arrayFilmDirectors.add(filmDirector?:"")
                }

                //actors
                var actorsKey = playService.loadActorsByFilm(it.film_key?:0)
                var arrayActors = arrayListOf<String>()

                actorsKey.forEach {
                    var actor = actorService.loadActorName(it.actor_key?:0)
                    arrayActors.add(actor)
                }

                //genres
                var genresKey = haveService.loadGenresByFilm(it.film_key?:0)
                var arrayGenres = arrayListOf<String>()

                genresKey.forEach {
                    var genre = genreService.loadGenresName(it.genre_key?:0)
                    arrayGenres.add(genre)
                }

                //country
                var countriesKey = originService.loadCountriesByFilm(it.film_key?:0)
                var arrayCountries = arrayListOf<String>()

                countriesKey.forEach {
                    var country = countryService.loadCountryName(it.country_key?:0)
                    arrayCountries.add(country)
                }

                var film = FilmAPIBean(
                    film_key = it.film_key,
                    film_trailer = it.film_trailer,
                    film_img = it.film_img,
                    film_description = it.film_description,
                    film_name = it.film_name,
                    film_imdb_id = it.film_imdb_id,
                    film_runtime = it.film_runtime,
                    film_released_date = it.film_released_date,
//                    film_display_on = arrayRandomDate,
                    film_directors = arrayFilmDirectors,
                    film_actors = arrayActors,
                    film_country = arrayCountries,
                    film_genre = arrayGenres
                )

                arrayFilmsAPI.add(film)
            }

            return arrayFilmsAPI
    }


}