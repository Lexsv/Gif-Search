package ua.com.gifsearch.di.gifDialog

import dagger.Module
import dagger.Provides
import ua.com.gifsearch.presenters.gifDialog.GifPresenter
import ua.com.gifsearch.presenters.gifDialog.IGifDialog


@Module
class GifPresenterModule(var view: IGifDialog.View) {
    @Provides
    fun getGifPresenter(): IGifDialog.Presenter = GifPresenter(view)
}