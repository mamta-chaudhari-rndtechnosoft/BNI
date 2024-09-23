package com.rndtechnosoft.bconn.Util

import android.content.Context
import android.content.SharedPreferences

class SaveSharedPreference private constructor(context: Context) {

    companion object {
        private const val PREFS_NAME = "MyAppPrefs"
        private const val TOKEN = "token"
        private const val USER_ID = "userId"
        private const val STATUS = "status"
        private const val USER_ID_REGISTER = "registerUserId"

        @Volatile
        private var INSTANCE: SaveSharedPreference? = null

        fun getInstance(context: Context): SaveSharedPreference {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SaveSharedPreference(context).also { INSTANCE = it }
            }
        }

    }

    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserId(userId: String) {
        sharedPreference.edit().putString(USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreference.getString(USER_ID, "")
    }

    fun clearUserId() {
        sharedPreference.edit().remove(USER_ID).apply()
    }

    fun saveRegisterUserId(registerUserId:String){
        sharedPreference.edit().putString(USER_ID_REGISTER,registerUserId).apply()
    }

    fun getRegisterUserIg():String?{
        return sharedPreference.getString(USER_ID_REGISTER,"")
    }

    fun clearRegisterUserId(){
        sharedPreference.edit().remove(USER_ID_REGISTER).apply()
    }

    fun saveToken(token:String){
        sharedPreference.edit().putString(TOKEN,token).apply()
    }

    fun getToken():String?{
        return sharedPreference.getString(TOKEN,"")
    }

    fun clearToken(){
        sharedPreference.edit().remove(TOKEN).apply()
    }

    fun saveStatus(status:String){
        sharedPreference.edit().putString(STATUS,status).apply()
    }

    fun getStatus():String?{
        return sharedPreference.getString(STATUS,"")
    }

    fun clearStatus(){
        sharedPreference.edit().remove(STATUS).apply()
    }
}