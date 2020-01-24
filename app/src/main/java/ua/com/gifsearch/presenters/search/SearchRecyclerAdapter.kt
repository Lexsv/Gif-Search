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


class SearchRecyclerAdapter(var list: List<GifObject>, val callback: Callback, val favoritCallback: FavoritCallback): RecyclerView.Adapter<SearchRecyclerAdapter.RecyclerHolder>()  {


    var listIner :ArrayList<GifObject>
    init {
       listIner = list as ArrayList
    }

    fun listAdd(lists: List<GifObject>){
        listIner.addAll(lists)}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder = RecyclerHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_recycle, parent, false))

    override fun getItemCount(): Int = listIner.size

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) { holder.bind(listIner[position]) }
    inner class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val imageView = itemView.findViewById<ImageView>(R.id.rv_image)
        private val title = itemView.findViewById<TextView>(R.id.rv_title)
        private val favoritButtn = itemView.findViewById<ImageView>(R.id.rv_follovingButtn)

        fun bind(item: GifObject) {
            if (item.favorite)favoritButtn.setImageResource(R.drawable.ic_favorite_on)

            if (item.title == ""){
                title.text = "No name"
            } else title.text = item.title

            Glide.with(imageView)
                .load(item.media[0].gif.preview)
                .placeholder(R.drawable.ic_cloud_download)
                .into(imageView)
                favoritButtn.setOnClickListener{
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        if (!item.favorite){
                            item.favorite = true
                            favoritButtn.setImageResource(R.drawable.ic_favorite_on)}
                        else {
                            item.favorite = false
                            favoritButtn.setImageResource(R.drawable.ic_favorite_off)}

                        favoritCallback.onFavoritClicked(listIner[adapterPosition])}
                }
                itemView.setOnClickListener{
                    if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(listIner[adapterPosition])
                }
        }

    }

    interface Callback {
        fun onItemClicked(item: GifObject)
    }

    interface FavoritCallback{
        fun onFavoritClicked(item: GifObject)


    }

}