package com.example.ibuilder

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ibuilder.model.indicatorsDB.ExchangeIndicators
import com.example.ibuilder.service.ExchangeResourcesService
import model.indicatorsDB.TypeResources

class ExchangeResourcesActivity : AppCompatActivity() {

    private lateinit var textViewNoticeResources: TextView
    private var typeResource: TypeResources? = null
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_resources)
        findViewById<RadioGroup>(R.id.radios_exchange).clearCheck()
        textViewNoticeResources = findViewById(R.id.textView_exchange_notice)
    }

    fun selectTypeResources(view: View) {
        val isChecked = (view as RadioButton).isChecked
        when(view.id) {
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
        findViewById<TextView>(R.id.textView_exchange_buy_price).text = ExchangeIndicators.exchangeRate[typeResource]!!["buy"].toString()
        findViewById<TextView>(R.id.textView_exchange_sell_price).text = ExchangeIndicators.exchangeRate[typeResource]!!["sell"].toString()
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

    private fun displayQuantity() {
        val quantityTextView = findViewById<TextView>(R.id.textView_exchange_quantity)
        quantityTextView.text = quantity.toString()
    }

    fun buyResources(view: View) {
        if (isInitTypeResource(view)) {
            if (!ExchangeResourcesService.isEnoughGold(typeResource!!, quantity)) {
                Toast.makeText(this, "У вас не хватает золота!", Toast.LENGTH_SHORT).show()
                return
            }
            ExchangeResourcesService.buyResources(typeResource!!, quantity)
            Toast.makeText(this, "Сделка завершена успешно!", Toast.LENGTH_SHORT).show()
        }
    }

    fun sellResources(view: View) {
        if (isInitTypeResource(view)) {
            if (!ExchangeResourcesService.isEnoughResourceForSell(typeResource!!, quantity)) {
                Toast.makeText(this, "У вас не хватает выбранного ресурса для продажи!", Toast.LENGTH_SHORT).show()
                return
            }
            ExchangeResourcesService.sellResource(typeResource!!, quantity)
            Toast.makeText(this, "Сделка завершена успешно!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isInitTypeResource(view: View) : Boolean {
        if (typeResource == null) {
            Toast.makeText(this, "Выберите тип ресурса", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}