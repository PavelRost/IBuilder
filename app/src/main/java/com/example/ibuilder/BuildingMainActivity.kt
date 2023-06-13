package com.example.ibuilder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.service.DialogService
import service.BuildingService

class BuildingMainActivity : AppCompatActivity() {

    private var typeBuilding: TypeBuilding? = null
    private lateinit var radiosBuilding: RadioGroup
    private lateinit var radiosConsumerBuildings: RadioGroup
    private lateinit var textViewCountProducer: TextView
    private lateinit var textViewCountConsumer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_main)
        initViews()
        if (supportActionBar != null) supportActionBar!!.hide()
        clearProducerBuilding()
        clearConsumerBuilding()
        showWorkingBuildings()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BuildingMainActivity::class.java)
        }
    }

    private fun initViews() {
        radiosBuilding = findViewById(R.id.radios_building)
        radiosConsumerBuildings = findViewById(R.id.radios_consumer_buildings)
        textViewCountProducer = findViewById(R.id.textView_building_producing_buildings)
        textViewCountConsumer = findViewById(R.id.textView_building_consuming_buildings)
    }

    private fun clearProducerBuilding() {
        radiosBuilding.clearCheck()
    }

    private fun clearConsumerBuilding() {
        radiosConsumerBuildings.clearCheck()
    }

    fun selectTypeBuilding(view: View) {
        val isChecked = (view as RadioButton).isChecked
        when (view.id) {
            R.id.radiobutton_building_gold -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_GOLD
                    clearConsumerBuilding()
                }
            }
            R.id.radiobutton_building_stone -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_STONE
                    clearConsumerBuilding()
                }
            }
            R.id.radiobutton_building_wood -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_WOOD
                    clearConsumerBuilding()
                }
            }
            R.id.radiobutton_building_food -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_FOOD
                    clearConsumerBuilding()
                }
            }
            R.id.radiobutton_building_house -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_WORKER
                    clearConsumerBuilding()
                }
            }
            R.id.radiobutton_building_tavern -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_TAVERN
                    clearProducerBuilding()
                }
            }
            R.id.radiobutton_building_circus -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_CIRCUS
                    clearProducerBuilding()
                }
            }
            R.id.radiobutton_building_church -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.CONSUMER_CHURCH
                    clearProducerBuilding()
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
        DialogService.showInformationBuilding(this, typeBuilding!!)
    }

    private fun showWorkingBuildings() {
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