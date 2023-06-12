package com.example.ibuilder.model.building

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources

abstract class AbstractBuilding {
    abstract val name: String
    abstract val serialNumber: Int
    abstract val typeBuild: TypeBuilding
    open var constructionTime: Int = 0
    open var profit: Int = 0
    open var needWorkers: Int = 0
    open var hiredWorkers: Int = 0
    open var isProfitActivate: Boolean = false
    abstract val typeResources: TypeResources

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
        if (Indicators.freeWorkers < needWorkers) {
            return "Недостаточно свободных рабочих, требуется: $needWorkers"
        }
        hiredWorkers = needWorkers
        Indicators.freeWorkers -= needWorkers
        Indicators.hiredWorkers += needWorkers
        return "На $name №$serialNumber рабочии приступили к производству."
    }

    fun removeWorkers(): String {
        hiredWorkers = 0
        Indicators.freeWorkers += needWorkers
        Indicators.hiredWorkers -= needWorkers
        if (typeResources == TypeResources.SATISFACTION) {
            Indicators.satisfactionCitizens -= profit
            isProfitActivate = false
        }
        return "$name №$serialNumber не производит ресурсов, рабочие распущены по домам"
    }

    fun showInformation(): String {
        var rsl = "Наименование: $name\n" +
                "Время постройки: $constructionTime\n" +
                "Необходимо работников: $needWorkers\n" +
                "Доход за ход: $profit ${typeResources.name}\n"
        if (typeResources == TypeResources.SATISFACTION) {
            rsl += "Потребляет за ход: "
        }
        return rsl
    }
}