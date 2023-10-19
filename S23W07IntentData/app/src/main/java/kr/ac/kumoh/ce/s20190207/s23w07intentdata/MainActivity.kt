package kr.ac.kumoh.ce.s20190207.s23w07intentdata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.ac.kumoh.ce.s20190207.s23w07intentdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        const val KEY_NAME = "location"
    }
    private lateinit var main: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        main.btnMountain.setOnClickListener {
            // 함수화?
            Toast.makeText(this, "산 이미지", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(KEY_NAME, "mountain")
            startActivity(intent)
        }
        main.btnSea.setOnClickListener {
            Toast.makeText(this, "바다 이미지", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(KEY_NAME, "sea")
            startActivity(intent)
        }
    }
}