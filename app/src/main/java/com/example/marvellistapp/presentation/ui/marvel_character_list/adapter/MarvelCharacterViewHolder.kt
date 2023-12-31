package com.example.marvellistapp.presentation.ui.marvel_character_list.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.marvellistapp.databinding.CharacterRowLayoutBinding
import com.example.marvellistapp.domain.model.MarvelCharacter

class MarvelCharacterViewHolder(private val binding: CharacterRowLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(marvelCharacter: MarvelCharacter, onItemClickListener: ((Int) -> Unit)?) {
        val imageUrl =
            marvelCharacter.thumbnail.path + "." + marvelCharacter.thumbnail.extension
        binding.characterImage.load(imageUrl) {
            crossfade(300)
        }
        binding.characterName.text = marvelCharacter.name

        binding.characterCard.setOnClickListener {
            onItemClickListener?.invoke(marvelCharacter.id)
        }
    }
}