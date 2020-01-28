package ua.com.gifsearch.di.mainactivity

import dagger.Component
import ua.com.gifsearch.MainActivity

@Component(modules = arrayOf(MainPresenterModule::class,MainViewProvider::class))
interface MainActivityComponent {
    fun inject( mainActivity: MainActivity)
}