package com.hsefakcay.yasamdongusu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hsefakcay.yasamdongusu.databinding.ActivityMainBinding
import com.hsefakcay.yasamdongusu.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding


    override fun onCreate(savedInstanceState: Bundle?) {  //ilk çalıştırılan fonk = onCreate
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //intent ten gelen bilgi
        val mainIntent = intent   //get intent
        val pushName = mainIntent.getStringExtra("name")
        binding.textView2.text = pushName
    }


    fun openMainActivity(view: View){
        //context
        val intent = Intent(this, MainActivity :: class.java)
        startActivity(intent)
        //finish()    //onDestroy manuel çarıştırıldı

    }
}