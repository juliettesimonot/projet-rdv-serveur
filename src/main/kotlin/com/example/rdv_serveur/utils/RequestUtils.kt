package com.example.rdv_serveur.utils

import com.example.rdv_serveur.model.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.LocalDateTime
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import kotlin.collections.ArrayList
import kotlin.random.Random

object RequestUtils {
    val client = OkHttpClient()
    val gson = Gson()
    var allBean = AllBean(arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf())
    private const val TMDB_API_KEY = "b073943e720f89bc518ef7fdb54ca675"
    private const val OMDB_API_KEY = "3208f04e"
    private const val URL_LIST_TMDB = "https://api.themoviedb.org/3/list/8216985?api_key=${TMDB_API_KEY}&language=fr"
    var URL_MOVIE_DETAILS = "https://api.themoviedb.org/3/movie/"


    //GET HTML

    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use {
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }


    //get movies id from imdb
    fun loadListTMDB():ArrayList<TMDBFilmBean>{
        //Réaliser la requête.
        val json: String = sendGet(URL_LIST_TMDB)

        //Parser le JSON avec le bon bean et GSON
        val data = gson.fromJson(json, TMDBBean::class.java)

        //Retourner liste film id
        return data.items
    }


    //fill part filmbean with movie details from imdb
    fun loadFilmDetailsTMDB():AllBean{
        //recuper id from list
        var arrayIdFilms = loadListTMDB()
        //keys
        var filmPK = 100;
        var countryPK = 100;
        var genrePK = 100;
        var actorPK = 100;
        var filmDirectorPK = 100;
        var showTimePK = 100


        arrayIdFilms.forEach {
            var MOVIE_ID = it.id.toString()
            //Réaliser la requête.
            val json: String = sendGet(URL_MOVIE_DETAILS+MOVIE_ID+"?api_key=${TMDB_API_KEY}&language=fr&append_to_response=videos,credits")

            //Parser le JSON avec le bon bean et GSON
            val data = gson.fromJson(json, TMDBfullMovieDetailsBean::class.java)

            //get videos
            var video =""

            if(!data.videos.results.isEmpty()){
                video=data.videos.results[0].key
            }

            //get film

            var film = FilmBean(
                film_key = filmPK+10,
                film_name = data.title,
                film_released_date = data.release_date,
                film_description = data.overview,
                film_imdb_id = data.id.toString(),
                film_img = URL_IMG+data.backdrop_path,
                film_trailer = URL_VIDEO+video,
                film_runtime = data.runtime.toString()
            )

            allBean.arrayFilmsBeans.add(film)

            //get countries

            data.production_countries.forEach {
                var country = CountryBean(countryPK+1,it.name)


                allBean.arrayCountryBeans.add(country)


                allBean.arrayOriginBeans.add(
                    OriginBean(
                        country_key = country.country_key,
                        film_key = film.film_key?:0
                    )
                )
                countryPK+=1
            }

            //get genres
            data.genres.forEach {
                var genre = GenreBean(genrePK+2,it.name)
                allBean.arrayGenreBeans.add(genre)

                allBean.arrayHaveBeans.add(
                    HaveBean(
                        genre_key = genre.genre_key,
                        film_key = film.film_key?:0
                    )
                )
                genrePK+=2
            }

            //get actors
            var arrayTMDBActors = data.credits.cast.filter { it.known_for_department == "Acting" }
            if(arrayTMDBActors.size>3){
                arrayTMDBActors = arrayTMDBActors.slice(0..2)
            }
            arrayTMDBActors.forEach {
                var actor = ActorBean(actorPK+3, it.name)
                allBean.arrayActorBeans.add(actor)

                allBean.arrayPlayBeans.add(
                    PlayBean(
                        actor_key = actor.actor_key,
                        film_key = film.film_key?:0
                    )
                )
                actorPK+=3
            }


            //get film directors
            var arrayTMDBFilmDirectors = data.credits.crew.filter { it.known_for_department == "Acting" || it.known_for_department == "Directing"}
            if(arrayTMDBFilmDirectors.size>1){
                arrayTMDBFilmDirectors = arrayTMDBFilmDirectors.slice(0..0)
            }
            arrayTMDBFilmDirectors.forEach {
                var filmDirector = FilmDirectorBean(filmDirectorPK+4, it.name)
                allBean.arrayFilmDirectorBeans .add(filmDirector)

                allBean.arrayMakeBeans.add(
                    MakeBean(
                        film_director_key = filmDirector.film_director_key,
                        film_key = film.film_key?:0
                    )
                )
                filmDirectorPK+=4
            }
            filmPK+=10



            //fill showTimes
            var i =0
            while (i<3){
                var currentDate = LocalDateTime.now()
                var tickets= 200;
                var date = currentDate.plusDays(Random.nextLong(15)).plusHours(Random.nextLong(23))
                var showTime = ShowTimeBean(showTimePK, date, tickets, film.film_key?:0)
                allBean.arrayShowTimes.add(showTime)
                i++
                showTimePK+=10
            }

        }





        //Retourner liste films
        return allBean
    }


}