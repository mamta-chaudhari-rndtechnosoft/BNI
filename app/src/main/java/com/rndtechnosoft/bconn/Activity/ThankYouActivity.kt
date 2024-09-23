package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.databinding.ActivityThankYouBinding

class ThankYouActivity : AppCompatActivity() {

    private lateinit var binding:ActivityThankYouBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThankYouBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }

}