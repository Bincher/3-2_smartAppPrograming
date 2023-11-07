package kr.ac.kumoh.ce.s20190207.s23w04carddealer

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.s20190207.s23w04carddealer.databinding.ActivityMainBinding
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model: CardDealerViewModel
    var count = 0
    var gameCount = Array(9,{0})
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
            Toast.makeText(this, "Shuffle 시작", Toast.LENGTH_SHORT).show()
            for(i in 1..10000){
                shuffleAndHand()
            }
        }
        main.btnFlush.setOnClickListener{
            Toast.makeText(this, "Shuffle 시작", Toast.LENGTH_SHORT).show()
            shuffleAndHand()
            while(main.textView1.text != "플러쉬"
                && main.textView1.text != "풀하우스"
                && main.textView1.text != "포카드"
                && main.textView1.text != "스트레이트 플러쉬"){
                shuffleAndHand()
            }
        }
        main.btnFourcard.setOnClickListener{
            Toast.makeText(this, "Shuffle 시작", Toast.LENGTH_SHORT).show()
            shuffleAndHand()
            while(main.textView1.text != "포카드"
                && main.textView1.text != "스트레이트 플러쉬"){
                shuffleAndHand()
            }
        }
        main.textView1.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("통계")
                .setMessage("Shuffle 횟수 : ${count}\n" +
                        "스트레이트 플러쉬 횟수 : ${gameCount[0]}(${round(gameCount[0].toDouble()/count*100)}%)\n"+
                        "포카드 횟수 : ${gameCount[1]}(${round(gameCount[1].toDouble()/count*100)}%)\n"+
                        "풀하우스 횟수 : ${gameCount[2]}(${round(gameCount[2].toDouble()/count*100)}%)\n"+
                        "플러쉬 횟수 : ${gameCount[3]}(${round(gameCount[3].toDouble()/count*100)}%)\n"+
                        "스트레이트 횟수 : ${gameCount[4]}(${round(gameCount[4].toDouble()/count*100)}%)\n"+
                        "트리플 횟수 : ${gameCount[5]}(${round(gameCount[5].toDouble()/count*100)}%)\n"+
                        "투페어 횟수 : ${gameCount[6]}(${round(gameCount[6].toDouble()/count*100)}%)\n"+
                        "원페어 횟수 : ${gameCount[7]}(${round(gameCount[7].toDouble()/count*100)}%)\n"+
                        "탑 횟수 : ${gameCount[8]}(${round(gameCount[8].toDouble()/count*100)}%)\n")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            // 다이얼로그를 띄워주기
            builder.show()
        }
    }
    private fun shuffleAndHand(){
        model.shuffle()
        count++
        val cards = model.cards.value ?: intArrayOf() // 현재 카드 배열
        var ranking = model.checkPokerHand(cards)
        main.textView1.text = ranking // 포커 족보 판별
        when(ranking){
            "스트레이트 플러쉬" -> gameCount[0] += 1
            "포카드" -> gameCount[1] += 1
            "풀하우스" -> gameCount[2] += 1
            "플러쉬" -> gameCount[3] += 1
            "스트레이트" -> gameCount[4] += 1
            "트리플" -> gameCount[5] += 1
            "투페어" -> gameCount[6] += 1
            "원페어" -> gameCount[7] += 1
            "탑" -> gameCount[8] += 1
            else -> count--
        }
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