package com.rndtechnosoft.bconn.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Adapter.KeyWordsAdapter
import com.rndtechnosoft.bconn.databinding.ActivityKeyWordsBinding

class KeyWordsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKeyWordsBinding
    //private lateinit var keyWordsList:MutableList<String>
    private val keyWordsList = mutableListOf<String>()
    private lateinit var adapter:KeyWordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeyWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //keyWordsList = emptyList<String>() as MutableList<String>

        binding.rvKeyword.layoutManager = LinearLayoutManager(this)
        adapter = KeyWordsAdapter(this,keyWordsList)
        binding.rvKeyword.adapter = adapter

        binding.btnAddKeyword.setOnClickListener {

            if(binding.etKeywords.text.toString().isEmpty()){
                Toast.makeText(this,"Please Add the Keyword.",Toast.LENGTH_SHORT).show();
            }else{
                val keyword:String = binding.etKeywords.text.toString()
                //keyWordsList.add(keyword)
                adapter.addKeyword(keyword)
                binding.etKeywords.text.clear()
            }

        }

        binding.btnAddAllKeyWords.setOnClickListener {
            if (keyWordsList.isEmpty()) {
                Toast.makeText(this, "Please add at least one keyword.", Toast.LENGTH_SHORT).show()
            } else {
                sendKeywordsBackToPreviousActivity()
            }
        }

    }

    private fun sendKeywordsBackToPreviousActivity() {
        val resultIntent = Intent()
        resultIntent.putStringArrayListExtra("KEYWORDS_LIST", ArrayList(keyWordsList))
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}