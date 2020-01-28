package ua.com.gifsearch.di.saerch

import dagger.Module
import ua.com.gifsearch.presenters.search.ISearch

@Module
class SearchViewProvider(var view: ISearch.View)