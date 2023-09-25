package kr.ac.kumoh.ce.s20190207.s23w03lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.s20190207.s23w03lotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model: LottoViewModel
    private lateinit var txtNum: Array<TextView?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecycle!!!", "onCreate()")

        //setContentView(R.layout.activity_main)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        txtNum = arrayOf(main.num1, main.num2, main.num3, main.num4, main.num5, main.num6)
//      txtNum[0] = main.num1
//      txtNum[1] = main.num2
//      txtNum[2] = main.num3
//      txtNum[3] = main.num4
//      txtNum[4] = main.num5
//      txtNum[5] = main.num6

        model = ViewModelProvider(this)[LottoViewModel::class.java]
        txtNum.forEachIndexed{
            index, textView ->
            textView?.text = model.numbers[index].toString()
        }

        main.btnGenerate.setOnClickListener {
            model.generate()
            txtNum.forEachIndexed{
                index, textView ->
                textView?.text = model.numbers[index].toString()
            }
//            main.num1.text = model.numbers[0].toString()
//            main.num2.text = model.numbers[1].toString()
//            main.num3.text = model.numbers[2].toString()
//            main.num4.text = model.numbers[3].toString()
//            main.num5.text = model.numbers[4].toString()
//            main.num6.text = model.numbers[5].toString()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("Lifecycle!!!", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Lifecycle!!!", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Lifecycle!!!", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Lifecycle!!!", "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Lifecycle!!!", "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Lifecycle!!!", "onDestroy()")
    }
}