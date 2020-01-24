package ua.com.gifsearch.di.mainactivity

import dagger.Module
import dagger.Provides
import ua.com.gifsearch.presenters.mainActivity.IMaimActivity
import ua.com.gifsearch.presenters.mainActivity.MainPresenter


@Module
class MainPresenterModul(var view: IMaimActivity.View) {
    @Provides
    fun getMainPresenterI(): IMaimActivity.Presenter = MainPresenter(view)
}