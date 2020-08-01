package com.nassdk.supchat.presentation.searchusers.ui

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.domain.model.User
import com.jakewharton.rxbinding2.widget.RxSearchView
import com.nassdk.supchat.R
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.presentation.searchusers.mvp.SearchPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.screen_search.*
import kotlinx.android.synthetic.main.screen_search.view.*
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment(), com.nassdk.supchat.presentation.searchusers.mvp.SearchView {

    override val resourceLayout = R.layout.screen_search

    private lateinit var searchInputField: EditText

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    private val adapter: UserAdapter by lazy {
        UserAdapter(onUserClicked = { presenter.toProfileScreen(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view = view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation(show = true)
    }

    private fun initViews(view: View) {

        showBottomNavigation(show = false)
        view.recView_SearchUsers.adapter = adapter

        initToolbar()
        initSearchMenu()
    }

    private fun initToolbar() {

        toolBarSearch.run {

            if (menu.size() < 1) {
                inflateMenu(R.menu.menu_search)
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.action_search -> {
                            initSearchMenu()
                            true
                        }
                        else -> false
                    }
                }
            }

            setNavigationOnClickListener { presenter.onBackPressed() }
        }
    }

    private fun initSearchMenu() {

        val menuItem = toolBarSearch.menu.getItem(0)

        val searchView = menuItem.actionView as SearchView

        val searchEditTextIdentifier = searchView.context.resources.getIdentifier("android:id/search_src_text", null, null)

        searchInputField = searchView.findViewById(searchEditTextIdentifier)

        searchInputField.run {

            setTextColor(Color.WHITE)
            setHintTextColor(ContextCompat.getColor(context, R.color.gray999999))
            setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.text_size_14)
            )
        }

        searchView.run {
            maxWidth = android.R.attr.width
            isIconified = false
            isIconifiedByDefault = true
            searchInputField.requestFocus()

            RxSearchView.queryTextChanges(this)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .skip(2)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        presenter.searchUser(searchQuery = it.toString())
                    }, {

                    })

        }
    }

    override fun showBottomNavigation(show: Boolean) {
        getBaseFragment().showBottomNavigation(show = show)
    }

    override fun setData(usersList: ArrayList<User>) {
        adapter.setData(users = usersList)
    }

    override fun onBackPressed() = presenter.onBackPressed()
}