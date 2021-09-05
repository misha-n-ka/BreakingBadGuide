package com.breakingBadGuide.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.breakingBadGuide.R
import com.breakingBadGuide.data.responses.AllCharactersResponse
import com.breakingBadGuide.databinding.FragmentCharsListBinding
import com.breakingBadGuide.utils.CharactersListAdapter
import com.breakingBadGuide.utils.StateWrapper
import com.breakingBadGuide.viewModels.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CharsListFragment : Fragment(R.layout.fragment_chars_list) {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding: FragmentCharsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharsListBinding.inflate(inflater, container, false)
        // tie toolbar with navController
        setupToolbarWithNavController()
        // getting all characters array
        viewModel.getAllCharacters()

        // set OnClick Listener for reloading character if Error occurred
        binding.btnRetry.setOnClickListener {
            viewModel.getAllCharacters()
        }

        // collecting viewModel.state for observes fragment state
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is StateWrapper.Success -> {
                        showList()
                        setupRecyclerView(state)
                    }
                    is StateWrapper.Fail -> {
                        showError()
                        binding.tvError.text = state.errorText
                    }
                    is StateWrapper.Loading -> {
                        showLoading()
                    }
                    else -> Unit
                }
            }
        }
        return binding.root
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            errorLayout.isVisible = false
            recyclerView.isVisible = false
        }
    }

    private fun showList() {
        binding.apply {
            progressBar.isVisible = false
            errorLayout.isVisible = false
            recyclerView.isVisible = true
        }
    }

    private fun showError() {
        binding.apply {
            progressBar.isVisible = false
            errorLayout.isVisible = true
            recyclerView.isVisible = false
        }
    }

    // settings up recycler view if state of fragment is Succeed
    private fun setupRecyclerView(state: StateWrapper.Success) {
        // retrieve characters list from Success(data)
        val charactersList = state.data as List<*>
        val adapter = CharactersListAdapter(charactersList as AllCharactersResponse)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupToolbarWithNavController() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.apply {

            // set Status Bar color to green_dark
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.green_dark)

            setupWithNavController(navController, appBarConfiguration)
        }
    }

}