package com.example.features.main.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codrum.databinding.ItemMusicBinding
import com.example.features.main.data.dto.Song

class SongAdapter(private val itemClickListener: (Song) -> Unit) :
    ListAdapter<Song, SongAdapter.ViewHolder>(musicDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemMusicBinding,
        private val itemClickListener: (Song) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                btnPlay.setOnClickListener {
                    songItem!!.run {
                        itemClickListener(this)
                    }
                }
            }
        }

        fun bind(song: Song) {
            binding.apply {
                songItem = song
                executePendingBindings()
            }
        }

    }

    companion object {
        private val musicDiffUtil = object : DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean =
                oldItem == newItem
        }
    }

}
