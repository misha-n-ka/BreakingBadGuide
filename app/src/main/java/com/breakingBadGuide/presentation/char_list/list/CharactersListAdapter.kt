package com.breakingBadGuide.presentation.char_list.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.breakingBadGuide.data.models.AllCharactersItem

class CharactersListAdapter(
    private val onCharacterClick: (AllCharactersItem) -> Unit
) : ListAdapter<AllCharactersItem, CharacterListViewHolder>(AllCharsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        return CharacterListViewHolder.create(parent, onCharacterClick)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object AllCharsDiffCallback: DiffUtil.ItemCallback<AllCharactersItem>() {
        override fun areItemsTheSame(
            oldItem: AllCharactersItem,
            newItem: AllCharactersItem
        ): Boolean {
            return oldItem.charId == newItem.charId
        }

        override fun areContentsTheSame(
            oldItem: AllCharactersItem,
            newItem: AllCharactersItem
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.img == newItem.img
        }
    }
}