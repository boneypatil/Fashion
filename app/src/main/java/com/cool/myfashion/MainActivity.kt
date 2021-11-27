package com.cool.myfashion

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.cool.myfashion.base.BaseDashboardFragment
import com.cool.myfashion.model.Images
import com.cool.myfashion.ui.DetailsScreenFragment
import com.cool.myfashion.ui.MainFragment

class MainActivity : AppCompatActivity(), BaseDashboardFragment.DashboardListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        selectScreen(MyFashionDashboardScreen.DashboardScreen)

    }


    private fun selectScreen(
        dashboardScreen: MyFashionDashboardScreen,
        bundleToSend: Bundle? = null
    ) {
        val fragment: BaseDashboardFragment = when (dashboardScreen) {
            MyFashionDashboardScreen.DashboardScreen -> MainFragment()
            MyFashionDashboardScreen.DetailScreen -> DetailsScreenFragment()
        }
        fragment.setListener(this)
        transactFragment(fragment, R.id.container, arguments = bundleToSend)
    }

    private enum class MyFashionDashboardScreen {
        DashboardScreen, DetailScreen
    }

    private fun transactFragment(
        frag: Fragment,
        @IdRes fragmentContainer: Int,
        arguments: Bundle? = null,
        retain: Boolean = true
    ) {
        val transaction = supportFragmentManager.beginTransaction()
            .apply {
                if (retain) addToBackStack(null)
                replace(fragmentContainer, frag, frag.tag)
            }
        arguments?.let {
            frag.arguments = it
        }

        transaction
            .commit()
    }

    override fun onImageSelected(images: Images) {
        selectScreen(
            MyFashionDashboardScreen.DetailScreen,
            bundleOf(Pair(getString(R.string.images_selected), images))
        )
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else
            this.finish()
    }


}