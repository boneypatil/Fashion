package com.cool.myfashion.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cool.myfashion.databinding.ItemImageSegementBinding
import com.cool.myfashion.model.Images

/**
 * Created by rahul,p
 *
 */
class DashboardImageAdapter(private val imageClickedListener: (image: Images) -> Unit) :
    ListAdapter<Images, DashboardImageAdapter.ImageResultHolder>(ImageDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageResultHolder {
        return ImageResultHolder(
            ItemImageSegementBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageResultHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            imageClickedListener.invoke(item)
        }

        holder.bind(item)
    }

    inner class ImageResultHolder(private val binding: ItemImageSegementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem: Images) {
            binding.image = imageItem
        }

    }
}

class ImageDataDiffCallback : DiffUtil.ItemCallback<Images>() {
    override fun areItemsTheSame(oldItem: Images, newItem: Images): Boolean {
        return oldItem.url == newItem.url
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Images, newItem: Images): Boolean {
        return oldItem == newItem
    }
}