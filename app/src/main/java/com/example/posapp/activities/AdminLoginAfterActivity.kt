package com.example.posapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.example.posapp.R
import com.example.posapp.adapters.ViewPagerAdapter
import com.example.posapp.databinding.ActivityAdminLoginAfterBinding
import com.example.posapp.databinding.ActivityLoginBinding
import com.example.posapp.db.MealDatabase
import com.example.posapp.viewModel.HomeViewModel
import com.example.posapp.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AdminLoginAfterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLoginAfterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginAfterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout_dashboard)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Customer"
                }
                1 -> {
                    tab.text = "Meals"
                }
                2 -> {
                    tab.text = "Receipts"
                }
            }
        }.attach()

        registerForContextMenu(binding.imgThreePoint)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.btn_three_point, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.admin_home -> {
                Toast.makeText(this, "Admin Home", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.admin_edit_info -> {
                Toast.makeText(this, "Admin info", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.admin_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }
}