package com.example.ibuilder

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Описание игры:")
            builder.setMessage(
                "Вы предводитель поселения, которое избрало путь экономического развития. " +
                        "Вокруг множество кочевников, от которых каждые 10 ходов необходимо откупаться " +
                        "(начиная с 100 хода откупаться придеться каждые 5 ходов). " +
                        "Если не заплатить им дань (100 золота), то они уведут половину жителей деревни. " +
                        "При отсутствии золота и поселенцев - игрок проигрывает." +
                        "\n\nИгра сохраняется автоматически при изменении игровых показателей. " +
                        "Здания, находящиеся на этапе строительства, не сохраняются! Ресурсы не возвращаются!"
            )
            builder.show()
        }
    }

    private fun initViews() {
        textViewCountResources = findViewById(R.id.textView_main_count_resources)
        textViewCountCitizens = findViewById(R.id.textView_main_count_citizens)
        textViewCountBuilt = findViewById(R.id.textView_main_count_built)
        textViewCurrentEra = findViewById(R.id.textView_main_current_era)
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

    private fun updateIndicatorsPlayer(view: View) {
        textViewCountResources.text = IndicatorService.showDisplayResources()
        textViewCountCitizens.text = IndicatorService.showDisplayCitizens()
        textViewCountBuilt.text = IndicatorService.showDisplayBuilt()
        textViewCurrentEra.text = EraService.showCurrentEra()
    }

    private fun updateIndicatorsWithoutView() {
        textViewCountResources.text = IndicatorService.showDisplayResources()
        textViewCountCitizens.text = IndicatorService.showDisplayCitizens()
        textViewCountBuilt.text = IndicatorService.showDisplayBuilt()
        textViewCurrentEra.text = EraService.showCurrentEra()
    }

    fun startNextMove(view: View) {
        Indicators.currentDay = Indicators.currentDay + 1
        val rsl = StringBuilder("Наступил ${Indicators.currentDay} день!\n")
        val textViewNotice = findViewById<TextView>(R.id.textView_main_notice)
        rsl.append(BuildingService.continueBuild())
        IndicatorService.addResources()
        IndicatorService.deleteResources()
        if (Indicators.frequencyAttackNomad != 5 && Indicators.currentDay >= 100) {
            Indicators.frequencyAttackNomad = 5
        }
        if (NomadService.checkDayForAttackNomad()) {
            val tmp = NomadService.nomadAttack()
            if (tmp == "Игра проиграна!") {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Внимание!")
                builder.setMessage("Игра проиграна! Можете играть дальше, конечно... но лучше начните сначала.")
                builder.show()
            }
            rsl.append(tmp)
        }
        if (Indicators.availableOperationExchange == 0) {
            ExchangeResourcesService.incrementCountOperations()
        }
        if (Indicators.availableUpdateTaxRate == 0) {
            TaxService.incrementCountUpdateTaxRate()
        }
        textViewNotice.text = rsl
        updateIndicatorsPlayer(view)
        databaseService.saveAllIndicators()
    }

    fun saveGame(view: View) {
        databaseService.saveAllIndicators()
        Toast.makeText(this, "Игра успешно сохранена!", Toast.LENGTH_SHORT).show()
    }

    fun updateEra(view: View) {
        val textViewNotice = findViewById<TextView>(R.id.textView_main_notice)
        if (EraService.showCurrentEra() == "3") {
            textViewNotice.text = "Достигнута максимальная технологическая эпоха!"
            return
        }
        if (EraService.isAvailableNextEra()) {
            EraService.deleteResourcesForUpdateEra()
            EraService.updateEra()
            updateIndicatorsPlayer(view)
            textViewNotice.text = "Наступила новая эпоха, поздравляем!"
            databaseService.saveAllIndicators()
            return
        }
        textViewNotice.text = "Недостаточно средств для перехода в следующую эпоху"
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
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Описание игры:")
        builder.setMessage(
            "Вы предводитель поселения, которое избрало путь экономического развития. " +
                    "Вокруг множество кочевников, от которых каждые 10 ходов необходимо откупаться " +
                    "(начиная с 100 хода откупаться придеться каждые 5 ходов). " +
                    "Если не заплатить им дань (100 золота), то они уведут половину жителей деревни. " +
                    "При отсутствии золота и поселенцев - игрок проигрывает." +
                    "\n\nИгра сохраняется автоматически при изменении игровых показателей. " +
                    "Здания, находящиеся на этапе строительства, не сохраняются! Ресурсы не возвращаются!"
        )
        builder.show()
    }
}