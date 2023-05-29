package com.example.ibuilder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ibuilder.model.indicatorsDB.ExchangeIndicators
import com.example.ibuilder.model.indicatorsDB.OtherIndicators
import com.example.ibuilder.service.*
import service.BuildingService

class MainActivity : AppCompatActivity() {

    private var isShowDescription = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        updateIndicatorsWithoutView()

        if (!isShowDescription) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Описание игры:")
            builder.setMessage(
                "Вы предводитель поселения, которое избрало путь экономического развития. " +
                        "Вокруг множество кочевников, от которых каждые 10 ходов необходимо откупаться " +
                        "(начиная с 100 хода откупаться придеться каждые 5 ходов). " +
                        "Если не заплатить им дань (100 золота), то они уведут половину жителей деревни. " +
                        "При отсутствии золота и поселенцев - игрок проигрывает."
            )
            builder.show()
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

    fun updateIndicatorsPlayer(view: View) {
        val textViewCountResources = findViewById<TextView>(R.id.textView_main_count_resources)
        val textViewCountCitizens = findViewById<TextView>(R.id.textView_main_count_citizens)
        val textViewCountBuilt = findViewById<TextView>(R.id.textView_main_count_built)
        val textViewCurrentEra = findViewById<TextView>(R.id.textView_main_current_era)
        textViewCountResources.text = IndicatorService.showDisplayResources()
        textViewCountCitizens.text = IndicatorService.showDisplayCitizens()
        textViewCountBuilt.text =
            "${IndicatorService.showDisplayBuiltProducer()}\n${IndicatorService.showDisplayBuiltConsumer()}"
        textViewCurrentEra.text = EraService.showCurrentEra()
    }

    fun startNextMove(view: View) {
        OtherIndicators.currentDay++
        val rsl = StringBuilder("Наступил ${OtherIndicators.currentDay} день!\n")
        val textViewNotice = findViewById<TextView>(R.id.textView_main_notice)
        rsl.append(BuildingService.continueBuild())
        IndicatorService.addResources()
        IndicatorService.deleteResources()
        if (OtherIndicators.frequencyAttackNomad != 5 && OtherIndicators.currentDay >= 100) {
            OtherIndicators.frequencyAttackNomad = 5
        }
        if (NomadService.checkDayForAttackNomad()) {
            val tmp = NomadService.nomadAttack()
            if (tmp == "Игра проиграна!") {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Внимание!")
                builder.setMessage("Игра проиграна! Можете играть дальше конечно... но лучше начните сначала.")
                builder.show()
            }
            rsl.append(tmp)
        }
        if (ExchangeIndicators.availableCountOperations == 0) {
            ExchangeResourcesService.incrementCountOperations()
        }
        if (OtherIndicators.availableUpdateTaxRate == 0) {
            TaxService.incrementCountUpdateTaxRate()
        }
        textViewNotice.text = rsl
        updateIndicatorsPlayer(view)
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
            return
        }
        textViewNotice.text = "Недостаточно средств для перехода в следующую эпоху"
    }

    fun switchActivityToBuilding(view: View) {
        val intent = Intent(this@MainActivity, BuildingMainActivity::class.java)
        startActivity(intent)
    }

    fun switchActivityToExchangeResources(view: View) {
        val intent = Intent(this@MainActivity, ExchangeResourcesActivity::class.java)
        startActivity(intent)
    }

    fun switchActivityCitizens(view: View) {
        val intent = Intent(this@MainActivity, CitizensActivity::class.java)
        startActivity(intent)
    }

    private fun updateIndicatorsWithoutView() {
        val textViewCountResources = findViewById<TextView>(R.id.textView_main_count_resources)
        val textViewCountCitizens = findViewById<TextView>(R.id.textView_main_count_citizens)
        val textViewCountBuilt = findViewById<TextView>(R.id.textView_main_count_built)
        val textViewCurrentEra = findViewById<TextView>(R.id.textView_main_current_era)
        textViewCountResources.text = IndicatorService.showDisplayResources()
        textViewCountCitizens.text = IndicatorService.showDisplayCitizens()
        textViewCountBuilt.text =
            "${IndicatorService.showDisplayBuiltProducer()}\n${IndicatorService.showDisplayBuiltConsumer()}"
        textViewCurrentEra.text = EraService.showCurrentEra()
    }

    fun showDescriptionGame(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Описание игры:")
        builder.setMessage(
            "Вы предводитель поселения, которое избрало путь экономического развития. " +
                    "Вокруг множество кочевников, от которых каждые 10 ходов необходимо откупаться " +
                    "(начиная с 100 хода откупаться придеться каждые 5 ходов). " +
                    "Если не заплатить им дань (100 золота), то они уведут половину жителей деревни. " +
                    "При отсутствии золота и поселенцев - игрок проигрывает."
        )
        builder.show()
    }
}