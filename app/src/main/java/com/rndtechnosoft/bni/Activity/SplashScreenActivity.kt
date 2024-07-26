package com.rndtechnosoft.bni.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val handler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler.postDelayed({

            //startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            //finish()

            val userId: String? = SaveSharedPreference.getInstance(this@SplashScreenActivity).getUserId()
            if (userId == "") {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()
            }

            else {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }

        }, 2000)


    }
}