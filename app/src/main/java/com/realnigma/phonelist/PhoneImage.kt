package com.realnigma.phonelist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhoneImage(
    @PrimaryKey(autoGenerate = true) val imageListId : Int,
    val phoneId : Int,
    val imageUrl : String
)