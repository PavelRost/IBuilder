package com.example.ibuilder.model.building

import com.example.ibuilder.model.indicatorsDB.Human
import com.example.ibuilder.model.indicatorsDB.Resource
import com.example.ibuilder.model.indicatorsDB.TypeResources

abstract class AbstractBuilding {
    abstract val name: String
    abstract val serialNumber: Int
    abstract val typeBuild: TypeBuilding
    open var constructionTime: Int = 0
    open var profit: Int = 0
    open var needWorkers: Int = 0
    open var hiredWorkers: Int = 0
    abstract val typeResources: TypeResources
    val resource = Resource
    val human = Human

    fun constructionStatus(): String {
        if (constructionTime == 0) {
            return "Строительство $name №$serialNumber - завершено!"
        }
        return "$name №$serialNumber осталось: $constructionTime дн."
    }

    fun build(): String {
        if (constructionTime > 0) {
            constructionTime--
        }
        return constructionStatus()
    }

    abstract fun createResources()

    fun addWorkers(): String {
        if (human.freeWorkers < needWorkers) {
            return "Недостаточно свободных рабочих, требуется: $needWorkers"
        }
        hiredWorkers = needWorkers
        human.freeWorkers = -needWorkers
        human.hiredWorkers = needWorkers
        return "На $name №$serialNumber рабочии приступили к производству."
    }

    fun removeWorkers(): String {
        hiredWorkers = 0
        human.freeWorkers = needWorkers
        human.hiredWorkers = -needWorkers
        return "$name №$serialNumber не производит ресурсов, рабочие распущены по домам"
    }
}