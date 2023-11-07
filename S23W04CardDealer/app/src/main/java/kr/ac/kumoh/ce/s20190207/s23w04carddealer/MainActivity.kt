package kr.ac.kumoh.ce.s20190207.s23w04carddealer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.s20190207.s23w04carddealer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model: CardDealerViewModel
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        model = ViewModelProvider(this)[CardDealerViewModel::class.java]
        model.cards.observe(this, Observer {
            val res = IntArray(5)
            for (i in res.indices) {
                res[i] = resources.getIdentifier(
                    getCardName(it[i]), "drawable", packageName
                )
            }

            val cardViews = arrayOf(main.card1, main.card2, main.card3, main.card4, main.card5)

            for (i in cardViews.indices) {
                cardViews[i].setImageResource(res[i])
            }

            }
        )

        main.btnShuffle.setOnClickListener {
            shuffleAndHand()
        }
        main.btn1000.setOnClickListener {
            for(i in 1..1000){
                shuffleAndHand()
            }
        }
        main.btnFlush.setOnClickListener{
            shuffleAndHand()
            while(main.textView1.text != "플러쉬"
                && main.textView1.text != "풀하우스"
                && main.textView1.text != "포카드"
                && main.textView1.text != "스트레이트 플러쉬"){
                shuffleAndHand()
            }
        }
        main.btnFourcard.setOnClickListener{
            shuffleAndHand()
            while(main.textView1.text != "포카드"
                && main.textView1.text != "스트레이트 플러쉬"){
                shuffleAndHand()
            }
        }
        main.textView1.setOnClickListener {
            Toast.makeText(this, "시험용", Toast.LENGTH_SHORT).show()
        }
    }
    private fun shuffleAndHand(){
        model.shuffle()
        val cards = model.cards.value ?: intArrayOf() // 현재 카드 배열
        main.textView1.text = model.checkPokerHand(cards) // 포커 족보 판별
    }
    private fun getCardName(c: Int) : String{
        var shape = when(c / 13){
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }
        val number = when (c % 13){
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> {
                shape = shape.plus(2)
                "jack"
            }
            11 -> {
                shape = shape.plus(2)
                "queen"}
            12 -> {
                shape = shape.plus(2)
                "king"}
            else -> "error"
        }
        if(shape == "error" || number == "error"){
           return "c_red_joker"
        }else{
            return "c_${number}_of_${shape}"
        }

    }
}