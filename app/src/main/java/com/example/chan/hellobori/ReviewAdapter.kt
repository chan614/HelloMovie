package com.example.chan.hellobori

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class ReviewAdapter(val context : Context, val reviewList : ArrayList<UserComment>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReviewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_item, p0, false)
        return ReviewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(p0: ReviewHolder, p1: Int) {
        p0.bind(reviewList[p1], context)
    }

    inner class ReviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rUserId = itemView.findViewById<TextView>(R.id.textView_Id)
        val rReviewDate = itemView.findViewById<TextView>(R.id.textView_Date)
        val rRate = itemView.findViewById<RatingBar>(R.id.ratingBar2)
        val rMainText = itemView.findViewById<TextView>(R.id.textView_MainText)
        val rUserProfile = itemView.findViewById<ImageView>(R.id.imageView_Profile)

        fun bind(review : UserComment, context : Context){
            rUserId.text = review.writer
            rReviewDate.text = review.time
            rRate.rating = review.rating
            rMainText.text = review.contents
            rUserProfile
            if (review.writer_image != null) {
                val resourceId = context.resources.getIdentifier(review.writer_image, "drawable", context.packageName)
                rUserProfile.setImageResource(resourceId)
            } else {
                rUserProfile.setImageResource(R.mipmap.ic_launcher)
            }
        }
    }
}