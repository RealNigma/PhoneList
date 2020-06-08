package com.realnigma.phonelist

import androidx.room.Embedded
import androidx.room.Relation

data class PhoneWithImages (
    @Embedded val phone : Phone,
    @Relation(
            parentColumn = "id",
            entityColumn = "phoneId"
    )
    val images: List<PhoneImage>
)