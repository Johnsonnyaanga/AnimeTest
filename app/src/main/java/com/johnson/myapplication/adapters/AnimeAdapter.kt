package com.johnson.myapplication.adapters


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.johnson.myapplication.R
import com.johnson.myapplication.data.Data


class AnimeAdapter(private val itemList: List<Data>) :
    RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    // ViewHolder to cache the views in the item layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movieTitleTextView)
        var poster:ImageView = itemView.findViewById(R.id.moviePosterImageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.anime_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title



        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
        Glide.with(holder.itemView.context).load(item.images.jpg.image_url).apply(options).into(holder.poster);







    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}