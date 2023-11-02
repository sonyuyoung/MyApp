package com.example.myapplication

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLoginFormBinding

class LoginFormActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        뒤로가기
//        val backBtn: Button = findViewById(R.id.backBtn)
//        backBtn.setOnClickListener {
//            val intent : Intent = Intent(this, ActivityLoginFormBinding::class.java)
//            startActivity(intent)
//        }
        // 툴바 붙이기
        setSupportActionBar(binding.toolbar)
        //시스템에 있는 액션바에 업버튼 붙이기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //    온크리에이트 끝
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // This will simulate the back button press
                return true
            }
            // Add other menu item handling if needed
        }
        return super.onOptionsItemSelected(item)
    }
}