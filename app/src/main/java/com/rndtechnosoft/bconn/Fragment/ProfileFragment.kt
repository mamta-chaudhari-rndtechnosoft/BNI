package com.rndtechnosoft.bconn.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.rndtechnosoft.bconn.Activity.AddWorkActivity
import com.rndtechnosoft.bconn.Activity.EditAboutMeActivity
import com.rndtechnosoft.bconn.Activity.EditProfileContactDetailActivity
import com.rndtechnosoft.bconn.Activity.ManageBusinessActivity
import com.rndtechnosoft.bconn.Activity.ManageMembersActivity
import com.rndtechnosoft.bconn.Adapter.BusinessListAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.BusinessListResponseData
import com.rndtechnosoft.bconn.Model.UpdateProfileBannerImageResponseData
import com.rndtechnosoft.bconn.Util.FileUtil
import com.rndtechnosoft.bconn.Util.ImageUtil
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.ViewModel.ManageProfileViewModel
import com.rndtechnosoft.bconn.databinding.FragmentProfileBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private var selectedBannerImageUri: Uri? = null
    private var selectedProfileImageUri: Uri? = null
    private lateinit var viewModel: ManageProfileViewModel
    private lateinit var adapter: BusinessListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[ManageProfileViewModel::class.java]

        val refCode:String? = SaveSharedPreference.getInstance(requireContext()).getReferralNumber()
        binding.tvRefCode.setText("Referral Code: $refCode")

        binding.imgEditContactDetails.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileContactDetailActivity::class.java))
        }

        binding.imgEditAboutMe.setOnClickListener {
            startActivity(Intent(requireContext(),EditAboutMeActivity::class.java))
        }

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

        binding.layoutAddWork.setOnClickListener {
            startActivity(Intent(requireContext(), ManageBusinessActivity::class.java))
        }


        binding.layoutManageMembers.setOnClickListener {
            startActivity(Intent(requireContext(), ManageMembersActivity::class.java))
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

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                20 -> {
                    val uri: Uri? = data?.data
                    if (uri != null) {
                        selectedBannerImageUri = uri
                        updateBannerImage(selectedBannerImageUri)
                        binding.imgBanner.setImageURI(uri)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Image Not Selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                21 -> {
                    val uri: Uri? = data?.data
                    if (uri != null) {
                        selectedProfileImageUri = uri
                        updateProfileImage(selectedProfileImageUri)
                        binding.imgProfile.setImageURI(uri)
                    } else {
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

    fun updateProfileImage(uriProfile: Uri?) {

        val token: String =
            "Bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()

        /* val imageProfileRequestBody: RequestBody? = uriProfile?.let { uri ->
             val imageBase64 = ImageUtil.convertImageToBase64(requireContext(), uri)
             //RequestBody.create(MediaType.parse("text/plain"), imageBase64)
             RequestBody.create("text/plain".toMediaTypeOrNull(),imageBase64)
         }*/

        val imageProfileRequestBody: RequestBody? = uriProfile?.let { uri ->
            val imageBase64 = ImageUtil.convertImageToBase64(requireContext(), uri)
            val prefixedImageBase64 = "data:image/png;base64,$imageBase64"
            prefixedImageBase64!!.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        viewModel.updateProfileImage(token, imageProfileRequestBody!!)

        viewModel.observeUpdateProfileImage().observe(requireActivity(), Observer {

            when {
                it.isSuccess -> {

                    val profileResponse = it.getOrNull()!!
                    val message: String = profileResponse.message
                    Log.d("Api Response", "messageProfile: $message")
                    Toast.makeText(
                        requireContext(),
                        "Profile Update Success: $message",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Log.d("Api Response", "Update Failed: ${error?.message.toString()}")
                    Toast.makeText(
                        requireContext(),
                        "Profile Update failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    /* fun updateBannerImage(uriBanner:Uri?){
         val token: String = "bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()

         val imageBannerRequestBody: RequestBody? = uriBanner?.let { uri ->
             val imageBase64 = ImageUtil.convertImageToBase64(requireContext(), uri)
             val prefixedImageBase64 = "data:image/png;base64,$imageBase64"
             prefixedImageBase64!!.toRequestBody("text/plain".toMediaTypeOrNull())
         }

         viewModel.updateBannerImage(token!!,imageBannerRequestBody!!)

         viewModel.observeUpdateBannerImage().observe(requireActivity(), Observer {
             when{
                 it.isSuccess -> {

                     val profileResponse = it.getOrNull()!!
                     val message:String = profileResponse.message
                     Log.d("Api Response","messageBanner: $message")
                     Toast.makeText(
                         requireContext(),
                         "Banner Update Success: $message",
                         Toast.LENGTH_SHORT
                     ).show()


                 }
                 it.isFailure -> {
                     val error = it.exceptionOrNull()
                     Log.d("Api Response","Login Failed: ${error?.message.toString()}")
                     Toast.makeText(
                         requireContext(),
                         "Profile Update failed",
                         Toast.LENGTH_SHORT
                     ).show()
                 }
             }

         })
     }*/

    private fun updateBannerImage(uriBanner: Uri?) {

        if (uriBanner == null) {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
            return
        }

        val token: String? =
            "bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()
        //val userId: String? = SaveSharedPreference.getInstance(requireContext()).getUserId()
        val userId:String? = SaveSharedPreference.getInstance(requireContext()).getUserId()
        /*var prefixedImageBase64:String? = null

        val imageBannerRequestBody: RequestBody? = uriBanner?.let { uri ->
            val imageBase64 = ImageUtil.convertImageToBase64(requireContext(), uri)
             prefixedImageBase64 = "data:image/png;base64,$imageBase64"
            prefixedImageBase64!!.toRequestBody("text/plain".toMediaTypeOrNull())

        }*/
        // Log.d("Api Response","ImageBanner: $prefixedImageBase64")

        // Convert Uri to File


        // Convert Uri to File using FileUtil
        val imageFile = FileUtil.getFileFromUri(requireContext(), uriBanner)
        if (imageFile == null) {
            Toast.makeText(requireContext(), "Failed to get image file", Toast.LENGTH_SHORT).show()
            return
        }

        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val imagePart =
            MultipartBody.Part.createFormData("bannerImg", imageFile.name, requestFile)


        RetrofitInstance.apiInterface.updateBanner(token!!, userId, imagePart)
            .enqueue(object : Callback<UpdateProfileBannerImageResponseData?> {
                override fun onResponse(
                    call: Call<UpdateProfileBannerImageResponseData?>,
                    response: Response<UpdateProfileBannerImageResponseData?>
                ) {
                    if (response.isSuccessful) {
                        //Toast.makeText(requireContext(), "Success !!", Toast.LENGTH_SHORT).show()
                        val bannerResponse = response.body()!!
                        val message: String = bannerResponse.message
                        Toast.makeText(requireContext(), "Banner: $message", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()

                        Toast.makeText(
                            requireContext(),
                            "Response Error: ${call.request().url}",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.d("Api Response","URL: ${call.request().url}")
                    }
                }

                override fun onFailure(
                    call: Call<UpdateProfileBannerImageResponseData?>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


}