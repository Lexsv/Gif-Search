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
import ua.com.gifsearch.di.mainactivity.MainPresenterModul
import ua.com.gifsearch.presenters.mainActivity.IMaimActivity
import ua.com.gifsearch.presenters.mainActivity.PagerAdapt

import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnTabSelectedListener,IMaimActivity.View {

    @Inject
    lateinit var mPresenter: IMaimActivity.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       addDaggerDepandance()
        mPresenter.onStart()
        initTabLayoutAndViewPager()

    }

    private fun addDaggerDepandance() {
        DaggerMainActivityComponent.builder()
            .mainPresenterModul(MainPresenterModul(this))
            .build()
            .inject(this)
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onSave()
    }

    private fun initTabLayoutAndViewPager() {
        tabsLayout.addTab(tabsLayout.newTab().setIcon(R.drawable.ic_search))
        tabsLayout.addTab(tabsLayout.newTab().setIcon(R.drawable.ic_favorite_on))
        tabsLayout.setTabGravity(TabLayout.GRAVITY_FILL)
        tabsLayout.addOnTabSelectedListener(this)
        viewPager.adapter = PagerAdapt(supportFragmentManager,tabsLayout.tabCount)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsLayout))
    }

    override fun onTabReselected(tab: TabLayout.Tab?){}

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        viewPager.setCurrentItem(tab!!.getPosition())
        hideKeyBoard()
    }


    private fun hideKeyBoard(){
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(cv_search.windowToken, 0)
    }
}
