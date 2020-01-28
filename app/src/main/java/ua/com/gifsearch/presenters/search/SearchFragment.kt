package ua.com.gifsearch.presenters.search

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import kotlinx.android.synthetic.main.search.*
import ua.com.gifsearch.R
import ua.com.gifsearch.di.saerch.DaggerSearchComponent
import ua.com.gifsearch.di.saerch.SearchPresenterModule
import ua.com.gifsearch.presenters.gifDialog.GifFragment
import ua.com.gifsearch.reposetory.retrofit.GifObject
import javax.inject.Inject

class SearchFragment : Fragment(), SwipyRefreshLayout.OnRefreshListener, ISearch.View {

    private var searchRecyclerAdapter: SearchRecyclerAdapter? = null
    private var queue = 10
    private val SPAN_COUNT = 2

    @Inject
    lateinit var mPresenter: ISearch.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addDaggerDependence()
        addListeners()
        mPresenter.onStart()
    }


    override fun showRecyclerList(list: List<GifObject>) {
        searchRecyclerAdapter =
            SearchRecyclerAdapter(list,
                object : SearchRecyclerAdapter.Callback {
                    override fun onItemClicked(item: GifObject) {
                        mPresenter.onClickCardView(item)
                    }
                },
                object : SearchRecyclerAdapter.FavoriteCallback {
                    override fun onFavoriteClicked(item: GifObject) {
                        mPresenter.addInFavorite(item)
                    }
                })
        rv_search.adapter = searchRecyclerAdapter
        rv_search.layoutManager = GridLayoutManager(context, SPAN_COUNT)
    }


    override fun showNextListGif(list: List<GifObject>) {
        if (searchRecyclerAdapter == null && rv_search.adapter == null) {
            showRecyclerList(list)
        }else{
            val scroll = rv_search.adapter!!.itemCount + 1
            searchRecyclerAdapter!!.listAdd(list)
            searchRecyclerAdapter!!.notifyDataSetChanged()
            rv_search.smoothScrollToPosition(scroll)
            swipe_refresh.isRefreshing = false
        }



    }

    override fun finishLoad() {
        swipe_refresh.isRefreshing = false
        Toast.makeText(context, "Gif больше нет", Toast.LENGTH_LONG).show()
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection?) {
        queue += 10
        mPresenter.listNext(input_search.text.toString(), queue)
    }

    private fun addDaggerDependence() {
        DaggerSearchComponent.builder()
            .searchPresenterModule(SearchPresenterModule(this))
            .build()
            .inject(this)
    }

    private fun addListeners() {
        swipe_refresh.setOnRefreshListener(this)
        search_button.setOnClickListener {
            queue = 10
            mPresenter.loadListGif(input_search.text.toString(), queue)
            swipe_refresh.isRefreshing = true
        }

        input_search.setOnEditorActionListener { _, actionId: Int, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mPresenter.loadListGif(input_search.text.toString(), queue)
                hideKeyBoard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun hideKeyBoard() {
        (context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(cv_search.windowToken, 0)
    }

    override fun getLifecycleOwner(): LifecycleOwner = this

    override fun showGif(item: GifObject) {
        GifFragment(item).show(childFragmentManager, "11")
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgress() {
        if (swipe_refresh.isRefreshing) swipe_refresh.isRefreshing = false
    }
}