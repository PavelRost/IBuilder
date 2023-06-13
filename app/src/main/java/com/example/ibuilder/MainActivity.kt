package com.example.ibuilder

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ibuilder.model.Indicators
import com.example.ibuilder.service.*
import service.BuildingService

class MainActivity : AppCompatActivity() {

    private var isShowDescription = false
    private lateinit var textViewCountResources: TextView
    private lateinit var textViewCountCitizens: TextView
    private lateinit var textViewCountBuilt: TextView
    private lateinit var textViewCurrentEra: TextView
    private lateinit var databaseService: DatabaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) supportActionBar!!.hide()
        initViews()
        databaseService = ViewModelProvider(this)[DatabaseService::class.java]
        databaseService.initAllIndicators()
        updateIndicatorsWithoutView()
        if (!isShowDescription) {
            DialogService.showDescriptionGame(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isShowDescription", isShowDescription)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isShowDescription = savedInstanceState.getBoolean("isShowDescription")
    }

    override fun onResume() {
        super.onResume()
        updateIndicatorsWithoutView()
    }

    fun startNextMove(view: View) {
        Indicators.currentDay = Indicators.currentDay + 1
        val rsl = StringBuilder("Наступил ${Indicators.currentDay} день!\n")
        rsl.append(BuildingService.continueBuild())

        IndicatorService.calculationResourcesPlayer()
        NomadService.nomadAttack(this@MainActivity)
        ExchangeResourcesService.incrementCountOperations()
        TaxService.incrementCountUpdateTaxRate()

        val textViewNotice = findViewById<TextView>(R.id.textView_main_notice)
        textViewNotice.text = rsl
        updateIndicatorsPlayer(view)
        databaseService.saveAllIndicators()
    }

    fun saveGame(view: View) {
        databaseService.saveAllIndicators()
        Toast.makeText(this, "Игра успешно сохранена!", Toast.LENGTH_SHORT).show()
    }

    fun updateEra(view: View) {
        EraService.updateEra(this@MainActivity, databaseService)
        updateIndicatorsPlayer(view)
    }

    fun switchActivityToBuilding(view: View) {
        startActivity(BuildingMainActivity.newIntent(this@MainActivity))
    }

    fun switchActivityToExchangeResources(view: View) {
        startActivity(ExchangeResourcesActivity.newIntent(this@MainActivity))
    }

    fun switchActivityCitizens(view: View) {
        startActivity(CitizensActivity.newIntent(this@MainActivity))
    }

    fun showDescriptionGame(view: View) {
        DialogService.showDescriptionGame(this)
    }

    fun showCostNextEra(view: View) {
        DialogService.showCostNextEra(this)
    }

    private fun updateIndicatorsPlayer(view: View) {
        textViewCountResources.text = IndicatorService.showDisplayResources()
        textViewCountCitizens.text = IndicatorService.showDisplayCitizens()
        textViewCountBuilt.text = IndicatorService.showDisplayBuilt()
        textViewCurrentEra.text = EraService.getCurrentEra().toString()
    }

    private fun updateIndicatorsWithoutView() {
        textViewCountResources.text = IndicatorService.showDisplayResources()
        textViewCountCitizens.text = IndicatorService.showDisplayCitizens()
        textViewCountBuilt.text = IndicatorService.showDisplayBuilt()
        textViewCurrentEra.text = EraService.getCurrentEra().toString()
    }

    private fun initViews() {
        textViewCountResources = findViewById(R.id.textView_main_count_resources)
        textViewCountCitizens = findViewById(R.id.textView_main_count_citizens)
        textViewCountBuilt = findViewById(R.id.textView_main_count_built)
        textViewCurrentEra = findViewById(R.id.textView_main_current_era)
    }
}