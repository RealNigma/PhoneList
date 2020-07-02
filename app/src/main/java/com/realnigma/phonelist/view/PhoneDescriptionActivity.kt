package com.realnigma.phonelist.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dingmouren.layoutmanagergroup.banner.BannerLayoutManager
import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import com.dingmouren.layoutmanagergroup.slide.SlideLayoutManager
import com.realnigma.phonelist.R
import com.realnigma.phonelist.room.Phone
import com.realnigma.phonelist.view.adapter.PhoneImageAdapter
import com.realnigma.phonelist.viewmodel.PhoneViewModel
import kotlinx.android.synthetic.main.activity_phone_description.*


class PhoneDescriptionActivity : AppCompatActivity() {
    
    private lateinit var phoneViewModel : PhoneViewModel
    private var phoneImageAdapter = PhoneImageAdapter()
    private val phoneId : Int by lazy { intent.getIntExtra("phoneId" , 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_description)
        initViewModel()
        phoneViewModel.phoneId.value = phoneId
        initRecyclerView()
        phoneGrade.setOnRatingBarChangeListener { _, rating, _ -> phoneGradeListener(rating) }
    }


    private fun initViewModel() {
        phoneViewModel = ViewModelProvider(this).get(PhoneViewModel::class.java)
        phoneViewModel.getPhoneWithImagesById(phoneId)
        phoneViewModel.phoneId.observe(this, Observer { observePhoneWithImages()})
    }

    private fun observePhoneWithImages() {
        phoneViewModel.phoneWithImagesById?.observe(this, Observer { phoneWithImages ->
            phoneWithImages?.let {
                phoneName.text = it.phone.name
                phoneGrade.rating = it.phone.grade
                phoneImageAdapter.updateImages(it.images)
            }
        })
    }

    private fun initRecyclerView() {
        imageRecyclerView.apply {
            layoutManager = SkidRightLayoutManager(1.25f,0.9f)
            adapter = phoneImageAdapter
        }
    }

    private fun phoneGradeListener(rating : Float) {
        val phoneId = phoneViewModel.phoneWithImagesById?.value?.phone?.id
        val phoneName = phoneViewModel.phoneWithImagesById?.value?.phone?.name
        val phone = Phone(phoneId!!, phoneName!!, rating)
        phoneViewModel.updatePhone(phone)
    }

}