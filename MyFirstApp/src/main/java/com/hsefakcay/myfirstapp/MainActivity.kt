package com.hsefakcay.myfirstapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hsefakcay.myfirstapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding  //her projede bu tanimlama yapılmalı

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //View Binding eklenmesi
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.textView.text = "Selam Sefa"

/*
        binding.buttonKaydet.setOnClickListener {  //tıklanıldığında yapılacak fonk
            binding.textView.text = "Kaydedildi"
        }

 */

        /*
        val image = findViewById<ImageView>(R.id.imageView)
        val selamText = findViewById<TextView>(R.id.textView)
        //findViewById verimli değil çünkü her idli nesnenin üzeirnden geçer

        selamText.text = "Selam ben Hüseyin Sefa"  //textView içeriği değiştirildi

         */
    }
    fun kaydet(view: View){
        binding.textView.text = "Kayıt Edildi !"
    }
    fun sil(view: View){
        binding.textView.text = "Silindi !"

    }

}