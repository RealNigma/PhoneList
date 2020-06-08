package com.realnigma.phonelist

import android.media.Image
import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    fun byteArrayToImage(imageByteArray: ByteArray): Image? {
        return null
    }

    @TypeConverter
    fun imageToByteArray(image: Image?): ByteArray? {
        return null
    }
}