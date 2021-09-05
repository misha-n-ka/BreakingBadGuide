package com.breakingBadGuide.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.breakingBadGuide.R
import com.breakingBadGuide.data.models.CharacterByIdItem
import com.breakingBadGuide.databinding.FragmentCharDetailsBinding
import com.breakingBadGuide.utils.CustomDateFormat
import com.breakingBadGuide.utils.StateWrapper
import com.breakingBadGuide.viewModels.DetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CharDetailFragment : Fragment(R.layout.fragment_char_details) {

    private lateinit var binding: FragmentCharDetailsBinding
    private val args: CharDetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharDetailsBinding.inflate(inflater, container, false)

        // retrieve charId from arguments passed from CharsListFragment
        val currentCharId = args.charId
        // getting a character by retrieved ID
        viewModel.getCharacterById(currentCharId)

        // set OnClick Listener for reloading character if Error occurred
        binding.btnRetry.setOnClickListener {
            viewModel.getCharacterById(currentCharId)
        }

        // collecting viewModel.state for observes fragment state
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is StateWrapper.Success -> {
                        //retrieve CharacterById from Success(data)
                        val character = state.data as CharacterByIdItem
                        showDetailsLayout()
                        setupCollapsingToolbarWithNavController()
                        bindDetails(character)
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

    private fun setupCollapsingToolbarWithNavController() {
        val layout = binding.collapsingAppBarLayout
        val toolbar = binding.toolbar
        // settings status bar color to transparent
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        // tie toolbar with navController
        layout.setupWithNavController(toolbar, navController, appBarConfiguration)
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            errorLayout.isVisible = false
            coordinatorLayout.isVisible = false
        }
    }

    private fun showDetailsLayout() {
        binding.apply {
            progressBar.isVisible = false
            errorLayout.isVisible = false
            coordinatorLayout.isVisible = true
        }
    }

    private fun showError() {
        binding.apply {
            progressBar.isVisible = false
            errorLayout.isVisible = true
            coordinatorLayout.isVisible = false
        }
    }

    private fun bindDetails(character: CharacterByIdItem) {
        binding.apply {
            // loading character photo to collapsing toolbar
            Glide.with(collapseCharAvatar.context)
                .load(character.img)
                .error(R.drawable.ic_char_avatar)
                .centerCrop()
                .into(collapseCharAvatar)

            // set character's name as toolbar title
            collapsingAppBarLayout.title = character.name

            // binding Character CardView
            tvNickname.text = character.nickname
            tvBirthday.text = setBirthdayDate(character.birthday, "MM-dd-yyyy")
            tvStatus.text = character.status

            // binding Occupation CardView
            tvOccupation.text = setOccupation(character.occupation)

            // binding Appearance CardView
            // show Breaking Bad layout if appeared
            llBBAppearance.isVisible = character.appearance.isNotEmpty()
            // show seasons where appeared
            tvAppearanceBBSeasons.text = setAppearance(character.appearance)
            // show Better Call Saul layout if appeared
            llBCSAppearance.isVisible = character.betterCallSaulAppearance.isNotEmpty()
            // show seasons where appeared
            tvAppearanceBCSSeasons.text = setAppearance(character.betterCallSaulAppearance)

            // binding Actor CardView
            tvActor.text = character.portrayed
            //loading character photo to Actor photo
            Glide.with(ivActorPhoto.context)
                .load(character.img)
                .centerInside()
                .circleCrop()
                .error(R.drawable.ic_char_avatar)
                .into(ivActorPhoto)
        }
    }

    // formatting Birthday Date
    private fun setBirthdayDate(fromDate: String, pattern: String): String {
        if (fromDate == "Unknown") return "-"
        val date = CustomDateFormat.parseDateTime(fromDate, pattern)
        return CustomDateFormat.formatDateTime(date)
    }

    // joining occupation Array to String
    private fun setOccupation(occupation: List<String>): String {
        return occupation.joinToString(",\n")
    }

    // joining appearance Array to String
    private fun setAppearance(appearance: List<Int>): String {
        return appearance.joinToString(",")
    }
}