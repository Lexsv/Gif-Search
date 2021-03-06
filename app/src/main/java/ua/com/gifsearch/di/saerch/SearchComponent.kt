package ua.com.gifsearch.di.saerch

import dagger.Component
import ua.com.gifsearch.presenters.search.SearchFragment

@Component(modules = arrayOf(SearchViewProvider::class,SearchPresenterModule::class))
interface SearchComponent {
    fun inject(searchFragment: SearchFragment)
}