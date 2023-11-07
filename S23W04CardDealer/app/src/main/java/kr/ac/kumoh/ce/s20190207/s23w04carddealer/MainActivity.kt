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
    private var count = 0
    private val countMap = mutableMapOf<String, Int>(
        "스트레이트 플러쉬" to 0,
        "포카드" to 0,
        "풀하우스" to 0,
        "플러쉬" to 0,
        "스트레이트" to 0,
        "트리플" to 0,
        "투페어" to 0,
        "원페어" to 0,
        "탑" to 0,
        )
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
            val message = buildStatisticsMessage()
            builder.setMessage(message)
                .setPositiveButton("확인") { dialog, id ->
                    // 확인 버튼 클릭 시 수행할 동작
                }
            builder.show()
        }
    }
    private fun shuffleAndHand(){
        model.shuffle()
        count++
        val cards = model.cards.value ?: intArrayOf() // 현재 카드 배열
        var ranking = model.checkPokerHand(cards)
        main.textView1.text = ranking // 포커 족보 판별
        countMap[ranking] = countMap[ranking]!! + 1
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
    private fun buildStatisticsMessage(): String {
        val message = StringBuilder()
        message.append("Shuffle 횟수 : $count\n")
        val statistics = listOf(
            "스트레이트 플러쉬",
            "포카드",
            "풀하우스",
            "플러쉬",
            "스트레이트",
            "트리플",
            "투페어",
            "원페어",
            "탑"
        )

        for (statistic in statistics) {
            val count_temp = countMap[statistic] ?: 0
            val percentage = (count_temp.toDouble() / count.toDouble()) * 100
            val roundedPercentage = String.format("%.5f", percentage)
            message.append("$statistic : $count_temp ($roundedPercentage%)\n")
        }

        return message.toString()
    }
}