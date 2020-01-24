package ua.com.gifsearch.di.gifDialog

import android.view.View
import dagger.Component
import ua.com.gifsearch.presenters.gifDialog.GifFragment

@Component(modules = arrayOf(GifFragmentProvid::class,GifPresenterModule::class))
interface GifDialodComponent {
    fun inject(view: GifFragment)
}