package com.example.chan.hellobori

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_moviedetail.*
import kotlinx.android.synthetic.main.fragment_moviedetail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MovieDetailFragment() : Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as MainActivity).supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ff0099cc")))
        (activity as MainActivity).setTitle("영화 상세")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ff0099cc")))
        (activity as MainActivity).setTitle("영화 상세")
        //var window = (activity as MainActivity).window
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        (activity as MainActivity).window.setStatusBarColor(Color.parseColor("#ff0099cc"))
        val rootView = inflater.inflate(R.layout.fragment_moviedetail, container, false)
        var index : Int?= (activity as MainActivity).getData()!!.plus(1)
        rootView.button_AllView.setOnClickListener {
            (activity as MainActivity).onFragmentButton(it, index!!)
        }
        rootView.recyclerView_Review.setOnTouchListener { v, event ->
            recyclerView_Review.canScrollVertically(1)
        }
        var temp : Int
        rootView.button_Up.setOnClickListener {
            if (!button_Up.isSelected) {
                button_Up.isSelected = true
                temp = Integer.parseInt(textView_Up.text.toString())
                temp += 1
                textView_Up.setText(temp.toString())
                if (button_Down.isSelected) {
                    button_Down.isSelected = false
                    temp = Integer.parseInt(textView_Down.text.toString())
                    temp -= 1
                    textView_Down.setText(temp.toString())
                }
            }
            else{

            }
        }
        rootView.button_Down.setOnClickListener {
            if(!button_Down.isSelected){
                button_Down.isSelected = true
                temp = Integer.parseInt(textView_Down.text.toString())
                temp += 1
                textView_Down.setText(temp.toString())
                if (button_Up.isSelected) {
                    button_Up.isSelected = false
                    temp = Integer.parseInt(textView_Up.text.toString())
                    temp -= 1
                    textView_Up.setText(temp.toString())
                }
            }
            else{

            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var index : Int?= (activity as MainActivity).getData()
        var retrofit = Retrofit.Builder()
            .baseUrl("http://boostcourse-appapi.connect.or.kr:10000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(RetrofitService::class.java)
        server?.getRequest(index?.plus(1))?.enqueue(object : Callback<MovieDetailResult> {
            override fun onFailure(call: Call<MovieDetailResult>, t: Throwable) {
                Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()
                Log.e("fail",t.message)
            }
            override fun onResponse(call: Call<MovieDetailResult>, response: Response<MovieDetailResult>) {
                Toast.makeText(context,"response", Toast.LENGTH_LONG).show()
                Log.e("response1",response.body().toString())
                var detailData = response.body()
                //Log.e("data",detailData!!.result[index].title)

                if(detailData != null) {
                    if (index != null) {
                        Toast.makeText(context, detailData!!.result[0].title, Toast.LENGTH_LONG).show()
                        Glide.with(imageView_Poster).load(detailData!!.result[0].thumb).into(imageView_Poster)
                        textView_Title.setText(detailData!!.result[0].title)
                        if (detailData!!.result[0].grade == "12") {
                            Glide.with(imageView_Age).load(R.drawable.ic_12).into(imageView_Age)
                        } else if (detailData!!.result[0].grade == "15") {
                            Glide.with(imageView_Age).load(R.drawable.ic_15).into(imageView_Age)
                        } else if (detailData!!.result[0].grade == "19") {
                            Glide.with(imageView_Age).load(R.drawable.ic_19).into(imageView_Age)
                        }
                        textView_OpenDate.setText(detailData!!.result[0].date + " 개봉")
                        textView_MovieContents.setText(detailData!!.result[0].genre + " / " + detailData!!.result[0].duration + "분")
                        textView_Up.setText(detailData!!.result[0].like)
                        textView_Down.setText(detailData!!.result[0].dislike)
                        textView_Rate.setText(detailData!!.result[0].reservation_rate + "%")
                        ratingBar.rating = detailData!!.result[0].user_rating.toFloat()
                        textView_Ratingbar.setText(detailData!!.result[0].user_rating)
                        textView_Count.setText(detailData!!.result[0].audience + "명")
                        textView_Story.setText(detailData!!.result[0].synopsis)
                        textView_PD.setText(detailData!!.result[0].director)
                        textView_Actor.setText(detailData!!.result[0].actor)

                        if(detailData!!.result[0].photos!=null || detailData!!.result[0].videos !=null) {
                            var temp = detailData!!.result[0].photos.split(",")
                            var temp1 = detailData!!.result[0].videos.split(",")

                            var galleryImg = ArrayList<GalleryImage>()

                            for (i in 0..temp.size - 1) {
                                galleryImg.add(GalleryImage(temp[i], null))
                            }
                            for (i in temp.size..temp.size + temp1.size - 1) {
                                //"https://youtu.be/VJAPZ9cIbs0"
                                val sub = temp1[i - temp.size].substring(17)
                                galleryImg.add(GalleryImage(temp1[i - temp.size], sub))
                            }

                            val galleryAdapter = GalleryAdapter(activity as MainActivity, galleryImg)
                            recyclerView_Gallery.adapter = galleryAdapter

                            val lm =
                                LinearLayoutManager(activity as MainActivity, LinearLayoutManager.HORIZONTAL, false)

                            recyclerView_Gallery.layoutManager = lm
                            recyclerView_Gallery.setHasFixedSize(true)
                        }
                    }
                }
            }
        })

        server?.getRequest(index?.plus(1),2)?.enqueue(object : Callback<MovieCommentResult> {
            override fun onFailure(call: Call<MovieCommentResult>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                Log.e("fail", t.message)
            }

            override fun onResponse(call: Call<MovieCommentResult>, response: Response<MovieCommentResult>) {
                Toast.makeText(context, "response", Toast.LENGTH_LONG).show()
                Log.e("response1", response.body().toString())
                var detailData = response.body()

                Log.e("comment", detailData!!.result.toString())
                val reviewAdapter = ReviewAdapter(activity as MainActivity, detailData!!.result)
                recyclerView_Review.adapter = reviewAdapter

                val lm = LinearLayoutManager(activity as MainActivity)
                recyclerView_Review.layoutManager = lm
                recyclerView_Review.setHasFixedSize(true)
            }
        })
    }

}