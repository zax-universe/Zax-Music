package com.musify.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musify.R
import com.musify.data.local.entity.PlaylistEntity
import com.musify.databinding.ItemPlaylistBinding

class PlaylistAdapter(
    private val onClick: (PlaylistEntity) -> Unit,
    private val onMore: (PlaylistEntity) -> Unit
) : ListAdapter<PlaylistEntity, PlaylistAdapter.ViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<PlaylistEntity>() {
            override fun areItemsTheSame(a: PlaylistEntity, b: PlaylistEntity) = a.id == b.id
            override fun areContentsTheSame(a: PlaylistEntity, b: PlaylistEntity) = a == b
        }
    }

    inner class ViewHolder(val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(p: PlaylistEntity) {
            binding.tvPlaylistName.text = p.name
            val count = if (p.trackIds.isEmpty()) 0 else p.trackIds.split(",").size
            binding.tvSongsCount.text = "$count songs"
            Glide.with(binding.root).load(p.coverArtUrl)
                .placeholder(R.drawable.ic_library_music).into(binding.ivPlaylistArt)
            binding.root.setOnClickListener { onClick(p) }
            binding.btnMore.setOnClickListener { onMore(p) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}
