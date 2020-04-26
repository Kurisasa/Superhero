package com.kurisani.superhero.module.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kurisani.superhero.SuperheroApplication
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kurisani.superhero.R
import com.kurisani.superhero.activity.BaseActivity
import com.kurisani.superhero.util.AlertUtil
import com.kurisani.superhero.util.Util
import kotlinx.android.synthetic.main.activity_home.*
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class HomeActivity @Inject constructor() : BaseActivity(), FragmentManager.OnBackStackChangedListener {

    private val homeNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_play -> {
                loadHomeView()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_invites -> {
                AlertUtil.toastInfo(this, this.getString(R.string.new_features))
            }
            R.id.navigation_shop -> {
                AlertUtil.toastInfo(this, this.getString(R.string.new_features))
            }
            R.id.navigation_profile -> {
                AlertUtil.toastInfo(this, this.getString(R.string.new_features))
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out)
        SuperheroApplication.instance!!.getDependencyComponent().inject(this)
        bottomNavigation.setOnNavigationItemSelectedListener(homeNavigationItemSelectedListener)

        loadHomeView()
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.addOnBackStackChangedListener(this)


        val view = getCurrentView()
        if (view is HomeFragment) {
           // view.loadData()
        }
    }

    override fun onBackPressed() {
       Util.dismissKeyboard(this)

        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount <= 1) {

            finish()
        } else {
            popView()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackStackChanged() {
        val view = getCurrentView()
        view?.onResume()
    }

    fun loadHomeData() {
        val view = supportFragmentManager.findFragmentByTag(
            HomeFragment::class.java.name
        ) as HomeFragment?

        //view?.loadData()
    }

    /**
     * Load home view
     */
    fun loadHomeView() {
        if (getCurrentView() != null && StringUtils.equalsIgnoreCase(
                getCurrentView()!!.tag, HomeFragment::class.java.name
            )
        ) {
            return
        }
        var view: HomeFragment? = null
        try {
            view = supportFragmentManager.findFragmentByTag(
                HomeFragment::class.java.name
            ) as HomeFragment?
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.message, e)
        }

        if (view != null) {
            supportFragmentManager.popBackStackImmediate(HomeFragment::class.java.name, 0)
        } else {
            view = HomeFragment()
            loadView(view, addToBackStack = true, animate = false)
        }
    }

    private fun loadView(view: Fragment, addToBackStack: Boolean) {
        loadView(view, addToBackStack, animate = true)
    }

    private fun loadView(view: Fragment, addToBackStack: Boolean, animate: Boolean) {
        loadView(view, addToBackStack, animate, R.anim.push_up_in, R.anim.push_up_out, checkCurrent = true)
    }

    private fun loadView(view: Fragment, addToBackStack: Boolean, animate: Boolean, animateIn: Int, animateOut: Int) {
        loadView(view, addToBackStack, animate, animateIn, animateOut, checkCurrent = true)
    }

    private fun loadView(
        view: Fragment, addToBackStack: Boolean, animate: Boolean, animateIn: Int, animateOut: Int,
        checkCurrent: Boolean
    ) {
        if (checkCurrent
            && getCurrentView() != null
            && StringUtils.equalsIgnoreCase(getCurrentView()!!.tag, view.javaClass.name)
        ) {
            return
        }
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // First detach current view
        val currentFragment = getCurrentView()
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }
        // Then add current view
        if (animate) {
            fragmentTransaction.setCustomAnimations(
                animateIn,
                animateOut,
                animateIn,
                animateOut
            )
        }

        val existingFragment = fragmentManager.findFragmentByTag(view.javaClass.name)
        // If fragment already exists, reuse that one instead
        if (existingFragment != null) {
            for (i in fragmentManager.backStackEntryCount - 1 downTo 1) {
                if (!fragmentManager.getBackStackEntryAt(i).name.equals(view.javaClass.name, true)) {
                    fragmentManager.popBackStack()
                } else {
                    break
                }
            }
        } else {
            fragmentTransaction.add(R.id.container_body, view, view.javaClass.name)
            if (addToBackStack) fragmentTransaction.addToBackStack(view.javaClass.name)
            fragmentTransaction.commit()
        }
    }

    private fun getCurrentView(): Fragment? {
        val fragmentManager = supportFragmentManager
        return if (fragmentManager.backStackEntryCount > 0) {
            val fragmentTag = fragmentManager.getBackStackEntryAt(
                fragmentManager.backStackEntryCount - 1
            ).name
            fragmentManager.findFragmentByTag(fragmentTag)
        } else {
            null
        }
    }

    private fun popView() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentManager.popBackStack()
        val currentFragment = getCurrentView()
        if (currentFragment != null) {
            fragmentTransaction.show(currentFragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun setSelectedNavItem(position: Int) {
        bottomNavigation.menu.getItem(position).isChecked = true
    }
}