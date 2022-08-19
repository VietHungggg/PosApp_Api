package com.example.posapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.posapp.fragments.admin.AdminMealFragment
import com.example.posapp.fragments.admin.AdminReceiptFragment
import com.example.posapp.fragments.admin.AdminUserFragment


class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {

        //  3 fragment -> return 3
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AdminUserFragment()
            }
            1 -> {
                AdminReceiptFragment()
            }
            2 -> {
                AdminMealFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}