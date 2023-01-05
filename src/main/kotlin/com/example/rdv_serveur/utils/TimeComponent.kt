package com.example.rdv_serveur.utils

import com.example.rdv_serveur.service.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class TimeComponent(val filmService: FilmService, val filmDirectorService: FilmDirectorsService, val makeService: MakeService, val actorService: ActorService, val playService: PlayService, val genreService: GenreService, val haveService: HaveService, val countryService: CountryService, val originService: OriginService, val showTimeService: ShowTimeService) {

//    Action chaque jour à 1h du matin
    @Scheduled(cron = "0 0 1 * * *")
    fun everyDay() {
        filmService.refreshDataFromAPI()
        showTimeService.refreshDataFromAPI()
        filmDirectorService.refreshDataFromAPI()
        actorService.refreshDataFromAPI()
        genreService.refreshDataFromAPI()
        countryService.refreshDataFromAPI()
        makeService.refreshDataFromAPI()
        playService.refreshDataFromAPI()
        haveService.refreshDataFromAPI()
        originService.refreshDataFromAPI()
    }



//    //Action chaque minute
//    @Scheduled(fixedRate = 60000)
//    fun everyMinute() {
//        filmService.refreshDataFromAPI()
//        filmDirectorsService.refreshDataFromAPI()
//        makeService.refreshDataFromAPI()
//        actorService.refreshDataFromAPI()
//        playService.refreshDataFromAPI()
//        genreService.refreshDataFromAPI()
//        haveService.refreshDataFromAPI()
//        countryService.refreshDataFromAPI()
//        originService.refreshDataFromAPI()
//    println("done")
//    }
}