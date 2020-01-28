package ua.com.gifsearch.di.mainactivity

import dagger.Module
import ua.com.gifsearch.presenters.mainActivity.IMaimActivity


@Module
class MainViewProvider(var view: IMaimActivity.View)