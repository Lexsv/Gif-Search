package ua.com.gifsearch.di.gifDialog


import dagger.Component
import ua.com.gifsearch.presenters.gifDialog.GifFragment

@Component(modules = arrayOf(GifFragmentProvider::class,GifPresenterModule::class))
interface GifDialogComponent {
    fun inject(view: GifFragment)
}