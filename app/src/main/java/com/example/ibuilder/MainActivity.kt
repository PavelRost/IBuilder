package com.example.ibuilder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ibuilder.model.indicatorsDB.OtherIndicators
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.Files
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import model.indicatorsDB.Human
import model.indicatorsDB.Resource
import service.BuildingService
import service.IndicatorService
import java.io.File
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    private val fileNameSaveHuman = "saveHuman.json"
    private val fileNameSaveResources = "saveResources.json"
    private val fileNameSaveOtherIndicators = "otherIndicators.json"
    private val builder = GsonBuilder()
    private val gson: Gson = builder.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateIndicatorsWithoutView()
    }

    fun updateIndicatorsPlayer(view: View) {
        val textViewCountResources = findViewById<TextView>(R.id.textView_main_count_resources)
        val textViewCountCitizens = findViewById<TextView>(R.id.textView_main_count_citizens)
        val textViewCountBuilt = findViewById<TextView>(R.id.textView_main_count_built)
        textViewCountResources.text = IndicatorService.showDisplayResources()
        textViewCountCitizens.text = IndicatorService.showDisplayCitizens()
        textViewCountBuilt.text = IndicatorService.showDisplayBuilt()
    }

    fun startNextMove(view: View) {
        OtherIndicators.currentDay = 1
        val rsl = StringBuilder("Наступил ${OtherIndicators.currentDay} день!\n")
        val textViewNotice = findViewById<TextView>(R.id.textView_main_notice)
        rsl.append(BuildingService.continueBuild())
        IndicatorService.addResources()
        IndicatorService.deleteResources()
        textViewNotice.text = rsl
        updateIndicatorsPlayer(view)
    }

    fun switchActivityToBuilding(view: View) {
        val intent = Intent(this@MainActivity, BuildingMainActivity::class.java)
        startActivity(intent)
    }

    fun switchActivityToExchangeResources(view: View) {
        val intent = Intent(this@MainActivity, ExchangeResourcesActivity::class.java)
        startActivity(intent)
    }

    private fun updateIndicatorsWithoutView() {
        val textViewCountResources = findViewById<TextView>(R.id.textView_main_count_resources)
        val textViewCountCitizens = findViewById<TextView>(R.id.textView_main_count_citizens)
        val textViewCountBuilt = findViewById<TextView>(R.id.textView_main_count_built)
        textViewCountResources.text = IndicatorService.showDisplayResources()
        textViewCountCitizens.text = IndicatorService.showDisplayCitizens()
        textViewCountBuilt.text = IndicatorService.showDisplayBuilt()
    }

    fun saveProgress(view: View) {
        val resourceJson = ResourceJson(
            gold = Resource.gold,
            food = Resource.food,
            wood = Resource.wood,
            stone = Resource.stone
        )
        val humanJson = HumanJson(
            totalWorkers = Human.totalWorkers,
            hiredWorkers = Human.hiredWorkers,
            freeWorkers = Human.freeWorkers
        )
        val otherIndicators = OtherIndicators(
            currentDay = OtherIndicators.currentDay
        )
        try {
            openFileOutput(fileNameSaveHuman, MODE_PRIVATE).write(gson.toJson(humanJson).toByteArray())
            openFileOutput(fileNameSaveResources, MODE_PRIVATE).write(gson.toJson(resourceJson).toByteArray())
            openFileOutput(fileNameSaveOtherIndicators, MODE_PRIVATE).write(gson.toJson(otherIndicators).toByteArray())
            Toast.makeText(this, "Игра успешно сохранена!", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(this, "Ошибка при сохранении данных, попробуйте снова!", Toast.LENGTH_SHORT).show()
        }
    }

    fun loadProgress(view: View) {
        if (fileList().size != 3) {
            Toast.makeText(this, "Сохранения прогресса отсутствуют!", Toast.LENGTH_SHORT).show()
            return
        }

        val path = applicationContext.filesDir
        val fileHuman = File(path, fileNameSaveHuman)
        val fileResources = File(path, fileNameSaveResources)
        val fileIndicators = File(path, fileNameSaveOtherIndicators)

        try {
            println(Files.toString(fileIndicators, StandardCharsets.UTF_8))
            val humanData = gson.fromJson(Files.toString(fileHuman, StandardCharsets.UTF_8), HumanJson::class.java)
            val resourcesData = gson.fromJson(Files.toString(fileResources, StandardCharsets.UTF_8), ResourceJson::class.java)
            val otherIndicators = gson.fromJson(Files.toString(fileIndicators, StandardCharsets.UTF_8), com.example.ibuilder.OtherIndicators::class.java)

            IndicatorService.resetValueIndicators()

            Human.totalWorkers = humanData.totalWorkers
            Human.hiredWorkers = humanData.hiredWorkers
            Human.freeWorkers = humanData.freeWorkers
            Resource.gold = resourcesData.gold
            Resource.food = resourcesData.food
            Resource.stone = resourcesData.stone
            Resource.wood = resourcesData.wood
            OtherIndicators.currentDay = otherIndicators.currentDay

        } catch (ex: Exception) {
            Toast.makeText(this, "Ошибка при загрузке данных, попробуйте снова!", Toast.LENGTH_SHORT).show()
        }
    }

}

data class HumanJson(val totalWorkers: Int, val hiredWorkers: Int, var freeWorkers: Int)

data class ResourceJson(val gold: Int, val food: Int, val wood: Int, val stone: Int)

data class OtherIndicators(val currentDay: Int)