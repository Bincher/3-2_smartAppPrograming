package kr.ac.kumoh.ce.s20190207.s23w07intentdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kr.ac.kumoh.ce.s20190207.s23w07intentdata.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {
    private lateinit var main : ActivityImageBinding
    companion object {
        const val IMAGE_RESULT = "image result"

        const val LIKE = 100
        const val DISLIKE = 101
        const val NONE = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityImageBinding.inflate(layoutInflater)
        setContentView(main.root)

        //Log.i("Image!!!", intent.getStringExtra("location") ?: "없음") //엘비스 연산자
        val res = when (intent.getStringExtra(MainActivity.KEY_NAME)){
            MainActivity.IMAGE_MOUNTAIN -> R.drawable.mountain
            MainActivity.IMAGE_SEA -> R.drawable.mountain
            else -> R.drawable.ic_launcher_foreground
        }
        main.image.setImageResource(res)
    }
}