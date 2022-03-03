package com.breakingBadGuide.presentation.char_list.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.breakingBadGuide.R
import com.breakingBadGuide.data.models.AllCharactersItem
import com.breakingBadGuide.databinding.ItemCharBinding
import com.bumptech.glide.Glide

class CharacterListViewHolder(
    private val binding: ItemCharBinding,
    private val onCharacterClick: (AllCharactersItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var listCharItem: AllCharactersItem? = null

    init {
        itemView.setOnClickListener {
            listCharItem?.let { character ->
                onCharacterClick(character)
            }
        }
    }

    fun bind(character: AllCharactersItem) {
        listCharItem = character
        binding.apply {
            tvName.text = character.name

            val imageUrl = character.img
            // load character avatar
            Glide.with(ivAvatar.context)
                .load(imageUrl)
                .error(R.drawable.ic_char_avatar)
                .circleCrop()
                .into(ivAvatar)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onCharacterClick: (AllCharactersItem) -> Unit
        ): CharacterListViewHolder {
            val binding =
                ItemCharBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CharacterListViewHolder(
                binding,
                onCharacterClick
            )
        }
    }
}