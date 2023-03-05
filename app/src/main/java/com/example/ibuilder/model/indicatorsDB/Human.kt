package model.indicatorsDB

object Human {

    const val useFood: Int = 2
    const val useGold: Int = 1

    var totalWorkers: Int = 0
        set(value) {
            field += value
        }
    var hiredWorkers: Int = 0
        set(value) {
            field += value
        }
    var freeWorkers: Int = 0
        set(value) {
            field += value
        }




}
