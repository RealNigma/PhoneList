package com.realnigma.phonelist.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realnigma.phonelist.R
import com.realnigma.phonelist.room.PhoneImage
import com.realnigma.phonelist.utils.loadImage
import kotlinx.android.synthetic.main.image_item.view.*

class PhoneImageAdapter : RecyclerView.Adapter<PhoneImageAdapter.PhoneImageViewHolder>() {

    private var images = ArrayList<PhoneImage>()

    fun updateImages(images : List<PhoneImage>) {
        this.images.clear()
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int) =
        PhoneImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_item,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holderImage: PhoneImageViewHolder, position: Int) {
        holderImage.bind(images[position])
        if (position == 0) { holderImage.showSwipeImage() }
    }

    class PhoneImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val image = view.imageView
        private val swipeImage = view.swipeImage

        fun bind(phoneImage : PhoneImage) {
            loadImage(phoneImage.imageUrl, image)
        }

        fun showSwipeImage() {
            swipeImage.visibility = View.VISIBLE
        }
    }

}