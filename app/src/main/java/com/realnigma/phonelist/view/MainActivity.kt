package com.realnigma.phonelist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import com.realnigma.phonelist.viewmodel.PhoneViewModel
import com.realnigma.phonelist.R
import com.realnigma.phonelist.view.adapter.PhoneAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var phoneViewModel: PhoneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val phoneAdapter = initRecyclerView()
        initViewModel(phoneAdapter)
    }

    private fun initViewModel(adapter: PhoneAdapter) {
        phoneViewModel = ViewModelProvider(this).get(PhoneViewModel::class.java)
        phoneViewModel.phones.observe(this, Observer { phones ->
            phones?.let { adapter.updatePhones(it)
            }
        })
    }

    private fun initRecyclerView() : PhoneAdapter {
        val phoneAdapter = PhoneAdapter()
        phoneRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = phoneAdapter
        }
        return phoneAdapter
    }
}