package com.example.chan.hellobori

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_all_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class AllReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_review)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))
        //var window = window
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setStatusBarColor(Color.parseColor("#ffffff"))
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        setTitle("한줄평")


        var retrofit = Retrofit.Builder()
            .baseUrl("http://boostcourse-appapi.connect.or.kr:10000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(RetrofitService::class.java)
        server?.getRequest(Intent().getIntExtra("index",1),15)?.
            enqueue(object : Callback<MovieCommentResult> {
            override fun onFailure(call: Call<MovieCommentResult>, t: Throwable) {
                Toast.makeText(application, t.message, Toast.LENGTH_LONG).show()
                Log.e("fail", t.message)
            }

            override fun onResponse(call: Call<MovieCommentResult>, response: Response<MovieCommentResult>) {
                Toast.makeText(application, "response", Toast.LENGTH_LONG).show()
                Log.e("response1", response.body().toString())
                var detailData = response.body()

                Log.e("comment", detailData!!.result.toString())
                val reviewAdapter = ReviewAdapter(application, detailData!!.result)
                recyclerView_AllReview.adapter = reviewAdapter

                val lm = LinearLayoutManager(application)
                recyclerView_AllReview.layoutManager = lm
                recyclerView_AllReview.setHasFixedSize(true)
            }
        })
        textView_Create.setOnClickListener {
            server?.getRequest(Intent().getIntExtra("index",1),
                "chan1234", ratingBar_Comment.rating, editText_Comment.text.toString())?.
                enqueue(object : Callback<CreateComment> {
                    override fun onFailure(call: Call<CreateComment>, t: Throwable) {
                        Toast.makeText(application, t.message, Toast.LENGTH_LONG).show()
                        Log.e("fail", t.message)
                    }

                    override fun onResponse(call: Call<CreateComment>, response: Response<CreateComment>) {
                        Toast.makeText(application, "response", Toast.LENGTH_LONG).show()
                        Log.e("response1", response.body().toString())
                        var data = response.body()
                        if(data!!.code == 200){
                            Toast.makeText(application, "작성되었습니다.", Toast.LENGTH_LONG).show()
                            finish()
                            startActivity(intent)
                        }
                    }
                })

        }

    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        startActivity(Intent(applicationContext,AllReviewActivity::class.java).apply {
//            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        })
//    }
}
