package com.treedevs.tahfidz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            // Mulai MainActivity setelah SPLASH_TIME_OUT
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Tutup SplashActivity agar tidak kembali saat tombol kembali ditekan
            finish()
        }, 3000)
    }
}