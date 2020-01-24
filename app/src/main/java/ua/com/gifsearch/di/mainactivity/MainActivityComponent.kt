package ua.com.gifsearch.di.mainactivity

import dagger.Component
import ua.com.gifsearch.MainActivity

@Component(modules = arrayOf(MainPresenterModul::class,MainViewProvid::class))
interface MainActivityComponent {
    fun inject( mainActivity: MainActivity)
}