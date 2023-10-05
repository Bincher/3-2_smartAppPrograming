package kr.ac.kumoh.ce.s20190207.s23w03lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.s20190207.s23w03lotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model: LottoViewModel
    private lateinit var txtNum: Array<TextView?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.i("Lifecycle!!!", "onCreate()")

        //setContentView(R.layout.activity_main)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        txtNum = arrayOf(main.num1, main.num2, main.num3, main.num4, main.num5, main.num6)

        model = ViewModelProvider(this)[LottoViewModel::class.java]

        model.numbers.observe(this, Observer{
            txtNum.forEachIndexed{
                    index, textView ->
                textView?.text = model.numbers.value!![index].toString()
            }
        })

        main.btnGenerate.setOnClickListener {
            model.generate()
        }
    }
}
