package com.example.ibuilder

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import model.building.TypeBuilding
import service.BuildingService
import service.IndicatorService

class BuildingMainActivity : AppCompatActivity() {

    private var typeBuilding: TypeBuilding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_main)
        findViewById<RadioGroup>(R.id.radios_building).clearCheck()
        showCountBuildings()
    }

    fun selectTypeBuilding(view: View) {
        val isChecked = (view as RadioButton).isChecked
        when(view.id) {
            R.id.radiobutton_building_gold -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_GOLD
                }
            }
            R.id.radiobutton_building_stone -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_STONE
                }
            }
            R.id.radiobutton_building_wood -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_WOOD
                }
            }
            R.id.radiobutton_building_food -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_FOOD
                }
            }
            R.id.radiobutton_building_house -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_WORKER
                }
            }
        }
    }

    fun beginConstruction(view: View) {
        if(isInitTypeBuilding(view)) {
            Toast.makeText(this, BuildingService.createBuilding(typeBuilding!!), Toast.LENGTH_SHORT).show()
        }
    }

    fun checkStatusConstruction(view: View) {
        if(isInitTypeBuilding(view)) {
            Toast.makeText(this, BuildingService.checkStatusConstruction(typeBuilding!!), Toast.LENGTH_SHORT).show()
        }
    }

    fun addWorkersInBuilding(view: View) {
        if(isInitTypeBuilding(view)) {
            Toast.makeText(this, BuildingService.addWorkersInBuilding(typeBuilding!!), Toast.LENGTH_SHORT).show()
        }
    }

    fun removeWorkersInBuilding(view: View) {
        if(isInitTypeBuilding(view)) {
            Toast.makeText(this, BuildingService.removeWorkersInBuilding(typeBuilding!!), Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllWorkingBuildings(view: View) {
        Toast.makeText(this, BuildingService.getAllWorkingBuildings(), Toast.LENGTH_SHORT).show()
    }

    private fun showCountBuildings() {
        val textViewCountProducer = findViewById<TextView>(R.id.textView_building_producing_buildings)
        val textViewCountConsumer = findViewById<TextView>(R.id.textView_building_consuming_buildings)
        val textViewCountDecorative = findViewById<TextView>(R.id.textView_building_decorative_buildings)
        textViewCountProducer.text = IndicatorService.showDisplayBuilt()
    }

    private fun isInitTypeBuilding(view: View) : Boolean {
        if (typeBuilding == null) {
            Toast.makeText(this, "Выберите тип здания", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}