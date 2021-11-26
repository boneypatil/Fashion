package com.cool.myfashion.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cool.myfashion.databinding.ItemSliderCardBinding
import com.cool.myfashion.model.Images

/**
 * Created by rahul,p
 *
 */
class DashboardSliderPagerAdapter(var listener: (Images) -> Unit) :
    ListAdapter<Images, DashboardSliderPagerAdapter.CityCardsViewHolder>(CityDiffutilCallback()) {
    class CityDiffutilCallback : DiffUtil.ItemCallback<Images>() {
        override fun areItemsTheSame(oldItem: Images, newItem: Images) =
            oldItem.url == newItem.url

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Images,
            newItem: Images
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    inner class CityCardsViewHolder(var binding: ItemSliderCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(imageItem: Images) {
            binding.image = imageItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CityCardsViewHolder(
        ItemSliderCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CityCardsViewHolder, position: Int) =
        holder.bindView(getItem(position))
}