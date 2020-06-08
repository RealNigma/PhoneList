package com.realnigma.phonelist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Phone(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val Name : String,
    val Grade : Int
)