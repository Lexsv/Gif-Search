package ua.com.gifsearch.presenters.gifDialog

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_gif.*

import ua.com.gifsearch.R
import ua.com.gifsearch.di.gifDialog.DaggerGifDialogComponent
import ua.com.gifsearch.di.gifDialog.GifPresenterModule
import ua.com.gifsearch.reposetory.retrofit.GifObject
import javax.inject.Inject

class GifFragment(var item: GifObject) : DialogFragment(),IGifDialog.View {

    @Inject
    lateinit var mPresenter: IGifDialog.Presenter
    private val REQUEST_CODE = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_gif, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addDaggerDependence()
        addListeners()
        mPresenter.onStart(item)

    }

    private fun addDaggerDependence() {
        DaggerGifDialogComponent.builder()
            .gifPresenterModule(GifPresenterModule(this))
            .build()
            .inject(this)
    }

    private fun addListeners() {
        view_gif_close.setOnClickListener{
            dismiss()
        }

        view_gif_save.setOnClickListener {
            mPresenter.onSave(item)
        }
    }

    override fun getLifecycleOwner(): LifecycleOwner = this

    override fun viewGif(title: String, url: String) {
        view_gif_title.text = title
        Glide.with(this)
            .asGif()
            .load(url)
            .placeholder(R.drawable.ic_cloud_download)
            .into(view_gif_image)
    }

    override fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE -> {
               if ( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                  mPresenter.onSave(item)
                   dismissView("Gif сохранено в: \n Download/gif/")

               }else{
                    Toast.makeText(context,"Доступ запрещен",Toast.LENGTH_LONG).show()
               }
            }
        }
    }

    override fun dismissView(message : String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}