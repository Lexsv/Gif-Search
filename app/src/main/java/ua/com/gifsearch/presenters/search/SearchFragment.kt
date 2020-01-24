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
import ua.com.gifsearch.di.saerch.SearchPresenterModul
import ua.com.gifsearch.presenters.gifDialog.GifFragment
import ua.com.gifsearch.reposetory.retrofit.GifObject
import javax.inject.Inject

class SearchFragment: Fragment(), SwipyRefreshLayout.OnRefreshListener,ISearch.View {

    var searchRecyclerAdapter : SearchRecyclerAdapter? = null
    var queue = 10

    @Inject
    lateinit var mPresenter: ISearch.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addDaggerDepandance()
        addListeners()
        mPresenter.onStart()
    }




     override fun showRecyclerList(list: List<GifObject>) {
        searchRecyclerAdapter =
            SearchRecyclerAdapter(list,
                object : SearchRecyclerAdapter.Callback {
                    override fun onItemClicked(item: GifObject) {

                        mPresenter.onClickCardView(item)

                    }},
                object : SearchRecyclerAdapter.FavoritCallback{
                    override fun onFavoritClicked(item: GifObject) {
                       mPresenter.addInFavorite(item)
                    }})
         rv_search.adapter = searchRecyclerAdapter
         rv_search.layoutManager = GridLayoutManager(context,2)
    }



    override fun swohNextListGif(list: List<GifObject>){
        val scroll = rv_search.adapter!!.itemCount + 1
        searchRecyclerAdapter!!.listAdd(list)
        searchRecyclerAdapter!!.notifyDataSetChanged()
        rv_search.smoothScrollToPosition(scroll)
        swipyRefresh.isRefreshing = false
    }

    override fun finishLoad() {
        swipyRefresh.isRefreshing = false
        Toast.makeText(context,"Gif больше нет",Toast.LENGTH_LONG).show()
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection?) {
        queue +=10
        mPresenter.listNext(input_search.text.toString(),queue)
    }

    fun   addDaggerDepandance(){
        DaggerSearchComponent.builder()
            .searchPresenterModul(SearchPresenterModul(this))
            .build()
            .inject(this)
    }

    fun addListeners(){
        swipyRefresh.setOnRefreshListener(this)
        search_button.setOnClickListener {
            queue= 10
            mPresenter.loadListGif(input_search.text.toString(),queue)
            swipyRefresh.isRefreshing = true
        }

        input_search.setOnEditorActionListener{_, actionId: Int, _->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mPresenter.loadListGif(input_search.text.toString(), queue)
                hideKeyBoard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun hideKeyBoard(){
        (context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(cv_search.windowToken, 0)
    }
    override fun getLifecycleOwner(): LifecycleOwner = this

    override fun showGif (item : GifObject){
        GifFragment(item).show(childFragmentManager,"11")
    }

    override fun swohMessag(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    override fun hideProgsess() {
        if (swipyRefresh.isRefreshing) swipyRefresh.isRefreshing = false
    }
}