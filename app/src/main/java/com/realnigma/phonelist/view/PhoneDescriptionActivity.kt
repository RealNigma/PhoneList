package com.realnigma.phonelist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.realnigma.phonelist.R
import com.realnigma.phonelist.viewmodel.PhoneViewModel
import kotlinx.android.synthetic.main.activity_phone_description.*

class PhoneDescriptionActivity : AppCompatActivity() {
    
    private lateinit var phoneViewModel : PhoneViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_description)
        val phoneId = intent.getIntExtra("phoneId",0)
        initViewModel(phoneId)
    }
    
    private fun initViewModel(phoneId : Int) {
        phoneViewModel = ViewModelProvider(this).get(PhoneViewModel::class.java)
        phoneViewModel.getPhoneWithImagesById(phoneId)
        phoneViewModel.phoneWithImagesById?.observe(this, Observer { phoneWithImages ->
            phoneWithImages?.let {
                phoneName.text = phoneWithImages.phone.Name
                phoneGrade.rating = phoneWithImages.phone.Grade
            }
        })
    }
}