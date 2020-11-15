package com.example.moviesapp.features.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.moviesapp.R
import com.example.moviesapp.data.model.Genre
import kotlinx.android.synthetic.main.genre_item_layout.view.*

class GenresAdapter(val genres: List<Genre>,val context: Context):BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val genre = this.genres[position]
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val card= inflator.inflate(R.layout.genre_item_layout,null)
        card.titleTv.text = genre.name
        return card
    }

    override fun getItem(position: Int): Any {
        return genres[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return genres.size
    }
}