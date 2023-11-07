package kr.ac.kumoh.ce.s20190207.s23w04carddealer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class CardDealerViewModel : ViewModel(){
    private var _cards = MutableLiveData<IntArray>(IntArray(5){-1})
    val cards : LiveData<IntArray>
        get() = _cards
    fun shuffle(){
        var num = 0
        val newCards = IntArray(5){-1}

        for(i in newCards.indices){
            do{
                num = Random.nextInt(52)
            }while(num in newCards)
            newCards[i] = num
        }
        newCards.sort()
        _cards.value = newCards
    }

    fun checkPokerHand(cards: IntArray): String {
        val cardCount = IntArray(13) { 0 }
        val suits = IntArray(4) { 0 }

        // 카드 숫자와 모양 수를 카운트
        for (card in cards) {
            val number = card % 13
            val suit = card / 13
            cardCount[number]++
            suits[suit]++
        }

        // 족보 판별 로직
        val isFlush = suits.any { it >= 5 }
        val isStraight = (0 until 9).any { i ->
            cardCount.slice(i until i + 5).all { it >= 1 }
        }
        val isFourOfAKind = cardCount.any { it >= 4 }
        val isThreeOfAKind = cardCount.any { it >= 3 }
        val isOnePair = cardCount.count { it == 2 } == 1
        val isTwoPair = cardCount.count { it == 2 } == 2

        // 족보 판별 및 결과 반환
        return when {
            isFlush && isStraight -> "스트레이트 플러쉬"
            isFourOfAKind -> "포카드"
            isThreeOfAKind && isOnePair -> "풀하우스"
            isFlush -> "플러쉬"
            isStraight -> "스트레이트"
            isThreeOfAKind -> "트리플"
            isTwoPair -> "투페어"
            isOnePair -> "원페어"
            else -> "탑"
        }
    }

}