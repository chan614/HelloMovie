package com.example.chan.hellobori

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class GalleryAdapter(val context : Context, val galleryList : ArrayList<GalleryImage>?) :
    RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GalleryHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_item, p0, false)
        return GalleryHolder(view)
    }

    override fun getItemCount(): Int {
        return galleryList!!.size
    }

    override fun onBindViewHolder(p0: GalleryHolder, p1: Int) {
        p0.bind(galleryList!![p1], context)
    }

    inner class GalleryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Image = itemView.findViewById<ImageView>(R.id.imageView_Gallery)
        val Video = itemView.findViewById<ImageView>(R.id.imageView_Video)

        fun bind(review : GalleryImage, context : Context){
            if(review.yn!=null) {
                Video.visibility = View.VISIBLE
                var sub = "https://img.youtube.com/vi/"+ review.yn + "/0.jpg"
                Glide.with(context).load(sub).into(Image)
                Image.setOnClickListener {
                    startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(review.url)),Bundle())
                }

            }else{
                Glide.with(context).load(review.url).into(Image)
                Video.visibility = View.INVISIBLE
                Image.setOnClickListener {
                    startActivity(context, Intent(context, FullscreenActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        putExtra("bit", review.url)
                    }, Bundle())
                }
            }
        }

    }
}