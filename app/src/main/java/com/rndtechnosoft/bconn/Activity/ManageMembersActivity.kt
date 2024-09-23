package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.rndtechnosoft.bconn.Adapter.ViewPagerAdapter
import com.rndtechnosoft.bconn.Fragment.ApproveMemberFragment
import com.rndtechnosoft.bconn.Fragment.PendingMemberFragment
import com.rndtechnosoft.bconn.Fragment.RejectedMemberFragment
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.databinding.ActivityManageMembersBinding

class ManageMembersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageMembersBinding
    private lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageMembersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        adapter = ViewPagerAdapter(this)
        adapter.addFragment(ApproveMemberFragment(), "Approve")
        adapter.addFragment(PendingMemberFragment(), "Pending")
        adapter.addFragment(RejectedMemberFragment(),"Rejected")

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()

    }
}