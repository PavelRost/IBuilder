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
import androidx.lifecycle.ViewModelProvider
import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.service.DatabaseService
import com.example.ibuilder.service.ExchangeResourcesService
import com.example.ibuilder.service.IndicatorService

class ExchangeResourcesActivity : AppCompatActivity() {

    private lateinit var textViewExchangeOperations: TextView
    private lateinit var textViewExchangeBuyPrice: TextView
    private lateinit var textViewExchangeSellPrice: TextView
    private lateinit var textViewExchangeQuantity: TextView
    private lateinit var textViewExchangeCountResources: TextView
    private var typeResource: TypeResources? = null
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_resources)
        initViews()
        if (supportActionBar != null) supportActionBar!!.hide()
        findViewById<RadioGroup>(R.id.radios_exchange).clearCheck()
        textViewExchangeOperations.text = Indicators.availableOperationExchange.toString()
        showCountResources()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ExchangeResourcesActivity::class.java)
        }
    }

    fun selectTypeResources(view: View) {
        val isChecked = (view as RadioButton).isChecked
        when (view.id) {
            R.id.radiobutton_exchange_stone -> {
                if (isChecked) {
                    typeResource = TypeResources.STONE
                }
            }
            R.id.radiobutton_exchange_wood -> {
                if (isChecked) {
                    typeResource = TypeResources.WOOD
                }
            }
            R.id.radiobutton_exchange_food -> {
                if (isChecked) {
                    typeResource = TypeResources.FOOD
                }
            }
        }
        textViewExchangeBuyPrice.text =
            ExchangeResourcesService.exchangeRate[typeResource]!!["buy"].toString()
        textViewExchangeSellPrice.text =
            ExchangeResourcesService.exchangeRate[typeResource]!!["sell"].toString()
    }

    fun incrementQuantity(view: View) {
        quantity++
        displayQuantity()
    }


    fun decrementQuantity(view: View) {
        if (quantity == 1) {
            Toast.makeText(this, "Можно обменять только >= 1 единицы ресурсов", Toast.LENGTH_SHORT).show()
            return
        }
        quantity--
        displayQuantity()
    }

    fun buyResources(view: View) {
        if (!ExchangeResourcesService.isAvailableExchangeOperations()) {
            Toast.makeText(this, "Исчерпаны доступные на данном ходу операции купли-продажи", Toast.LENGTH_SHORT).show()
            return
        }
        if (isInitTypeResource(view)) {
            if (!ExchangeResourcesService.isEnoughGold(typeResource!!, quantity)) {
                Toast.makeText(this, "У вас не хватает золота!", Toast.LENGTH_SHORT).show()
                return
            }
            ExchangeResourcesService.buyResources(typeResource!!, quantity)
            showCountResources()
            ExchangeResourcesService.decrementCountOperations()
            textViewExchangeOperations.text = Indicators.availableOperationExchange.toString()
            Toast.makeText(this, "Сделка завершена успешно!", Toast.LENGTH_SHORT).show()
        }
    }

    fun sellResources(view: View) {
        if (!ExchangeResourcesService.isAvailableExchangeOperations()) {
            Toast.makeText(this, "Исчерпаны доступные на данном ходу операции купли-продажи", Toast.LENGTH_SHORT).show()
            return
        }
        if (isInitTypeResource(view)) {
            if (!ExchangeResourcesService.isEnoughResourceForSell(typeResource!!, quantity)) {
                Toast.makeText(this, "У вас не хватает выбранного ресурса для продажи!", Toast.LENGTH_SHORT).show()
                return
            }
            ExchangeResourcesService.sellResource(typeResource!!, quantity)
            showCountResources()
            ExchangeResourcesService.decrementCountOperations()
            textViewExchangeOperations.text = Indicators.availableOperationExchange.toString()
            Toast.makeText(this, "Сделка завершена успешно!", Toast.LENGTH_SHORT).show()
            ViewModelProvider(this)[DatabaseService::class.java].saveAllIndicators()
        }
    }

    private fun showCountResources() {
        textViewExchangeCountResources.text = IndicatorService.showDisplayResources()
    }

    private fun displayQuantity() {
        textViewExchangeQuantity.text = quantity.toString()
    }

    private fun isInitTypeResource(view: View): Boolean {
        if (typeResource == null) {
            Toast.makeText(this, "Выберите тип ресурса", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun initViews() {
        textViewExchangeOperations = findViewById(R.id.textView_exchange_resource_operations)
        textViewExchangeBuyPrice = findViewById(R.id.textView_exchange_buy_price)
        textViewExchangeSellPrice = findViewById(R.id.textView_exchange_sell_price)
        textViewExchangeQuantity = findViewById(R.id.textView_exchange_quantity)
        textViewExchangeCountResources = findViewById(R.id.textView_exchange_count_resources)
    }
}