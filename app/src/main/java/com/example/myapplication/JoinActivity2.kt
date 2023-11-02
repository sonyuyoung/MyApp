package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityJoin2Binding

class JoinActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityJoin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityJoin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userEmail = intent.getStringExtra("userEmail")
        val userName = intent.getStringExtra("userName")
        val userAddress = intent.getStringExtra("userAddress")
        binding.resultEmailView.text = "Email: $userEmail"
        binding.resultNamelView.text = "$userName"
        binding.resultAddressView.text = "Address: $userAddress"
        // 데이터 확인
        Log.d("syy","데이터 확인 2번 화면 이메일: ${userEmail} , " +
                "패스워드: ${userName}, 나이: ${userAddress}")


    }
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