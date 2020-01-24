package ua.com.gifsearch.di.saerch

import dagger.Module
import ua.com.gifsearch.presenters.search.ISearch

@Module
class SearchViewProvid(var view: ISearch.View) {
}