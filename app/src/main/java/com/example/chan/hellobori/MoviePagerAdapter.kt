package com.example.chan.hellobori

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MoviePagerAdapter : FragmentStatePagerAdapter{
    constructor(fm: FragmentManager?) : super(fm)
    var fragmentList = ArrayList<Fragment>()

    fun addItem(item : Fragment){
        fragmentList.add(item)
    }

    override fun getItem(position: Int): Fragment? {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}