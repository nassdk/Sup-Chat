package com.nassdk.supchat.domain.extensions

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.nassdk.supchat.R

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

val emailRegex = """(.+)@(.+)\.(.+)""".toRegex()


inline fun <reified T : View> Fragment.find(id: Int): T = view?.findViewById(id) as T

inline fun <T : Fragment> T.withArgs(
        argsBuilder: Bundle.() -> Unit
): T =
        this.apply {
            arguments = Bundle().apply(argsBuilder)
        }

internal fun View.makeGone() {
    visibility = View.GONE
}

internal fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

internal fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun Fragment.toast(message: String) {
    this.context!!.toast(message)
}

fun Fragment.longToast(message: String) {
    this.context!!.longToast(message)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun EditText.text() = this.text.toString()

fun View.accessible(access: Boolean) {

    val background = if (access)
        R.drawable.background_ripple_selector_base_pink
    else
        R.drawable.backgronud_gray_background_rounded_eighteen

    setBackground(ContextCompat.getDrawable(this.context, background))
    isEnabled   = access
}
