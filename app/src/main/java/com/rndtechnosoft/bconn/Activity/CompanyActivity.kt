package com.rndtechnosoft.bconn.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.AddCatalogResponseData
import com.rndtechnosoft.bconn.Model.BusinessInfoResponseData
import com.rndtechnosoft.bconn.Model.UpdateProfileBannerImageResponseData
import com.rndtechnosoft.bconn.Model.UpdateProfileImageResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.FileUtil
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityCompanyBinding
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyBinding
    private var selectedBannerImageUri: Uri? = null
    private var selectedProfileImageUri: Uri? = null
    private var catalogUrl: String? = null

    companion object {
        const val PDF_REQUEST_CODE = 30
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.layoutProgressBar.visibility = View.VISIBLE
        getBusinessInfo()

        binding.addImgBanner.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(
                    1080,
                    1080
                ).start(20)
        }

        binding.addImgProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(
                    1080,
                    1080
                ).start(21)
        }

        val businessId: String? = intent.getStringExtra("companyId")
        binding.imgEditContactDetails.setOnClickListener {
            val intent = Intent(this@CompanyActivity, EditCompanyContactDetailsActivity::class.java)
            intent.putExtra("businessId", businessId)
            startActivity(intent)
        }

        binding.imgEditCompanyDetails.setOnClickListener {
            val intent = Intent(this@CompanyActivity, EditCompanyDetailsActivity::class.java)
            intent.putExtra("businessId", businessId)
            startActivity(intent)
        }

        binding.layoutAddCatalog.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PDF_REQUEST_CODE)
        }

        binding.layoutViewCatalog.setOnClickListener {

            if(catalogUrl.isNullOrEmpty()){
                Toast.makeText(this@CompanyActivity,"No Catalog is added.",Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(catalogUrl)
                startActivity(intent)
            }

        }

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
                            this@CompanyActivity,
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
                            this@CompanyActivity,
                            "Image Not Selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                PDF_REQUEST_CODE -> { // Handle PDF File
                    val pdfUri: Uri? = data?.data
                    if (pdfUri != null) {
                        val pdfFile = FileUtil.getFileFromUri(this@CompanyActivity, pdfUri)
                        if (pdfFile != null) {
                            binding.layoutProgressBar.visibility = View.VISIBLE
                            uploadPdfFile(pdfFile)
                        } else {
                            Toast.makeText(
                                this@CompanyActivity,
                                "Failed to get PDF file",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                else -> {
                    Toast.makeText(
                        this@CompanyActivity,
                        "Invalid Request",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateProfileImage(uriProfile: Uri?) {

        val token: String =
            "Bearer " + SaveSharedPreference.getInstance(this@CompanyActivity).getToken()
        val businessId: String? = intent.getStringExtra("companyId")

        val imageFile = FileUtil.getFileFromUri(this@CompanyActivity, uriProfile!!)
        if (imageFile == null) {
            Toast.makeText(this@CompanyActivity, "Failed to get image file", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val imagePart = MultipartBody.Part.createFormData("profileImg", imageFile.name, requestFile)

        RetrofitInstance.apiInterface.updateProfileImageBusiness(token!!, businessId, imagePart)
            .enqueue(object : Callback<UpdateProfileImageResponseData?> {
                override fun onResponse(
                    call: Call<UpdateProfileImageResponseData?>,
                    response: Response<UpdateProfileImageResponseData?>
                ) {
                    if (response.isSuccessful) {

                    } else {
                        Toast.makeText(
                            this@CompanyActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UpdateProfileImageResponseData?>, t: Throwable) {
                    Toast.makeText(
                        this@CompanyActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })


    }


    private fun updateBannerImage(uriBanner: Uri?) {

        if (uriBanner == null) {
            Toast.makeText(this@CompanyActivity, "No image selected", Toast.LENGTH_SHORT).show()
            return
        }

        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@CompanyActivity).getToken()
        val businessId: String? = intent.getStringExtra("companyId")
        //val userId: String? = SaveSharedPreference.getInstance(this@CompanyActivity).getUserId()

        // Convert Uri to File using FileUtil
        val imageFile = FileUtil.getFileFromUri(this@CompanyActivity, uriBanner)
        if (imageFile == null) {
            Toast.makeText(this@CompanyActivity, "Failed to get image file", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val imagePart = MultipartBody.Part.createFormData("bannerImg", imageFile.name, requestFile)

        RetrofitInstance.apiInterface.updateBannerUserBusiness(token!!, businessId, imagePart)
            .enqueue(object : Callback<UpdateProfileBannerImageResponseData?> {
                override fun onResponse(
                    call: Call<UpdateProfileBannerImageResponseData?>,
                    response: Response<UpdateProfileBannerImageResponseData?>
                ) {
                    if (response.isSuccessful) {
                        //Toast.makeText(requireContext(), "Success !!", Toast.LENGTH_SHORT).show()
                        val bannerResponse = response.body()!!
                        val message: String = bannerResponse.message
                        Toast.makeText(this@CompanyActivity, "Banner: $message", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        Toast.makeText(
                            this@CompanyActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()

                        Toast.makeText(
                            this@CompanyActivity,
                            "Response Error: ${call.request().url}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("Api Response", "URL: ${call.request().url}")
                    }
                }

                override fun onFailure(
                    call: Call<UpdateProfileBannerImageResponseData?>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@CompanyActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


    private fun getBusinessInfo() {

        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@CompanyActivity).getToken()

        val businessId: String? = intent.getStringExtra("companyId")

        RetrofitInstance.apiInterface.getBusinessInfo(token!!, businessId!!)
            .enqueue(object : Callback<BusinessInfoResponseData?> {
                override fun onResponse(
                    call: Call<BusinessInfoResponseData?>,
                    response: Response<BusinessInfoResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        val businessInfoResponseData = response.body()!!
                        val businessData = businessInfoResponseData.data
                        val message = businessInfoResponseData.message

                        val companyName = businessData.companyName
                        val industryName = businessData.industryName
                        val designationName = businessData.designation
                        val aboutCompany = businessData.aboutCompany
                        val address = businessData.companyAddress
                        val profileImage = businessData.profileImg
                        val bannerImage = businessData.bannerImg
                        val catalog = businessData.catalog

                        val imgUrl = "https://bconn.rndtechnosoft.com/api/image/download/"
                        val profile: String = imgUrl + profileImage
                        val banner: String = imgUrl + bannerImage

                        val pdfUrl = "https://bconn.rndtechnosoft.com/api/pdf/download/"

                        binding.tvCompanyName.text = companyName
                        binding.tvIndustry.text = industryName
                        binding.tvDesignation.text = designationName
                        binding.tvAboutCompany.text = aboutCompany
                        binding.tvAddress.text = address

                        if (profileImage.isNullOrEmpty()) {
                            Picasso.get().load(R.drawable.profile_placeholder)
                                .into(binding.imgProfile)
                        } else {
                            Picasso.get().load(profile).into(binding.imgProfile)
                        }

                        if (bannerImage.isNullOrEmpty()) {
                            Picasso.get().load(R.drawable.banner_placeholder_two)
                                .into(binding.imgBanner)
                        } else {
                            Picasso.get().load(banner).into(binding.imgBanner)
                        }

                        if (!catalog.isNullOrEmpty()) {
                            //binding.layoutViewCatalog.visibility = View.GONE
                            catalogUrl = pdfUrl + catalog
                        }


                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@CompanyActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BusinessInfoResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@CompanyActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

    private fun uploadPdfFile(pdfFile: File) {
        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@CompanyActivity).getToken()

        val businessId: String? = intent.getStringExtra("companyId")

        val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(), pdfFile)
        val pdfPart = MultipartBody.Part.createFormData("catalog", pdfFile.name, requestFile)


        RetrofitInstance.apiInterface.addCatalogue(token!!, businessId, pdfPart)
            .enqueue(object : Callback<AddCatalogResponseData?> {
                override fun onResponse(
                    call: Call<AddCatalogResponseData?>,
                    response: Response<AddCatalogResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE

                        var responseData = response.body()!!
                        var catalogId: String = responseData.updatedFields.catalog

                        Log.d("Api Response", "Catalog: $catalogId")

                        Toast.makeText(
                            this@CompanyActivity,
                            "Catalog Added Successfully.",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@CompanyActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AddCatalogResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@CompanyActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

}