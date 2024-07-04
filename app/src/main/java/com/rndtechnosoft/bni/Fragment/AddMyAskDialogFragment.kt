package com.rndtechnosoft.bni.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rndtechnosoft.bni.Model.AddMyAskBody
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.ViewModel.MyAskViewModel
import com.rndtechnosoft.bni.databinding.FragmentAddMyAskDialogBinding

class AddMyAskDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddMyAskDialogBinding
    private lateinit var myAskViewModel: MyAskViewModel

    companion object {

        fun newInstance(): AddMyAskDialogFragment {
            val fragment = AddMyAskDialogFragment()
            val args = Bundle()
            //args.putString(ARG_TIME,time)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMyAskDialogBinding.inflate(inflater, container, false)
        getDialog()?.getWindow()?.setBackgroundDrawableResource(R.drawable.dialog_bg_border)

        myAskViewModel = ViewModelProvider(requireActivity())[MyAskViewModel::class.java]

        myAskViewModel.observeAddMyAskResult().observe(requireActivity(), Observer {
            when {
                it.isSuccess -> {

                    val addMyAskResponseBody = it.getOrNull()!!
                    val status: String = addMyAskResponseBody.status
                    val message:String = addMyAskResponseBody.message

                    when(status){
                        "success" -> {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            dismiss()
                        }

                        "failed" -> {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Log.d("Api Response","Failed: ${error?.message.toString()}")
                    Toast.makeText(
                        requireContext(),
                        "Failed: ${error?.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        binding.btnAddAsk.setOnClickListener {
            addMyAsk()
        }

        return binding.root
    }

    private fun addMyAsk() {

        val token:String? = "bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()

        val companyName = binding.etCompany.editText?.text.toString()
        val deptName = binding.etDepartment.editText?.text.toString()
        val companyMessage = binding.etMessage.editText?.text.toString()

        val addMyAskBody = AddMyAskBody(companyName,deptName,companyMessage)

        myAskViewModel.addMyAskResult(addMyAskBody,token!!)

    }

}