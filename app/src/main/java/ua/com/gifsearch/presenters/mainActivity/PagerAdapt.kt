package ua.com.gifsearch.presenters.mainActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ua.com.gifsearch.presenters.favorite.FavoriteFragment
import ua.com.gifsearch.presenters.search.SearchFragment

class PagerAdapt(fm: FragmentManager, var tabCount: Int): FragmentStatePagerAdapter(fm,
                                                    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
    override fun getItem(position: Int): Fragment {
        if (position == 0)return SearchFragment()
        else return FavoriteFragment()
    }

    override fun getCount() = tabCount
}