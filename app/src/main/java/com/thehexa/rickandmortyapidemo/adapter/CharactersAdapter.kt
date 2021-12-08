package com.thehexa.rickandmortyapidemo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.thehexa.rickandmortyapidemo.model.Character
import com.thehexa.rickandmortyapidemo.databinding.ItemCharacterBinding
import com.google.gson.JsonObject

class CharactersAdapter(private val listener: CharacterItemListener) : RecyclerView.Adapter<CharacterViewHolder>() {

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Int, locationId: Int)
    }

    private val items = ArrayList<Character>()

    fun setItems(items: ArrayList<Character>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = holder.bind(items[position])
}

class CharacterViewHolder(private val itemBinding: ItemCharacterBinding, private val listener: CharactersAdapter.CharacterItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var character: Character

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Character) {
        this.character = item
        itemBinding.name.text = item.name
        itemBinding.species.text = item.species
        itemBinding.status.text = item.status
        Glide.with(itemBinding.root)
            .load(item.image)
            .transform(CircleCrop())
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(character.id, getLocationId(character.location))
    }

    private fun getLocationId(location: JsonObject): Int {
        val locationUrl = location.get("url").toString()
        Log.v("Location url:", locationUrl)
        val number = locationUrl.split("location/")[1]
        Log.v("Loc id:", number.dropLast(1))
        return number.dropLast(1).toInt()
    }
}

