package ua.com.gifsearch

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search.*
import ua.com.gifsearch.di.mainactivity.DaggerMainActivityComponent
import ua.com.gifsearch.di.mainactivity.MainPresenterModule
import ua.com.gifsearch.presenters.mainActivity.IMaimActivity
import ua.com.gifsearch.presenters.mainActivity.PagerAdapt

import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnTabSelectedListener,IMaimActivity.View {

    @Inject
    lateinit var mPresenter: IMaimActivity.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       addDaggerDependence()
        mPresenter.onStart()
        initTabLayoutAndViewPager()

    }

    private fun addDaggerDependence() {
        DaggerMainActivityComponent.builder()
            .mainPresenterModule(MainPresenterModule(this))
            .build()
            .inject(this)
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onSave()
    }

    private fun initTabLayoutAndViewPager() {
        tabs_layout.addTab(tabs_layout.newTab().setIcon(R.drawable.ic_search))
        tabs_layout.addTab(tabs_layout.newTab().setIcon(R.drawable.ic_favorite_on))
        tabs_layout.tabGravity = TabLayout.GRAVITY_FILL
        tabs_layout.addOnTabSelectedListener(this)
        view_pager.adapter = PagerAdapt(supportFragmentManager,tabs_layout.tabCount)
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs_layout))
    }

    override fun onTabReselected(tab: TabLayout.Tab?){}

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null) {
            view_pager.currentItem = tab.position
            hideKeyBoard()
        }
    }


    private fun hideKeyBoard(){
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(cv_search.windowToken, 0)
    }
}
