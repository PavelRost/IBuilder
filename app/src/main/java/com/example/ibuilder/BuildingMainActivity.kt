package com.example.ibuilder

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ibuilder.model.building.TypeBuilding
import service.BuildingService

class BuildingMainActivity : AppCompatActivity() {

    private var typeBuilding: TypeBuilding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_main)
        findViewById<RadioGroup>(R.id.radios_building).clearCheck()
        findViewById<RadioGroup>(R.id.radios_building1).clearCheck()
        showWorkingBuildings()
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
            R.id.radiobutton_building_tavern -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_TAVERN
                }
            }
            R.id.radiobutton_building_circus -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_CIRCUS
                }
            }
            R.id.radiobutton_building_church -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_CHURCH
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

    private fun showWorkingBuildings() {
        val textViewCountProducer =
            findViewById<TextView>(R.id.textView_building_producing_buildings)
        val textViewCountConsumer =
            findViewById<TextView>(R.id.textView_building_consuming_buildings)
        textViewCountProducer.text = BuildingService.showWorkingProducerBuilding()
        textViewCountConsumer.text = BuildingService.showWorkingConsumerBuilding()
    }

    private fun isInitTypeBuilding(view: View) : Boolean {
        if (typeBuilding == null) {
            Toast.makeText(this, "Выберите тип здания", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}