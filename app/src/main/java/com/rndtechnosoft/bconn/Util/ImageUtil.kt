package com.rndtechnosoft.bconn.Util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

object ImageUtil {
    fun convertImageToBase64(context: Context, uri: Uri?): String? {
        return if (uri != null) {
            try {
                val imageBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                val byteArrayOutputStream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }
}