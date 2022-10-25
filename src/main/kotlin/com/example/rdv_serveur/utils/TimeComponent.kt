package com.example.rdv_serveur.utils

import com.example.rdv_serveur.service.FilmService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class TimeComponent(val filmService: FilmService) {

//    Action chaque jour à 1h du matin
    @Scheduled(cron = "0 0 1 * * *")
    fun everyDay() {
        filmService.refreshDataFromAPI()
    }



//    //Action chaque minute
//    @Scheduled(fixedRate = 60000)
//    fun everyMinute() {
//        filmService.refreshDataFromAPI()
//    println("done")
//    }
}