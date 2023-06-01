package com.example.day9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var btnKurSec: Button
    lateinit var spinner: Spinner
    lateinit var txtSecilenDovizK: TextView
    lateinit var btnSPAlıs:EditText
    lateinit var btnSPSatıs:EditText
    lateinit var btnBAlıs:EditText
    lateinit var btnBSatıs:EditText
    lateinit var txtDate:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val xml = XmlResult()

        btnKurSec = findViewById(R.id.btnKurSec)
        spinner = findViewById(R.id.spinner)
        txtSecilenDovizK = findViewById(R.id.txtSecilenDovizK)
        txtDate=findViewById(R.id.txtDate)
        btnSPAlıs = findViewById(R.id.btnSPAlıs)
        btnSPSatıs = findViewById(R.id.btnSPSatıs)
        btnBAlıs = findViewById(R.id.btnBAlıs)
        btnBSatıs = findViewById(R.id.btnBSatıs)
        btnSPAlıs.isEnabled=false
        btnSPSatıs.isEnabled=false
        btnBAlıs.isEnabled=false
        btnBSatıs.isEnabled=false


        // ArrayAdapter oluşturma
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.doviz_kuru,
            android.R.layout.simple_spinner_dropdown_item
        )

        // Spinner'a adapter ekleme
        spinner.adapter = adapter

        // Spinner üzerinde seçim yapılınca yapılacak işlemler
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Seçilen öğenin değerini al

                val selectedValue = parent?.getItemAtPosition(position).toString()
                val selectedPosition = position

                // Seçilen değeri ekrana yazdır
                txtSecilenDovizK.text = "Seçilen Döviz Kur :$selectedValue "



                val policy = ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                Thread {
                    xml.xmlCurrency()
                }.start()
                val tarihStr=xml.xmlDate()
                val formattedDate = String.format("%02d/%02d/%d", tarihStr.dayOfMonth, tarihStr.monthValue, tarihStr.year)
                txtDate.text="$formattedDate Günü Belirlenen Gösterge Niteliğindeki Türkiye Cumhuriyet Merkez Bankası Kurları"


                val arr = xml.xmlCurrency()
                var yeniD=arr.get(selectedPosition)
                btnSPAlıs.setText(yeniD.ForexBuying)
                btnSPSatıs.setText(yeniD.ForexSelling)
                btnBAlıs.setText(yeniD.BanknoteBuying)
                btnBSatıs.setText(yeniD.BanknoteSelling)


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Butona tıklama olayı ekleme
        btnKurSec.setOnClickListener {
            // Spinner'ı aç
            spinner.performClick()
        }
    }

}

