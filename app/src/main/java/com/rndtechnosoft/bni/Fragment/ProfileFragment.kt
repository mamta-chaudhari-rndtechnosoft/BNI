package com.rndtechnosoft.bni.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.ViewModel.ManageProfileViewModel

import com.rndtechnosoft.bni.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    private var selectedBannerImageUri: Uri? = null
    private var selectedProfileImageUri:Uri? = null
    private lateinit var viewModel: ManageProfileViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.addImgBanner.setOnClickListener {
            //openImageChooser()
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(
                    1080,
                    1080
                ).start(20)
        }

        binding.addImgProfile.setOnClickListener {
            //openImageChooser()
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(
                    1080,
                    1080
                ).start(21)
        }

        return binding.root
    }

    private fun openImageChooser() {
        ImagePicker.with(this)
            .crop() //Crop image(Optional), Check Customization for more option
            .compress(1024)    //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start(20)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){

            when(requestCode){

                20 -> {
                    val uri:Uri? = data?.data
                    if(uri!= null){
                        selectedBannerImageUri = uri
                        binding.imgBanner.setImageURI(uri)
                    }
                    else{
                        Toast.makeText(
                            requireContext(),
                            "Image Not Selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                21 -> {
                    val uri:Uri? = data?.data
                    if(uri != null){
                        selectedProfileImageUri = uri
                        binding.imgProfile.setImageURI(uri)
                    }
                    else{
                        Toast.makeText(
                            requireContext(),
                            "Image Not Selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Invalid Request",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


        }
    }

    fun updateProfileImage(){

    }

    fun updateBannerImage(){

    }
}