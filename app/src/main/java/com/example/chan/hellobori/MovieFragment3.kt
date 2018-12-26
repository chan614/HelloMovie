package com.example.chan.hellobori

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie1.view.*
import kotlinx.android.synthetic.main.fragment_movie3.*

class MovieFragment3 : Fragment() {
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie3, container, false)
        rootView.button_Detail.setOnClickListener {
            //Toast.makeText(context, "프레그먼트", Toast.LENGTH_LONG).show()
            (activity as MainActivity).onSelectedMovie(3)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = (activity as MainActivity)
        if(act.listData != null){
            //Toast.makeText(context,act.data!!.result[0].image,Toast.LENGTH_LONG).show()
            Glide.with(this).load(act.listData!!.result[2].image).into(imageView_PosterL)
            textView_MovieTitle.setText(act.listData!!.result[2].id + ". " + act.listData!!.result[2].title)
            textView_Tiketing.setText("예매율 " + act.listData!!.result[2].reservation_rate + "%")
            textView_Age.setText(act.listData!!.result[2].grade + "세 관람가")
            textView_Dday.setText(act.listData!!.result[2].date)
        }
    }
    override fun onDetach() {
        super.onDetach()

    }

}