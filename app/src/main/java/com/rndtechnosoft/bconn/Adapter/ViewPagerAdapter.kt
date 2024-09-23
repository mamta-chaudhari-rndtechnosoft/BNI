package com.rndtechnosoft.bconn.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = ArrayList<Fragment>()
    private val fragmentTitles = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
    fun getPageTitle(position: Int): String {
        return fragmentTitles[position]
    }
}