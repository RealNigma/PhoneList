package com.realnigma.phonelist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity
data class PhoneImage(
    @PrimaryKey(autoGenerate = true) val imageListId : Int,
    val phoneId : Int,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image : Blob
)