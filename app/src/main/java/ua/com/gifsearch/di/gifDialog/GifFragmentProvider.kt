package ua.com.gifsearch.di.gifDialog

import dagger.Module
import ua.com.gifsearch.presenters.gifDialog.IGifDialog

@Module
class GifFragmentProvider(var view: IGifDialog.View)