package com.example.ibuilder.service

import android.content.Context
import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import service.BuildingService

object NomadService {

    fun nomadAttack(context: Context) {
        incrementFrequencyAttackNomad()
        if (checkDayForAttackNomad()) {
            if (Indicators.allResources[TypeResources.GOLD]!! >= 100) {
                Indicators.allResources[TypeResources.GOLD] =
                    Indicators.allResources[TypeResources.GOLD]!! - 100
                DialogService.showNomadTookGold(context)
                return
            } else if (Indicators.totalWorkers > 0) {
                var workersForRemove = 1
                if (Indicators.totalWorkers > 1) {
                    workersForRemove = Indicators.totalWorkers / 2
                }
                BuildingService.convertHiredInFreeWorkers(workersForRemove)
                Indicators.totalWorkers -= workersForRemove
                Indicators.freeWorkers -= workersForRemove
                BuildingService.changeCapacityHouse(workersForRemove)
                DialogService.showNomadTookCitizens(context, workersForRemove)
                return
            }
            DialogService.showGameOver(context)
        }
    }

    private fun checkDayForAttackNomad(): Boolean {
        return Indicators.currentDay > 9 && Indicators.currentDay % Indicators.frequencyAttackNomad == 0
    }

    private fun incrementFrequencyAttackNomad() {
        if (Indicators.frequencyAttackNomad != 5 && Indicators.currentDay >= 100) {
            Indicators.frequencyAttackNomad = 5
        }
    }
}