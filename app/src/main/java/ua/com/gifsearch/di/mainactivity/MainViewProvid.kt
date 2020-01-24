package ua.com.gifsearch.di.mainactivity

import dagger.Module
import ua.com.gifsearch.presenters.mainActivity.IMaimActivity


@Module
class MainViewProvid(var view: IMaimActivity.View) {
}