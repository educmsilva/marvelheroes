package com.example.marvelheroes.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.marvelheroes.R
import com.example.marvelheroes.util.model.Result
import kotlinx.android.synthetic.main.super_hero_item.view.*

class SuperHeroesListAdapter(
    private val superHeroes: List<Result>,
    private val context: Context,
    private var onItemClickListener: (superHero: Result, position: Int) -> Unit
) : Adapter<SuperHeroesListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val superHero = superHeroes[position]
        holder.let {
            it.bindView(superHero)
            it.itemView.setOnClickListener {
                onItemClickListener(superHero, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.super_hero_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return superHeroes.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(superHero: Result) {
            val image = itemView.imageView_Hero
            val description = itemView.textView_heroName

            Glide.with(image.context)
                .load(superHero.thumbnail.path+"."+superHero.thumbnail.extension)
                .into(image)

            description.text = superHero.name
        }
    }
}