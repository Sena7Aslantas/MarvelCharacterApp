package com.example.marvellistapp.presentation.ui.marvel_character_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding
import com.example.marvellistapp.R
import com.example.marvellistapp.databinding.FragmentMarvelCharacterListBinding
import com.example.marvellistapp.presentation.bindingadapter.BindingFragment
import com.example.marvellistapp.presentation.ui.marvel_character_list.adapter.MarvelCharacterAdapter
import com.example.marvellistapp.presentation.ui.marvel_character_list.viewmodel.MarvelCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



@AndroidEntryPoint
class MarvelCharacterList : BindingFragment<FragmentMarvelCharacterListBinding>() {
    private val marvelViewModel: MarvelCharacterViewModel by viewModels()

    private lateinit var adapter: MarvelCharacterAdapter

    override val bindingInflater: (LayoutInflater) -> ViewBinding
            get() = FragmentMarvelCharacterListBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setUpAdapter()
            observeViewModel()
        }

    private fun setUpAdapter() {
            adapter = MarvelCharacterAdapter()
            binding.recyclerview.adapter = adapter
            adapter.onItemClickListener =
                { characterId ->
                    navigateDetailFragment(characterId)
                }
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                adapter.loadStateFlow.collectLatest { loadStates ->
                    binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    showError(loadStates.refresh is LoadState.Error)
                }

            }
        }

    private fun showError(state: Boolean) {

        binding.viewError.root.isVisible = state
        binding.viewError.tvNoConnection.text = getString(R.string.text_error)
        binding.viewError.retry.setOnClickListener {
                adapter.retry()
            }
        }


    private fun observeViewModel() {
            marvelViewModel.marvelCharacterList.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
        }

    private fun navigateDetailFragment(characterId: Int) {
            findNavController().navigate(
                MarvelCharacterListDirections.actionMarvelCharacterListToMarvelCharacterDetail(
                    characterId
                )
            )
        }
    }