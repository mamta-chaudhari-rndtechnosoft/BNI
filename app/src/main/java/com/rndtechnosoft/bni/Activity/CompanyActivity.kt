package com.rndtechnosoft.bni.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.databinding.ActivityCompanyBinding

class CompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}