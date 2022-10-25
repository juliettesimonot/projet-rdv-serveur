package com.example.rdv_serveur.utils

import com.example.rdv_serveur.model.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

object RequestUtils {
    val client = OkHttpClient()
    val gson = Gson()
    private const val TMDB_API_KEY = "b073943e720f89bc518ef7fdb54ca675"
    private const val OMDB_API_KEY = "3208f04e"
    private const val URL_LIST_TMDB = "https://api.themoviedb.org/3/list/8216985?api_key=${TMDB_API_KEY}&language=fr"
    var URL_MOVIE_DETAILS = "https://api.themoviedb.org/3/movie/"
    private const val URL_OMDB_MOVIE = "https://omdbapi.com/?apikey=${OMDB_API_KEY}&i="


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
    fun loadFilmDetailsTMDB():ArrayList<FilmBean>{
        //recuper id from list
        var arrayIdFilms = loadListTMDB()
        var arrayFilmsBeans = arrayListOf<FilmBean>()

        arrayIdFilms.forEach {
            var MOVIE_ID = it.id.toString()
            //Réaliser la requête.
            val json: String = sendGet(URL_MOVIE_DETAILS+MOVIE_ID+"?api_key=${TMDB_API_KEY}&language=fr&append_to_response=videos")

            //Parser le JSON avec le bon bean et GSON
            val data = gson.fromJson(json, TMDBfullMovieDetailsBean::class.java)
            var video =""

            if(!data.videos.results.isEmpty()){
                video=data.videos.results[0].key
            }

            arrayFilmsBeans.add(FilmBean(film_imdb_id = data.imdb_id,  film_img = data.backdrop_path, film_trailer = URL_VIDEO+video))
        }

        //Retourner liste film id
        return arrayFilmsBeans
    }



    fun loadFilmfromOMDB():ArrayList<FilmBean>{
        var arrayFilmsBeans = loadFilmDetailsTMDB()

        arrayFilmsBeans.forEach {
            var imdb_id = it.film_imdb_id

            //Réaliser la requête.
            val json: String = sendGet(URL_OMDB_MOVIE+imdb_id.toString())

            //Parser le JSON avec le bon bean et GSON
            val data = gson.fromJson(json, OMDBFilmBean::class.java)

            it.film_title = data.Title
            it.film_actors = data.Actors
            it.film_country = data.Country
            it.film_description = data.Plot
            it.film_director = data.Director
            it.film_released_date = data.Year
            it.film_runtime = data.Runtime
            it.film_genre = data.Genre
        }

        return arrayFilmsBeans
    }
}