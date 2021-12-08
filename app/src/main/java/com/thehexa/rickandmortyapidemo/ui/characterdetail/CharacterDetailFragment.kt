package com.thehexa.rickandmortyapidemo.ui.characterdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.thehexa.rickandmortyapidemo.model.Character
import com.thehexa.rickandmortyapidemo.databinding.CharacterDetailFragmentBinding
import com.thehexa.rickandmortyapidemo.utils.Resource

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.thehexa.rickandmortyapidemo.R
import com.thehexa.rickandmortyapidemo.model.Location
import com.thehexa.rickandmortyapidemo.viewmodel.CharacterDetailViewModel
import com.thehexa.rickandmortyapidemo.viewmodel.LocationDetailViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: CharacterDetailFragmentBinding
    private val viewModel: CharacterDetailViewModel by viewModels()
    private val locationViewModel: LocationDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        arguments?.getInt("loc_id")?.let { locationViewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.character.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindCharacter(it.data!!)
                }

                Resource.Status.ERROR ->{
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    it.message?.let { it1 -> Log.e("Network req failed1: ", it1) }
                }

                Resource.Status.LOADING -> {
                    binding.name.text = getString(R.string.loading)
                }
            }
        })

        locationViewModel.location.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindLocationDetails(it.data!!)
                }

                Resource.Status.ERROR ->{
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    it.message?.let { it1 -> Log.e("Network req failed: ", it1) };
                }

                Resource.Status.LOADING -> {
                    binding.locationName.text = getString(R.string.loading)
                    binding.locationType.text = getString(R.string.loading)
                    binding.locationDimension.text = getString(R.string.loading)
                    binding.locationResidents.text = getString(R.string.loading)
                }
            }
        })
    }

    private fun bindCharacter(character: Character) {
        binding.name.text = character.name
        binding.locationInfo.text = getString(R.string.locationInfo)
        Glide.with(binding.root)
            .load(character.image)
            .transform(CircleCrop())
            .into(binding.image)
    }

    private fun bindLocationDetails(location: Location){
        binding.locationName.text = location.name
        binding.locationType.text = location.type
        binding.locationDimension.text = location.dimension
        binding.locationResidents.text = location.residents.size.toString()
    }
}
