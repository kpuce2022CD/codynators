package com.example.codrum.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codrum.data.Song
import com.example.codrum.databinding.ItemMusicBinding

class MusicAdapter(private val itemClickListener: (Song) -> Unit) :
    ListAdapter<Song, MusicAdapter.ViewHolder>(musicDiffUtil) {

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
            itemView.setOnClickListener {
                binding.songItem!!.run {
                    itemClickListener(this)
                }
            }
        }

        fun bind(song: Song) {
            binding.songItem = song
            binding.executePendingBindings()
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
