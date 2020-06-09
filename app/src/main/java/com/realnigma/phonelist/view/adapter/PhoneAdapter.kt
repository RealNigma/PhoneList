package com.realnigma.phonelist.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realnigma.phonelist.R
import com.realnigma.phonelist.room.Phone
import com.realnigma.phonelist.view.PhoneDescriptionActivity
import kotlinx.android.synthetic.main.phone_item.view.*

class PhoneAdapter : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {

    private var phones = ArrayList<Phone>()

    fun updatePhones(phones : List<Phone>) {
        this.phones.clear()
        this.phones.addAll(phones)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int) =
        PhoneViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.phone_item,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = phones.size

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(phones[position])
    }

    class PhoneViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val phoneCard = view.phoneCard
        private val phoneName = view.phoneName
        private val phoneGrade = view.phoneGrade

        fun bind(phone : Phone) {
            phoneName.text = phone.Name
            phoneGrade.rating = phone.Grade
            phoneCard.setOnClickListener { onClick(it, phone) }
        }

        private fun onClick(view: View, phone: Phone) {
            val context = view.context
            val intent = Intent(context, PhoneDescriptionActivity::class.java)
            intent.putExtra("phoneId", phone.id)
            context.startActivity(intent)
        }

    }

}