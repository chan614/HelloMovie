package com.example.chan.hellobori

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_moviedetail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var listData : MovieListResult? = null
    var index : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setTitle("영화 목록")

        //supportFragmentManager.beginTransaction().add(R.id.container, MovieListFragment()).commit()

        var adapter = MoviePagerAdapter(supportFragmentManager)
        adapter.addItem(MovieFragment1())
        adapter.addItem(MovieFragment2())
        adapter.addItem(MovieFragment3())
        adapter.addItem(MovieFragment4())
        adapter.addItem(MovieFragment5())
        var pager = viewPager
        pager.adapter = adapter

        var retrofit = Retrofit.Builder()
            .baseUrl("http://boostcourse-appapi.connect.or.kr:10000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(RetrofitService::class.java)
        server?.getRequest()?.enqueue(object : Callback<MovieListResult> {
            override fun onFailure(call: Call<MovieListResult>, t: Throwable) {
                Toast.makeText(application,t.message, Toast.LENGTH_LONG).show()
                Log.e("fail",t.message)
            }
            override fun onResponse(call: Call<MovieListResult>, response: Response<MovieListResult>){
                Toast.makeText(application,"response", Toast.LENGTH_LONG).show()
                Log.e("response",response.body().toString())
                listData = response.body()
                //var data : MovieListResult =  Gson().fromJson(response.body().toString(),MovieListResult::class.java)
                //Log.e("data",data!!.result[2].image)
            }
        })

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            if(!supportFragmentManager.beginTransaction().isEmpty) {
                supportFragmentManager.beginTransaction().remove(MovieDetailFragment()).commit()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                //supportFragmentManager.beginTransaction().replace(R.id.container, MovieListFragment()).commit()
                finish()
                startActivity(Intent(application, MainActivity::class.java))
            }
//            R.id.nav_gallery -> {
//
//            }
            R.id.nav_slideshow -> {

            }

            R.id.nav_share -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun onSelectedMovie(item : Int){
        when(item) {
            1 -> {
                index = 0
                supportFragmentManager.beginTransaction().replace(R.id.container, MovieDetailFragment()).commit()
            }
            2 -> {
                index = 1
                supportFragmentManager.beginTransaction().replace(R.id.container, MovieDetailFragment()).commit()
            }
            3 -> {
                index = 2
                supportFragmentManager.beginTransaction().replace(R.id.container, MovieDetailFragment()).commit()
            }
            4 -> {
                index = 3
                supportFragmentManager.beginTransaction().replace(R.id.container, MovieDetailFragment()).commit()
            }
            5 -> {
                index = 4
                supportFragmentManager.beginTransaction().replace(R.id.container, MovieDetailFragment()).commit()
            }
        }
    }

    fun onFragmentButton(v : View, index : Int){
        when(v) {
            button_AllView -> startActivity(Intent(application, AllReviewActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra("index", index)
            })
        }
    }

    fun getData() : Int? {
        return index
    }

}
