package com.example.calculatorapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.operator.Operator


class MainActivity : AppCompatActivity() {
    lateinit var b1:Button
    lateinit var b2:Button
    lateinit var b3:Button
    lateinit var b4:Button
    lateinit var b5:Button
    lateinit var b6:Button
    lateinit var b7:Button
    lateinit var b8:Button
    lateinit var b9:Button
    lateinit var b0:Button
    lateinit var b00:Button
    lateinit var bdot:Button
    lateinit var open_Para:Button
    lateinit var close_Para:Button

    lateinit var b_plus:Button
    lateinit var b_minus:Button
    lateinit var b_divide:Button
    lateinit var b_multiply:Button
    lateinit var b_percent:Button
    lateinit var b_equal:Button
    lateinit var fact_btn:Button
    lateinit var pi_btn:Button

    lateinit var b_allClear:Button
    lateinit var b_clear:Button

    lateinit var tvPrimary:TextView
    lateinit var tvSecondry:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvPrimary = findViewById(R.id.answer)
        tvSecondry = findViewById(R.id.input_numbers_to_calculate)

        b1 = findViewById(R.id.one_btn)
        b2 = findViewById(R.id.two_btn)
        b3 = findViewById(R.id.three_btn)
        b4 = findViewById(R.id.four_btn)
        b5 = findViewById(R.id.five_btn)
        b6 = findViewById(R.id.six_btn)
        b7 = findViewById(R.id.seven_button)
        b8 = findViewById(R.id.eight_button)
        b9 = findViewById(R.id.nine_btn)
        b0 = findViewById(R.id.zero_btn)
        b00 = findViewById(R.id.zero_zero_btn)
        bdot = findViewById(R.id.dot_btn)
        open_Para = findViewById(R.id.open_parenthesis)
        close_Para = findViewById(R.id.close_parenthesis)
        pi_btn = findViewById(R.id.pi_btn)
        fact_btn = findViewById(R.id.fact_btn)

        b_plus =findViewById(R.id.add_btn)
        b_minus = findViewById(R.id.subtract_btn)
        b_divide = findViewById(R.id.divide_button)
        b_multiply = findViewById(R.id.multiply_btn)
        b_percent = findViewById(R.id.mod_button)
        b_allClear = findViewById(R.id.all_clear)
        b_clear = findViewById(R.id.delete)
        b_equal = findViewById(R.id.equal_btn)

        b1.setOnClickListener { setValue("1" , false) }
        b2.setOnClickListener { setValue("2" , false) }
        b3.setOnClickListener { setValue("3" , false) }
        b4.setOnClickListener { setValue("4" , false) }
        b5.setOnClickListener { setValue("5" , false) }
        b6.setOnClickListener { setValue("6" , false) }
        b7.setOnClickListener { setValue("7" , false) }
        b8.setOnClickListener { setValue("8" , false) }
        b9.setOnClickListener { setValue("9" , false) }
        b0.setOnClickListener { setValue("0" , false) }
        b00.setOnClickListener { setValue("00" , false) }

        b_plus.setOnClickListener { setValue("+" , false) }
        b_minus.setOnClickListener { setValue("-" , false) }
        b_divide.setOnClickListener { setValue("/" , false) }
        b_multiply.setOnClickListener { setValue("*" , false) }
        bdot.setOnClickListener { setValue("." , false) }
        open_Para.setOnClickListener { setValue("(" , false) }
        close_Para.setOnClickListener { setValue(")" , false) }

//        for Clearing answer Screen
        b_allClear.setOnClickListener {
            tvPrimary.text=""
            tvSecondry.text=""
        }

//        for deleteing last value in calculation
        b_clear.setOnClickListener {
            val expression = tvSecondry.text.toString()
            if(expression.isNotEmpty()) {
                tvSecondry.text = expression.substring(0,expression.length-1)
            }
        }

        // PI button
        pi_btn.setOnClickListener {
            tvSecondry.text = "π"
            val expression = ExpressionBuilder("pi").build()
            val result = expression.evaluate()
            val longResult = result.toLong()
            if(result == longResult.toDouble()){
                tvPrimary.text=longResult.toString()
            }else {
                tvPrimary.text=result.toString()
            }
        }

//        percent button
        b_percent.setOnClickListener {
            val value = tvSecondry.text.toString()
            val expression = ExpressionBuilder(value + "* 0.01").build()
            val ans = expression.evaluate();
            tvPrimary.text = ans.toString()
        }

//          Factorial button
        fact_btn.setOnClickListener {
            calculateFactorial()
        }

//        for calculate answer
        b_equal.setOnClickListener {
            try {
                val expression = ExpressionBuilder(tvSecondry.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if(result == longResult.toDouble()){
                    tvPrimary.text=longResult.toString()
                }else {
                    tvPrimary.text=result.toString()
                }
            }catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();

                Log.d("EXCEPTION", "Message: ${e.message}")
            }
        }

    }

//    this function is setting button values
    private fun setValue(string: String, isClear: Boolean) {
        if (isClear) {
            tvSecondry.text = ""
            tvSecondry.append(string)
        } else {
            tvSecondry.append(string)
        }
    }

    fun calculateFactorial() {
        val factorial: Operator = object : Operator("!", 1, true, PRECEDENCE_POWER + 1) {
            override fun apply(vararg args: Double): Double {
                val arg = args[0].toInt()
                require(arg.toDouble() == args[0]) { "Operand for factorial has to be an integer" }
                require(arg >= 0) { "The operand of the factorial can not be less than zero" }
                var result = 1.0
                for (i in 1..arg) {
                    result *= i.toDouble()
                }
                return result
            }
        }
        val value = tvSecondry.text.toString()+"!"
        val expression = ExpressionBuilder(value).operator(factorial).build()
        val result = expression.evaluate()
        val longResult = result.toLong()
        if(result == longResult.toDouble()){
            tvPrimary.text=longResult.toString()
        }else {
            tvPrimary.text=result.toString()
        }
    }
}