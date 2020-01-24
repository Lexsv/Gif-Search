package ua.com.gifsearch.di.gifDialog

import dagger.Module
import ua.com.gifsearch.presenters.gifDialog.IGifDialog

@Module
class GifFragmentProvid(var view: IGifDialog.View) {
}