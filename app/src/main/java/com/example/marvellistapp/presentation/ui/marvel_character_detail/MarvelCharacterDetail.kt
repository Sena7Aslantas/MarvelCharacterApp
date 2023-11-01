package com.example.marvellistapp.presentation.ui.marvel_character_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import coil.load
import com.example.marvellistapp.R
import com.example.marvellistapp.databinding.FragmentMarvelCharacterDetailBinding
import com.example.marvellistapp.domain.model.Comics
import com.example.marvellistapp.presentation.bindingadapter.BindingFragment
import com.example.marvellistapp.presentation.ui.activites.MainActivity
import com.example.marvellistapp.presentation.ui.marvel_character_detail.adapter.ComicsAdapter
import com.example.marvellistapp.presentation.ui.marvel_character_detail.state.MarvelDetailState
import com.example.marvellistapp.presentation.ui.marvel_character_detail.viewmodel.MarvelCharacterDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MarvelCharacterDetail : BindingFragment<FragmentMarvelCharacterDetailBinding>() {
    private val marvelCharacterDetailViewModel: MarvelCharacterDetailViewModel by viewModels()
    private val comicList: ArrayList<Comics> = ArrayList()
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMarvelCharacterDetailBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeModel()
    }

    private fun observeModel() {
        lifecycleScope.launch {
            marvelCharacterDetailViewModel.marvelCharacter.observe(viewLifecycleOwner) { state ->
                showError(state)
                binding.viewError.root.isVisible = state.errorShowing
                binding.progressBar.isVisible = state.isLoading
                setCharacterView(state)
                setCharacterComics(state)
            }
        }
    }


    private fun showError(state: MarvelDetailState) {
        if (state.error.isNotBlank()) {
            binding.viewError.tvNoConnection.text = state.error
            binding.viewError.retry.setOnClickListener {
                lifecycleScope.launch {
                    marvelCharacterDetailViewModel.getMarvelCharacterByIdAndComicsData()
                }
            }
        }
    }


    private fun setCharacterView(state: MarvelDetailState) {
        binding.apply {
            state.character?.let { marvelCharacter ->
                val imageUrl =
                    marvelCharacter.thumbnail.path + '.' + marvelCharacter.thumbnail.extension
                characterImage.load(imageUrl) {
                    crossfade(300)
                }
                characterName.text = marvelCharacter.name
                (requireActivity() as MainActivity).title = marvelCharacter.name + "\t\tComics"

                if (marvelCharacter.description?.isNotEmpty() == true) {
                    characterDescription.isVisible = true
                    characterDescription.text = marvelCharacter.description
                }
            }
        }
    }

    private fun setCharacterComics(state: MarvelDetailState) {
        binding.apply {
            if (state.comics.isNotEmpty()) {
                val adapter = ComicsAdapter()
                state.comics.forEach {
                    val titleDate = it.title.split(" ").toList()
                    titleDate.forEach { str ->
                        val date = str.replace("(", "").replace(")", "")
                        if (date.toIntOrNull() != null) {
                            it.date = date
                        }
                    }
                    if (!it.date.isNullOrEmpty()) {
                        if (it.date!!.toInt() >= 2005) {
                            comicList.add(it)
                        }
                    }
                }
                if (comicList.isNotEmpty()) {
                    comicsText.isVisible = true
                }
                print(comicList)
                adapter.setData(comicList.sortedByDescending {
                    it.date
                })
                binding.recyclerview.adapter = adapter
            }
        }
    }



}