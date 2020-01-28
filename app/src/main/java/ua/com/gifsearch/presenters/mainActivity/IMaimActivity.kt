package ua.com.gifsearch.presenters.mainActivity

interface IMaimActivity {
    interface View
    interface Presenter{
        fun onStart()
        fun onSave()
    }
}