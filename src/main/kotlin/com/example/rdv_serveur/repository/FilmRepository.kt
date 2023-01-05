package com.example.rdv_serveur.repository

import com.example.rdv_serveur.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface FilmRepository: JpaRepository<FilmBean, Int> {
    @Query("from FilmBean where film_key =?1")
    fun findOneFilm(filmId: Int): FilmBean
}

@Repository
interface FilmDirectorRepository: JpaRepository<FilmDirectorBean, Int> {
    @Query("select film_director_name from FilmDirectorBean where film_director_key =?1")
    fun findFilmDirectorName(filmDirectorId: Int): String
}

@Repository
interface MakeRepository: JpaRepository<MakeBean, Int> {
    @Query("from MakeBean where film_key =?1")
    fun findFilmDirectors(filmId: Int): ArrayList<MakeBean>
}

@Repository
interface ActorRepository: JpaRepository<ActorBean, Int> {
    @Query("select actor_name from ActorBean where actor_key =?1")
    fun findActorName(filmActorId: Int): String
}

@Repository
interface PlayRepository: JpaRepository<PlayBean, Int> {
    @Query("from PlayBean where film_key =?1")
    fun findActors(filmId: Int): ArrayList<PlayBean>
}

@Repository
interface GenreRepository: JpaRepository<GenreBean, Int> {
    @Query("select genre_name from GenreBean where genre_key =?1")
    fun findGenreName(filmGenreId: Int): String
}

@Repository
interface HaveRepository: JpaRepository<HaveBean, Int> {
    @Query("from HaveBean where film_key =?1")
    fun findGenres(filmId: Int): ArrayList<HaveBean>
}

@Repository
interface CountryRepository: JpaRepository<CountryBean, Int> {
    @Query("select country_name from CountryBean where country_key =?1")
    fun findCountryName(filmCountryId: Int): String
}

@Repository
interface OriginRepository: JpaRepository<OriginBean, Int> {
    @Query("from OriginBean where film_key =?1")
    fun findCountries(filmId: Int): ArrayList<OriginBean>
}

@Repository
interface ShowTimeRepository: JpaRepository<ShowTimeBean, Int> {
    @Query("from ShowTimeBean where film_key =?1")
    fun findShowTimesByFilm(filmId: Int): ArrayList<ShowTimeBean>

}