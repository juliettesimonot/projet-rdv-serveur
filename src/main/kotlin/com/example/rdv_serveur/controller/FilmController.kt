package com.example.rdv_serveur.controller

import com.example.rdv_serveur.service.FilmService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


@RestController
class FilmController(val filmService: FilmService) {

    //http://localhost:8080/getAllFilms
    @GetMapping("/getAllFilms")
    fun getAllFilms(response: HttpServletResponse) : Any {
        println("/getAllFilms")
        try {
            return filmService.loadFilms()

        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 512
            return e
        }
    }


}