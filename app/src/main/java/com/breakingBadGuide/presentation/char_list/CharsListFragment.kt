package com.breakingBadGuide.presentation.char_list

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
import com.breakingBadGuide.data.models.AllCharactersItem
import com.breakingBadGuide.databinding.FragmentCharsListBinding
import com.breakingBadGuide.presentation.ScreenStateWrapper
import com.breakingBadGuide.presentation.char_list.list.CharactersListAdapter
import com.breakingBadGuide.utils.collectLatestLifecycleAware
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

        val adapter = CharactersListAdapter {
            goToCharacterDetails(it.charId)
        }

        setupRecyclerView(adapter)
        subscribeUi(adapter)
        setupListener()
        return binding.root
    }

    private fun setupListener() {
        // set OnClick Listener for reloading character if Error occurred
        binding.btnRetry.setOnClickListener {
            viewModel.getAllCharacters()
        }
    }

    private fun subscribeUi(adapter: CharactersListAdapter) {
        collectLatestLifecycleAware(viewModel.state) { state ->
            when (state) {
                is ScreenStateWrapper.Success<*> -> {
                    val characters = state.data as? List<AllCharactersItem> ?: emptyList()
                    adapter.submitList(characters)
                    showList()
                }
                is ScreenStateWrapper.Error -> {
                    showError()
                    binding.tvError.text = state.errorText
                }
                is ScreenStateWrapper.Loading -> {
                    showLoading()
                }
                else -> Unit
            }
        }
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

    private fun setupRecyclerView(adapter: CharactersListAdapter) {
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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

    private fun goToCharacterDetails(charId: Int) {
        val action = CharsListFragmentDirections.actionCharsListFragmentToCharDetailFragment(charId)
        findNavController().navigate(action)
    }
}