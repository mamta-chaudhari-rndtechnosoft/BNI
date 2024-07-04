package com.rndtechnosoft.bni.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rndtechnosoft.bni.Activity.CompanyActivity
import com.rndtechnosoft.bni.Activity.MyAsksActivity
import com.rndtechnosoft.bni.Activity.MyGivesActivity
import com.rndtechnosoft.bni.Activity.MyMatchesActivity
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        val userId:String? = SaveSharedPreference.getInstance(requireContext()).getUserId()
        //Toast.makeText(this@MainActivity,"ID: $userId",Toast.LENGTH_SHORT).show()
        Log.d("Api Response",userId!!)

        binding.cardMyAsk.setOnClickListener {
            startActivity(Intent(requireContext(), MyAsksActivity::class.java))
        }

        binding.cardMyGives.setOnClickListener {
            startActivity(Intent(requireContext(), MyGivesActivity::class.java))
        }
        binding.cardMatches.setOnClickListener {
            startActivity(Intent(requireContext(), MyMatchesActivity::class.java))
        }


        return binding.root
    }


}