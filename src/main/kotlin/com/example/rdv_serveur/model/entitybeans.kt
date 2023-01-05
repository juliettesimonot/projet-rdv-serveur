package com.example.rdv_serveur.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


//---------------------------------//
//beans bdd
//---------------------------------//

data class UserBean(
    var user_key:Int,
    var user_pseudo:String,
    var user_password:String,
    var user_mail:String,
)

//user type

data class UserTypeBean(
    var user_type_key:Int,
    var user_type_name:String
)

data class HaveTypeBean(
    var user_type_key:Int,
    var user_key:Int,
)

//user interactions

data class AddListBean(
    var film_key:Int,
    var user_key:Int
)

data class BookBean(
    var show_time_key:Int,
    var user_key:Int
)


data class CommentBean(
    var film_key:Int,
    var user_key:Int,
    var comment_description:String,
    var comment_note:Int
)


//filmdirector
@Entity
@Table(name = "film_director")
data class FilmDirectorBean(
    @Id
    var film_director_key:Int?=0,
    var film_director_name:String?=""
)


@Entity
@Table(name = "make")
data class MakeBean(
    @Id
    var film_director_key:Int?=0,
    var film_key:Int?=0
)

//actor
@Entity
@Table(name = "actor")
data class ActorBean(
    @Id
    var actor_key:Int?=0,
    var actor_name:String?=""
)
@Entity
@Table(name = "play")
data class PlayBean(
    @Id
    var actor_key:Int?=0,
    var film_key:Int?=0
)

@Entity
@Table(name = "genre")
//genre
data class GenreBean(
    @Id
    var genre_key:Int?=0,
    var genre_name:String?=""
)

@Entity
@Table(name = "have")
data class HaveBean(
    @Id
    var genre_key:Int?=0,
    var film_key:Int?=0
)

@Entity
@Table(name = "country")
//country
data class CountryBean(
    @Id
    var country_key:Int?=0,
    var country_name:String?=""
)

@Entity
@Table(name = "origin")
data class OriginBean(
    @Id
    var country_key:Int?=0,
    var film_key:Int?=0
)

//film
@Entity
@Table(name = "film")
data class FilmBean(
    @Id
    var film_key:Int?=0,
    var film_name:String?="",
    var film_released_date: String?="",
    var film_description:String?="",
    var film_img:String?="",
    var film_trailer:String?="",
    var film_runtime:String?="",
    var film_imdb_id:String?="",
)

@Entity
@Table(name = "show_time")
data class ShowTimeBean(
    @Id
    var show_time_key: Int?=0,
    var show_time_date_hour: LocalDateTime?=null,
    var show_time_tickets:Int?=0,
    var film_key:Int?=0
)

