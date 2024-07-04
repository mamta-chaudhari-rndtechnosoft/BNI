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
import com.rndtechnosoft.bni.Model.AddMyGivesBody
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.ViewModel.MyGivesViewModel
import com.rndtechnosoft.bni.databinding.FragmentAddMyGivesDialogBinding


class AddMyGivesDialogFragment : DialogFragment() {

    private lateinit var binding:FragmentAddMyGivesDialogBinding
    private lateinit var myGivesViewModel: MyGivesViewModel

    companion object {

        fun newInstance(): AddMyGivesDialogFragment {
            val fragment = AddMyGivesDialogFragment()
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
        // Inflate the layout for this fragment
        binding = FragmentAddMyGivesDialogBinding.inflate(inflater,container,false)
        getDialog()?.getWindow()?.setBackgroundDrawableResource(R.drawable.dialog_bg_border)


        myGivesViewModel = ViewModelProvider(requireActivity())[MyGivesViewModel::class.java]

        myGivesViewModel.observeAddMyGivesResult().observe(requireActivity(), Observer {

            when {
                it.isSuccess -> {

                    val addMyGivesResponseBody = it.getOrNull()!!
                    val status: String = addMyGivesResponseBody.status
                    val message:String = addMyGivesResponseBody.message

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

        binding.btnAddGives.setOnClickListener {
            addMyGives()
        }

        return binding.root
    }

    private fun addMyGives(){

        val token:String? = "bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()

        val companyName = binding.etCompany.editText?.text.toString()
        val deptName = binding.etDepartment.editText?.text.toString()
        val companyEmail = binding.etCompanyEmail.editText?.text.toString()
        val companyPhoneNumber = binding.etCompanyPhone.editText?.text.toString()
        val companyUrl = binding.etWebUrl.editText?.text.toString()

        val addMyGives = AddMyGivesBody(companyName,deptName,companyEmail, companyPhoneNumber,companyUrl)

        myGivesViewModel.addMyGivesResult(addMyGives,token!!)

    }
}