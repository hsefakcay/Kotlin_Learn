package com.hsefakcay.bilgisaklamaprojesi

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hsefakcay.bilgisaklamaprojesi.databinding.ActivityMainBinding
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //shared preferences    - cihazın hafızasında veri saklama kçük veriler
    lateinit var sharedPreferences: SharedPreferences
    var alinanKullaniciAdi : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         sharedPreferences = getSharedPreferences("com.hsefakcay.bilgisaklamaprojesi", MODE_PRIVATE)

        alinanKullaniciAdi = sharedPreferences.getString("isim","")
        if(alinanKullaniciAdi == ""){
            binding.textView.text = "Kaydedilen İsim : "
        }else{
            binding.textView.text = "Kaydedilen isim : ${alinanKullaniciAdi}"
        }
    }

    fun Save(view: View){
        val kullanıcıIsmi =binding.editText.text.toString()

        if (kullanıcıIsmi == ""){
            Toast.makeText(this@MainActivity,"Kullanıcı Adını giriniz", Toast.LENGTH_LONG).show()
        }else{
            sharedPreferences.edit().putString("isim", kullanıcıIsmi).apply()
            binding.textView.text = "Kaydedilen isim : ${kullanıcıIsmi}"
        }
    }

    fun Delete(view: View){
        if(alinanKullaniciAdi != ""){
            sharedPreferences.edit().remove("isim").apply()
        }
        binding.textView.text = "Kaydedilen isim :"
    }
}