package com.example.ibuilder.service

import com.example.ibuilder.model.indicatorsDB.Human
import com.example.ibuilder.model.indicatorsDB.OtherIndicators
import com.example.ibuilder.model.indicatorsDB.Resource
import com.example.ibuilder.model.indicatorsDB.TypeResources
import service.BuildingService

object NomadService {

    fun nomadAttack(): String {
        if (Resource.allResources[TypeResources.GOLD]!! >= 100) {
            Resource.allResources[TypeResources.GOLD] =
                Resource.allResources[TypeResources.GOLD]!! - 100
            return "Вы заплатили дань кочевникам: 100 золотых монет\n"
        } else if (Human.totalWorkers > 0) {
            var workersForRemove = 1
            if (Human.totalWorkers > 1) {
                workersForRemove = Human.totalWorkers / 2
            }
            BuildingService.convertHiredInFreeWorkers(workersForRemove)
            Human.totalWorkers = -workersForRemove
            Human.freeWorkers = -workersForRemove
            BuildingService.changeCapacityHouse(workersForRemove)
            return "Кочевники увели в рабство $workersForRemove жителей"
        }
        return "Игра проиграна!"
    }

    fun checkDayForAttackNomad(): Boolean {
        return OtherIndicators.currentDay > 9 && OtherIndicators.currentDay % OtherIndicators.frequencyAttackNomad == 0
    }
}