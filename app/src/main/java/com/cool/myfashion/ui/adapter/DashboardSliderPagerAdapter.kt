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
class DashboardSliderPagerAdapter(private var imageClickedListener: (Images) -> Unit) :
    ListAdapter<Images, DashboardSliderPagerAdapter.SliderViewHolder>(CityDiffutilCallback()) {
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

    inner class SliderViewHolder(var binding: ItemSliderCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(imageItem: Images) {
            binding.image = imageItem
            val scale: Float =  binding.cardBackground.resources.displayMetrics.density
            val width = imageItem.width
            val height = imageItem.height
            val widthPixels = (width * scale + 0.5f)
            val heightPixels = (height * scale + 0.5f)

            binding.cardBackground.layoutParams.width = widthPixels.toInt()
            binding.cardBackground.layoutParams.height = heightPixels.toInt()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SliderViewHolder(
        ItemSliderCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            imageClickedListener.invoke(item)
        }
        holder.bindView(item)

    }

}