package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rndtechnosoft.bconn.databinding.ActivityCompanyBinding

class CompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}