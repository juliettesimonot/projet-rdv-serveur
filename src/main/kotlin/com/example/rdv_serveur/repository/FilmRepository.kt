package com.example.rdv_serveur.repository

import com.example.rdv_serveur.model.FilmBean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FilmRepository: JpaRepository<FilmBean, Int> {
}