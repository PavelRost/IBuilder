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

class BuildingMainActivity : AppCompatActivity() {

    private var typeBuilding: TypeBuilding? = null
    private lateinit var textViewNoticeBuilding: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_main)
        findViewById<RadioGroup>(R.id.radios).clearCheck()
        textViewNoticeBuilding = findViewById(R.id.textView_notice_building)
    }

    fun selectTypeBuilding(view: View) {
        val isChecked = (view as RadioButton).isChecked
        when(view.id) {
            R.id.radiobutton_gold -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_GOLD
                }
            }
            R.id.radiobutton_stone -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_STONE
                }
            }
            R.id.radiobutton_wood -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_WOOD
                }
            }
            R.id.radiobutton_food -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_FOOD
                }
            }
            R.id.radiobutton_house -> {
                if (isChecked) {
                    typeBuilding = TypeBuilding.PRODUCER_WORKER
                }
            }
        }
    }

    fun beginConstruction(view: View) {
        if(isInitTypeBuilding(view)) {
            textViewNoticeBuilding.text = BuildingService.createBuilding(typeBuilding!!)
        }
    }

    fun checkStatusConstruction(view: View) {
        if(isInitTypeBuilding(view)) {
            textViewNoticeBuilding.text = BuildingService.checkStatusConstruction(typeBuilding!!)
        }
    }

    fun addWorkersInBuilding(view: View) {
        if(isInitTypeBuilding(view)) {
            textViewNoticeBuilding.text = BuildingService.addWorkersInBuilding(typeBuilding!!)
        }
    }

    fun removeWorkersInBuilding(view: View) {
        if(isInitTypeBuilding(view)) {
            textViewNoticeBuilding.text = BuildingService.removeWorkersInBuilding(typeBuilding!!)
        }
    }

    fun getAllWorkingBuildings(view: View) {
        textViewNoticeBuilding.text = BuildingService.getAllWorkingBuildings()
    }

    private fun isInitTypeBuilding(view: View) : Boolean {
        if (typeBuilding == null) {
            Toast.makeText(this, "Выберите тип здания", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}