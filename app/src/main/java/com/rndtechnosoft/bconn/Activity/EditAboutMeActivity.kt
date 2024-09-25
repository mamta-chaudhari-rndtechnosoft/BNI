package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.databinding.ActivityEditAboutMeBinding

class EditAboutMeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAboutMeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarEditAboutMe.setNavigationOnClickListener {
            finish()
        }

    }
}