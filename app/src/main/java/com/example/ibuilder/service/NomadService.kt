package com.example.ibuilder.service

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import service.BuildingService

object NomadService {

    fun nomadAttack(): String {
        if (Indicators.allResources[TypeResources.GOLD]!! >= 100) {
            Indicators.allResources[TypeResources.GOLD] =
                Indicators.allResources[TypeResources.GOLD]!! - 100
            return "Вы заплатили дань кочевникам: 100 золотых монет\n"
        } else if (Indicators.totalWorkers > 0) {
            var workersForRemove = 1
            if (Indicators.totalWorkers > 1) {
                workersForRemove = Indicators.totalWorkers / 2
            }
            BuildingService.convertHiredInFreeWorkers(workersForRemove)
            Indicators.totalWorkers = Indicators.totalWorkers - workersForRemove
            Indicators.freeWorkers = Indicators.freeWorkers - workersForRemove
            BuildingService.changeCapacityHouse(workersForRemove)
            return "Кочевники увели в рабство $workersForRemove жителей"
        }
        return "Игра проиграна!"
    }

    fun checkDayForAttackNomad(): Boolean {
        return Indicators.currentDay > 9 && Indicators.currentDay % Indicators.frequencyAttackNomad == 0
    }
}