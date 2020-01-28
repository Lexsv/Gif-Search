package ua.com.gifsearch.di.saerch

import dagger.Module
import dagger.Provides
import ua.com.gifsearch.presenters.search.ISearch
import ua.com.gifsearch.presenters.search.SearchPresenter

@Module
class SearchPresenterModule(var view: ISearch.View) {
    @Provides
    fun getSearchModule():ISearch.Presenter = SearchPresenter(view)

}