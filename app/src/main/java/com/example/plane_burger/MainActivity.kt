package com.example.plane_burger

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.plane_burger.databinding.ActivityMainBinding


class MainActivity :
    AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val toolbar = initToolbar()
        toolbar.setTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        toolbar.setBackgroundColor(
            ContextCompat.getColor(this, R.color.dark_dark_grey)
        )

        initDrawer(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        drawerLayout.setOnTouchListener { view, event -> // Consume the touch event to prevent the drawer from closing
            true
        }

        replaceFragment(ListFragment())
    }

    private fun initToolbar(): Toolbar {
        val toolbar = binding?.appBarMain?.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        binding?.appBarMain?.back?.setOnClickListener {
            super.onBackPressed()
        }
        return toolbar!!
    }

    private fun initDrawer(toolbar: Toolbar) {
        val drawer = binding?.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()

        setupDrawerMenu()
    }
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//
//        when (id) {
//            R.id.first_option -> {
//                replaceFragmentForPlaneFragment(0)
//            }
//
//            R.id.second_option -> {
//                replaceFragmentForPlaneFragment(1)
//            }
//
//            R.id.third_option -> {
//                replaceFragmentForPlaneFragment(2)
//            }
//
//            R.id.forth_option -> {
//                replaceFragmentForPlaneFragment(3)
//            }
//        }
//        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//        //drawer.closeDrawer(GravityCompat.START)
//        return true
//    }

    private fun replaceFragmentForPlaneFragment(i: Int) {
        val fragment = PlaneFragment.newInstance(i)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null).commit()
    }

    private fun setupDrawerMenu() {
        binding?.navView?.apply {
            val headerView = this.getHeaderView(0)
            val menuLayout = headerView.findViewById<LinearLayout>(R.id.menu_layout)
            val close = headerView.findViewById<ImageView>(R.id.close)
            close.setOnClickListener {
                binding?.drawerLayout?.close()
            }

            for (i in 0..3) {
                val textView = TextView(this@MainActivity)
                textView.text = getTitle(resources, i)
                textView.textSize = 20f
                textView.setTextColor(ContextCompat.getColor(context, R.color.white))
                textView.setOnClickListener {
                    val fragment = PlaneFragment.newInstance(i)

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit()
                }
                menuLayout.addView(textView)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun changeTitle(title: String) {
        binding?.appBarMain?.title?.text = title
    }

    fun setDefaultTitle() {
        binding?.appBarMain?.title?.text = resources.getText(R.string.airplanes)
    }

    fun setArrowVisibilityToTrue() {
        binding?.navView?.apply {
            binding?.appBarMain?.back?.visibility = View.VISIBLE
        }
    }

    fun setArrowVisibilityToFalse() {
        binding?.navView?.apply {
            binding?.appBarMain?.back?.visibility = View.GONE
        }
    }
}