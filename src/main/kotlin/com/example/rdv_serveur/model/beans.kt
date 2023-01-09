package com.example.rdv_serveur.model

import java.lang.reflect.Constructor
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList


//Bean retourné en JSON
data class FilmAPIBean(
    var film_key:Int?=null,
    var film_name:String?="",
    var film_released_date: String?="",
    var film_description:String?="",
    var film_img:String?="",
    var film_trailer:String?="",
//    var film_display_on: kotlin.collections.List<LocalDateTime>,
    var film_country: List<String>,
    var film_runtime:String?="",
    var film_imdb_id:String?="",
    var film_genre: List<String>,
    var film_directors: kotlin.collections.List<String>,
    var film_actors: List<String>,
)


//beansUtils

data class AllBean(
    //films
    var arrayFilmsBeans : ArrayList<FilmBean>,

    //countries
    var arrayCountryBeans : ArrayList<CountryBean>,
    var arrayOriginBeans :ArrayList<OriginBean>,

    //genres
    var arrayGenreBeans: ArrayList<GenreBean>,
    var arrayHaveBeans :ArrayList<HaveBean>,

    //actors
    var arrayActorBeans: ArrayList<ActorBean>,
    var arrayPlayBeans: ArrayList<PlayBean>,

    //film directors
    var arrayFilmDirectorBeans :ArrayList<FilmDirectorBean>,
    var arrayMakeBeans:ArrayList<MakeBean>,


    //showtimes
    var arrayShowTimes :ArrayList<ShowTimeBean>
)

//---------------------------------//
// beans appel api themoviedatabase
//---------------------------------//
//list film
data class TMDBBean(
    var items:kotlin.collections.ArrayList<TMDBFilmBean>
)

data class TMDBFilmBean(
    var id:Int, //appeler le trailer
)

data class TMDBfullMovieDetailsBean(
    var id:Int,
    var title:String,
    var release_date:String,
    var overview:String,
    var backdrop_path:String,
    var videos:VideoBean,
    var runtime: Int,
    var genres:Array<TMDBGenreBean>,
    var production_countries: Array<TMDBcountryBean>,
    var credits:TMDBCreditBean
)

data class VideoBean(
    var results:kotlin.collections.ArrayList<VideoPathBean>
)

data class VideoPathBean(
    var key:String
)

data class TMDBGenreBean(
    var name: String
)

data class TMDBcountryBean(
    var name: String
)

data class TMDBCreditBean(
    var cast: kotlin.collections.ArrayList<TMDBPersonBean>,
    var crew: kotlin.collections.ArrayList<TMDBPersonBean>
)

data class TMDBPersonBean(
    var known_for_department: String,
    var name: String
)

