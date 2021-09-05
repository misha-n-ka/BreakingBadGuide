package com.breakingBadGuide.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.breakingBadGuide.R
import com.breakingBadGuide.data.models.AllCharactersItem
import com.breakingBadGuide.databinding.ItemCharBinding
import com.breakingBadGuide.ui.fragments.CharsListFragmentDirections
import com.bumptech.glide.Glide

class CharactersListAdapter(private val allAllCharacters: List<AllCharactersItem>) :
    RecyclerView.Adapter<CharactersListAdapter.ItemViewHolder>() {

    private lateinit var navController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharBinding.inflate(inflater, parent, false)
        navController = parent.findNavController()
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = allAllCharacters.size

    inner class ItemViewHolder(private val binding: ItemCharBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        // position of clicked item
        private var _pos = -1

        fun bind(position: Int) {
            //setting item position to local field for passing id in OnClick(view)
            _pos = position
            //bind character name
            binding.textView.text = allAllCharacters[position].name
            // get imageUrl
            val imageUrl = allAllCharacters[position].img
            // load character avatar
            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .error(R.drawable.ic_char_avatar)
                .circleCrop()
                .into(binding.imageView)
        }

        override fun onClick(v: View?) {
            // retrieving character ID of current position
            val id = allAllCharacters[_pos].charId
            // putting character ID to action
            val action = CharsListFragmentDirections.actionCharsListFragmentToCharDetailFragment(id)
            //navigate to CharDetailFragment
            navController.navigate(action)
        }
    }
}