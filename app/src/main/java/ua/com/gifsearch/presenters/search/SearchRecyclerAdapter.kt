package ua.com.gifsearch.presenters.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ua.com.gifsearch.R
import ua.com.gifsearch.reposetory.retrofit.GifObject
import ua.com.gifsearch.utils.resourceToSring


class SearchRecyclerAdapter(list: List<GifObject>, val callback: Callback, val favoriteCallback: FavoriteCallback): RecyclerView.Adapter<SearchRecyclerAdapter.RecyclerHolder>()  {


    var listInner :ArrayList<GifObject> = list as ArrayList

    fun listAdd(lists: List<GifObject>){
        listInner.addAll(lists)}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder = RecyclerHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_recycle, parent, false))

    override fun getItemCount(): Int = listInner.size

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) { holder.bind(listInner[position]) }
    inner class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val imageView = itemView.findViewById<ImageView>(R.id.rv_image)
        private val title = itemView.findViewById<TextView>(R.id.rv_title)
        private val favoriteButton = itemView.findViewById<ImageView>(R.id.rv_favorite_button)

        fun bind(item: GifObject) {
            if (item.favorite)favoriteButton.setImageResource(R.drawable.ic_favorite_on)

            if (item.title == ""){
                val defaultName = resourceToSring(R.string.no_name)
                title.text = defaultName
            } else title.text = item.title

            Glide.with(imageView)
                .load(item.media[0].gif.preview)
                .placeholder(R.drawable.ic_cloud_download)
                .into(imageView)
                favoriteButton.setOnClickListener{
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        if (!item.favorite){
                            item.favorite = true
                            favoriteButton.setImageResource(R.drawable.ic_favorite_on)}
                        else {
                            item.favorite = false
                            favoriteButton.setImageResource(R.drawable.ic_favorite_off)}

                        favoriteCallback.onFavoriteClicked(listInner[adapterPosition])}
                }
                itemView.setOnClickListener{
                    if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(listInner[adapterPosition])
                }
        }

    }

    interface Callback {
        fun onItemClicked(item: GifObject)
    }

    interface FavoriteCallback{
        fun onFavoriteClicked(item: GifObject)


    }

}