package com.example.ibuilder

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExchangeResourcesActivity : AppCompatActivity() {

    private lateinit var textViewNoticeResources: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_resources)
        findViewById<RadioGroup>(R.id.radios_exchange).clearCheck()
        textViewNoticeResources = findViewById(R.id.textView_notice_exchange)
    }
}