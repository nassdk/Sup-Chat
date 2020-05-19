package com.nassdk.supchat.domain.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatFragment
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.main.ui.MainFlowFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val resourceLayout: Int
    protected val subscriptions = CompositeDisposable()

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            layoutInflater.inflate(resourceLayout, container, false)

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }

    fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(getString(R.string.warning_message_title))
                .setMessage(getString(R.string.warning_no_internet_message))
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.warning_alert_exit_button_title)
                ) { _, _ -> activity?.finish() }
        val alert = builder.create()
        alert.show()
    }

    protected fun setupToolbar(
            title: String,
            showNavIcon: Boolean = false,
            @DrawableRes iconDrawable: Int = R.drawable.ic_action_navigation_arrow_back_inverted
    ) {
        val toolbar: Toolbar? = view?.findViewById(R.id.toolBar)
        toolbar?.run {
            if (showNavIcon) {
                setNavigationIcon(iconDrawable)
                setNavigationOnClickListener { onBackPressed() }
            }
            this.title = title
        }
    }

    protected fun getBaseFragment() = (parentFragment as MainFlowFragment)

    abstract fun onBackPressed()
    open fun showBottomNavigation(show: Boolean) {}
}