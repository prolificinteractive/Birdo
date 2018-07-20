package com.prolificinteractive.sample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.prolificinteractive.sample.CharactersAdapter.ItemViewHolder
import com.prolificinteractive.sample.R.layout
import com.prolificinteractive.sample.api.responses.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.image
import kotlinx.android.synthetic.main.item_character.view.name

class CharactersAdapter(var data: List<Character> = emptyList()) : RecyclerView.Adapter<ItemViewHolder>() {

  class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.image
    val name: TextView = view.name
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ItemViewHolder(inflater.inflate(layout.item_character, parent, false))
  }

  override fun getItemCount() = data.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    val item = data[position]

    holder.name.text = item.name

    Picasso.with(holder.image.context)
        .load(item.image)
        .into(holder.image)
  }
}