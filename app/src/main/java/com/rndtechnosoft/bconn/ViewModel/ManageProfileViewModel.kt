package com.rndtechnosoft.bconn.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rndtechnosoft.bconn.Model.UpdateProfileBannerImageResponseData
import com.rndtechnosoft.bconn.Model.UpdateProfileImageResponseData
import com.rndtechnosoft.bconn.Repository.ManageProfileRepository
import okhttp3.RequestBody

class ManageProfileViewModel : ViewModel() {

    private val manageProfileRepository = ManageProfileRepository()
    private val updateProfileImage = MutableLiveData<Result<UpdateProfileImageResponseData>>()
    private val updateBannerImage = MutableLiveData<Result<UpdateProfileBannerImageResponseData>>()
    private val errorLiveData = MutableLiveData<String>()

    fun updateProfileImage(token: String, image: RequestBody) {
        manageProfileRepository.updateProfile(token,image).observeForever {
            updateProfileImage.value = it
        }
    }

    /*fun updateBannerImage(token:String,imageBanner:RequestBody){
        manageProfileRepository.updateBanner(token,imageBanner).observeForever {
            updateBannerImage.value = it
        }
    }*/

    fun observeUpdateProfileImage():MutableLiveData<Result<UpdateProfileImageResponseData>>{
        return updateProfileImage
    }

    fun observeUpdateBannerImage():MutableLiveData<Result<UpdateProfileBannerImageResponseData>>{
        return updateBannerImage
    }

    fun errorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }

}