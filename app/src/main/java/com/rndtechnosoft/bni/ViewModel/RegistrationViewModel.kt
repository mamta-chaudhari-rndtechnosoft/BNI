package com.rndtechnosoft.bni.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rndtechnosoft.bni.Model.ChapterData
import com.rndtechnosoft.bni.Model.CityData
import com.rndtechnosoft.bni.Model.CountryData
import com.rndtechnosoft.bni.Model.RegisterResponseData
import com.rndtechnosoft.bni.Model.RegisterUserBody
import com.rndtechnosoft.bni.Repository.RegistrationRepository
import okhttp3.ResponseBody

class RegistrationViewModel : ViewModel() {

    private val registerResult = MutableLiveData<Result<RegisterResponseData>>()

    private val countriesLiveData = MutableLiveData<List<CountryData>>()
    private val cityLiveData = MutableLiveData<List<CityData>>()
    private val chapterLiveData = MutableLiveData<List<ChapterData>>()
    private val repository = RegistrationRepository.getInstance()
    //val countries: MutableLiveData<List<CountryData>> get() = countriesLiveData


    fun getCountries() {
        repository.getCountries().observeForever {
            countriesLiveData.value = it
        }
    }

    fun observeCountryLiveData(): MutableLiveData<List<CountryData>> {
        return countriesLiveData
    }

    fun getCity(countryName:String) {
        repository.getCity(countryName).observeForever { cityLiveData.value = it }
    }

    fun observeCityLiveData(): MutableLiveData<List<CityData>> {
        return cityLiveData
    }

    fun getChapter(cityName:String) {
        repository.getChapter(cityName).observeForever { chapterLiveData.value = it }
    }

    fun observeChapterLiveData(): MutableLiveData<List<ChapterData>> {
        return chapterLiveData
    }

    fun registerUser(registerUserBody: RegisterUserBody) {
        repository.registerUser(registerUserBody).observeForever {
            registerResult.value = it
        }
    }

    fun observeRegisterResult(): MutableLiveData<Result<RegisterResponseData>> {
        return registerResult
    }


    override fun onCleared() {
        super.onCleared()
        repository.getCountries().removeObserver { countriesLiveData }
    }

}