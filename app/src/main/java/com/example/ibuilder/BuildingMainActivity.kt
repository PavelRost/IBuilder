package com.example.ibuilder

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ibuilder.model.building.TypeBuilding
import service.BuildingService

class BuildingMainActivity : AppCompatActivity() {

    private var typeBuilding: TypeBuilding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_main)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        findViewById<RadioGroup>(R.id.radios_building).clearCheck()
        findViewById<RadioGroup>(R.id.radios_consumer_buildings).clearCheck()
        showWorkingBuildings()
    }

    fun selectTypeBuilding(view: View) {
        val isChecked = (view as RadioButton).isChecked
        when(view.id) {
            R.id.radiobutton_building_gold -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_GOLD
                    findViewById<RadioGroup>(R.id.radios_consumer_buildings).clearCheck()
                }
            }
            R.id.radiobutton_building_stone -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_STONE
                    findViewById<RadioGroup>(R.id.radios_consumer_buildings).clearCheck()
                }
            }
            R.id.radiobutton_building_wood -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_WOOD
                    findViewById<RadioGroup>(R.id.radios_consumer_buildings).clearCheck()
                }
            }
            R.id.radiobutton_building_food -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_FOOD
                    findViewById<RadioGroup>(R.id.radios_consumer_buildings).clearCheck()
                }
            }
            R.id.radiobutton_building_house -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_WORKER
                    findViewById<RadioGroup>(R.id.radios_consumer_buildings).clearCheck()
                }
            }
            R.id.radiobutton_building_tavern -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_TAVERN
                    findViewById<RadioGroup>(R.id.radios_building).clearCheck()
                }
            }
            R.id.radiobutton_building_circus -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_CIRCUS
                    findViewById<RadioGroup>(R.id.radios_building).clearCheck()
                }
            }
            R.id.radiobutton_building_church -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_CHURCH
                    findViewById<RadioGroup>(R.id.radios_building).clearCheck()
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
            Toast.makeText(
                this,
                BuildingService.addWorkersInBuilding(typeBuilding!!),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun removeWorkersInBuilding(view: View) {
        if (isInitTypeBuilding(view)) {
            Toast.makeText(
                this,
                BuildingService.removeWorkersInBuilding(typeBuilding!!),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun showInformationBuilding(view: View) {
        if (typeBuilding == null) {
            Toast.makeText(this, "Выберите тип здания", Toast.LENGTH_SHORT).show()
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Информация о постройке:")
        builder.setMessage(BuildingService.showInformationBuilding(typeBuilding!!))
        builder.show()
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