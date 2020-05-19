package com.nassdk.supchat.presentation.main.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.makeGone
import com.nassdk.supchat.domain.extensions.makeVisible
import com.nassdk.supchat.domain.global.BaseFragment
import kotlinx.android.synthetic.main.screen_main_flow.*



class MainFlowFragment : BaseFragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    override val resourceLayout = R.layout.screen_main_flow

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun showBottomNavigation(show: Boolean) {

        if (show) bottomNavigationView.makeVisible() else bottomNavigationView.makeGone()

        val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        )

        layoutParams.setMargins(
                0, 0, 0,
                if (show)
                    resources.getDimension(R.dimen.materialize_toolbar).toInt()
                else
                    0
        )

        profileTabContainer.layoutParams = layoutParams
        newTabContainer    .layoutParams = layoutParams
        myChatsTabContainer.layoutParams = layoutParams
    }


    override fun onBackPressed() {}
}