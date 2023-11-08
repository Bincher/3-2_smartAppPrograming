// MainActivity.kt
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
    companion object {
        const val STRAIGHTFLUSH = "스트레이트 플러쉬"
        const val FOURCARD = "포카드"
        const val FULLHOUSE = "풀하우스"
        const val FLUSH = "플러쉬"
        const val STRAIGHT = "스트레이트"
        const val TRIPLE = "트리플"
        const val TWOPAIR = "투페어"
        const val ONEPAIR = "원페어"
        const val TOP = "탑"
    }

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        })

        // 화면 회전 시에도 족보 유지
        model.ranking.observe(this, Observer {
            main.textView1.text = it
        })

        main.btnShuffle.setOnClickListener {
            main.textView1.text = model.shuffleAndHand()
        }

        main.btn1000.setOnClickListener {
            Toast.makeText(this, "Shuffle 시작", Toast.LENGTH_SHORT).show()
            for (i in 1..10000) {
                main.textView1.text = model.shuffleAndHand()
            }
        }

        main.btnFlush.setOnClickListener {
            Toast.makeText(this, "Shuffle 시작", Toast.LENGTH_SHORT).show()
            model.shuffleAndHand()
            while (main.textView1.text != FLUSH
                && main.textView1.text != FULLHOUSE
                && main.textView1.text != FOURCARD
                && main.textView1.text != STRAIGHTFLUSH) {
                main.textView1.text = model.shuffleAndHand()
            }
        }

        main.btnFourcard.setOnClickListener {
            Toast.makeText(this, "Shuffle 시작", Toast.LENGTH_SHORT).show()
            main.textView1.text = model.shuffleAndHand()
            while (main.textView1.text != FOURCARD
                && main.textView1.text != STRAIGHTFLUSH) {
                main.textView1.text = model.shuffleAndHand()
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

    private fun getCardName(c: Int): String {
        var shape = when (c / 13) {
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }
        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> {
                shape = shape.plus(2)
                "jack"
            }
            11 -> {
                shape = shape.plus(2)
                "queen"
            }
            12 -> {
                shape = shape.plus(2)
                "king"
            }
            else -> "error"
        }
        if (shape == "error" || number == "error") {
            return "c_red_joker"
        } else {
            return "c_${number}_of_${shape}"
        }
    }

    private fun buildStatisticsMessage(): String {
        val message = StringBuilder()
        message.append("Shuffle 횟수 : ${model.count}\n")
        val statistics = listOf(
            STRAIGHTFLUSH,
            FOURCARD,
            FULLHOUSE,
            FLUSH,
            STRAIGHT,
            TRIPLE,
            TWOPAIR,
            ONEPAIR,
            TOP
        )

        for (statistic in statistics) {
            val count_temp = model.countMap[statistic] ?: 0
            val percentage = (count_temp.toDouble() / model.count.toDouble()) * 100
            val roundedPercentage = String.format("%.5f", percentage)
            message.append("$statistic : $count_temp ($roundedPercentage%)\n")
        }

        return message.toString()
    }
}
