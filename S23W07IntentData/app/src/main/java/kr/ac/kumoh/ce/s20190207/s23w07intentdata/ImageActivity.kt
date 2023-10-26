package kr.ac.kumoh.ce.s20190207.s23w07intentdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kr.ac.kumoh.ce.s20190207.s23w07intentdata.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var main : ActivityImageBinding
    private lateinit var imageName : String
    companion object {
        const val IMAGE_RESULT = "image result"
        const val IMAGE_NAME = "image name"

        const val LIKE = 100
        const val DISLIKE = 101
        const val NONE = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityImageBinding.inflate(layoutInflater)
        setContentView(main.root)

        //Log.i("Image!!!", intent.getStringExtra("location") ?: "없음") //엘비스 연산자
        imageName = intent.getStringExtra(MainActivity.KEY_NAME) ?: "none"
        val res = when (imageName){
            MainActivity.IMAGE_MOUNTAIN -> R.drawable.mountain
            MainActivity.IMAGE_SEA -> R.drawable.sea
            else -> R.drawable.ic_launcher_foreground
        }
        main.image.setImageResource(res)

        main.btnLike.setOnClickListener(this)
        main.btnDislike.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent()
        val value = when(v?.id){
            main.btnLike.id -> LIKE
            main.btnDislike.id -> DISLIKE
            else -> NONE
        }
        intent.putExtra(IMAGE_NAME, imageName)
        intent.putExtra(IMAGE_RESULT, value)
        setResult(RESULT_OK, intent)
        finish()
    }
}