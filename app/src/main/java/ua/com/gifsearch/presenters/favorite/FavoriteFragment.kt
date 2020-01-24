package ua.com.gifsearch.presenters.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.favorite.*
import kotlinx.android.synthetic.main.search.*
import ua.com.gifsearch.R
import ua.com.gifsearch.presenters.gifDialog.GifFragment
import ua.com.gifsearch.presenters.search.SearchRecyclerAdapter
import ua.com.gifsearch.reposetory.localstore.Store
import ua.com.gifsearch.reposetory.retrofit.GifObject

class FavoriteFragment : Fragment() {

    var favoriteRecyclerAdapter : FavoriteRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Store.getListFavorite().observe(this, Observer {
            showRecyclerList(it)
        })


    }


    fun showRecyclerList(list: List<GifObject>) {
        favoriteRecyclerAdapter =
            FavoriteRecyclerAdapter(list,
                object : FavoriteRecyclerAdapter.Callback {
                    override fun onItemClicked(item: GifObject) {
                        showGif(item)
                    }},
                object : FavoriteRecyclerAdapter.UnFavoritCallback{
                    override fun onFavoritClicked(item: GifObject) {
                        favoriteRecyclerAdapter!!.listRemove(item)
                        favoriteRecyclerAdapter!!.notifyDataSetChanged()

                    }})
        rv_favorite.adapter = favoriteRecyclerAdapter
        rv_favorite.layoutManager = GridLayoutManager(context,2)
    }


    fun showGif (item : GifObject){
        GifFragment(item).show(childFragmentManager,"11")
    }
}