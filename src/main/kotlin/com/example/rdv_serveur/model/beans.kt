package com.example.rdv_serveur.model

import java.lang.reflect.Constructor
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

//---------------------------------//
//beans bdd
//---------------------------------//


data class UserBean(
    var user_key:Int,
    var user_pseudo:String,
    var user_password:String,
    var user_mail:String,
)

data class UserTypeBean(
    var user_type_key:Int,
    var user_type_name:String
)

data class HaveTypeBean(
    var user_type_key:Int,
    var user_key:Int,
)


@Entity
@Table(name = "film")
data class FilmBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var film_key:Int?=null,
    var film_title:String?="",
    var film_released_date: String?="",
    var film_description:String?="",
    var film_img:String?="",
    var film_trailer:String?="",
    var film__display_on: Date?=null,
    var film_director: String?="",
    var film_actors: String?="",
    var film_country: String?="",
    var film_runtime:String?="",
    var film_imdb_id:String?="",
    var film_genre:String?=""
)
//){
//    constructor(film_imdb_id:Int, film_trailer:String,film_img:String) : this(0,"", "", "", film_img, URL_VIDEO+film_trailer, null, "", "", "", "", film_imdb_id)
//}

data class AddListBean(
    var film_key:Int,
    var user_key:Int
)

data class BookBean(
    var film_key:Int,
    var user_key:Int
)


data class CommentBean(
    var film_key:Int,
    var user_key:Int,
    var comment_description:String,
    var comment_note:Int
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
    var imdb_id:String, //appeler openemoviedatabase
    var backdrop_path:String,
    var videos:VideoBean
)

data class VideoBean(
    var results:kotlin.collections.ArrayList<VideoPathBean>
)

data class VideoPathBean(
    var key:String
)



//---------------------------------//
// beans appel api openmoviedatabase
//---------------------------------//
 data class OMDBFilmBean(
    var Title:String?="",
    var Year:String?="",
    var Runtime:String?="",
    var Genre:String?="",
    var Director:String?="",
    var Actors:String?="",
    var Plot:String?="",
    var Country:String?="",
 )
