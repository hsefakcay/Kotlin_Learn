package com.hsefakcay.kotlincalculatorapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hsefakcay.kotlincalculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


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
    }


    var result: Float = 0.0f
    fun calculate(view: View, operation: (Float,Float) -> Float){
        val firstNumText = binding.firstNumText.text.toString()
        val secondNumText = binding.secondNumText.text.toString()

        if (firstNumText.isNotEmpty() && secondNumText.isNotEmpty()){
            val firstNum = firstNumText.toFloat()
            val secondNum = secondNumText.toFloat()
            result = operation(firstNum,secondNum)
            binding.resultsText.text = String.format("%.3f", result)
        }else{
            binding.resultsText.text = "Sayıları eksiksiz giriniz!"
        }
    }

    fun addition(view: View){
        calculate(view){a,b -> a+b}
    }
    fun substraction(view: View){
        calculate(view){a,b -> a-b}

    }
    fun multiplication(view: View){
        calculate(view){a,b -> a*b}

    }
    fun division(view: View){
        calculate(view){a,b -> a/b}

    }
}