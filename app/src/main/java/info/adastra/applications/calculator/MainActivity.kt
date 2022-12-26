package info.adastra.applications.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput:TextView? = null
    private var lastNumeric:Boolean = false
    private var lastDot:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
    tvInput?.append((view as Button).text)

        lastNumeric = true
        lastDot = false
    //Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show()
    }

    fun onClear(view:View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view:View){
        if(lastNumeric&&!lastDot){
            tvInput?.text = "."
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view:View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }

    fun onEqual(view:View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            if(tvValue.startsWith("-")){
                prefix = "-"
                tvValue = tvValue.substring(1)
            }

            try {
                if(tvValue.contains("-")){
                    val splitVal = tvValue.split("-")
                    var one = splitVal[0]
                    var two = splitVal[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                }

                else if(tvValue.contains("+")){
                    val splitVal = tvValue.split("+")
                    var one = splitVal[0]
                    var two = splitVal[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

                else if(tvValue.contains("*")){
                    val splitVal = tvValue.split("*")
                    var one = splitVal[0]
                    var two = splitVal[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

                else if(tvValue.contains("/")){
                    val splitVal = tvValue.split("/")
                    var one = splitVal[0]
                    var two = splitVal[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }


            }catch (exc:java.lang.ArithmeticException){
                exc.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String):String{
        var value = result
        val resultLength = result.count()
        var str = result.substring(resultLength-2)

        if(str.equals(".0")){
            value = result.substring(0,result.length - 2)
        }
        return value

    }

    private fun isOperatorAdded(value:String):Boolean{

        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}