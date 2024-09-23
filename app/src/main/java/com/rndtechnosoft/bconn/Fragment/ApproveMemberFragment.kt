package com.rndtechnosoft.bconn.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.databinding.FragmentApproveMemberBinding

class ApproveMemberFragment : Fragment() {

    private var _binding:FragmentApproveMemberBinding? = null
    private val binding get() = _binding!!

    //private lateinit var adapter: MyAskAdapter
    private var userId: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApproveMemberBinding.inflate(inflater,container,false)

        return binding.root
    }


}