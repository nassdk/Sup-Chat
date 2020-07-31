package com.nassdk.supchat

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.di.customnavigation.CustomRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nassdk.supchat.global.BaseActivity
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.global.BasePresenter
import com.nassdk.supchat.global.navigation.Screens
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import android.graphics.Rect as Rect1

class AppActivity : BaseActivity() {

    override val resourceLayout = R.layout.activit_base

    private val router: CustomRouter by inject()
    private val navigationHolder: NavigatorHolder by inject()

    private val navigator: Navigator by lazy {
        SupportAppNavigator(this, supportFragmentManager, R.id.container)
    }

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?

    override fun handleLaunch() {
        router.newRootChain(Screens.SplashScreen)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        //sendStatus("offline")
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: router.exit()
    }

    private fun sendStatus(status: String) {

        val fbUser = FirebaseAuth.getInstance().currentUser
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference(BasePresenter.USERS).child(fbUser!!.uid)

        reference.updateChildren(hashMapOf("status" to status) as Map<String, Any>)
    }

    override fun onPostResume() {
        super.onPostResume()
        //sendStatus("online")
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect1()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
