package com.cool.myfashion.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.cool.myfashion.R
import com.cool.myfashion.databinding.ItemCarouselWidgetBinding
import com.cool.myfashion.databinding.ItemImageWidgetBinding
import com.cool.myfashion.databinding.ItemSliderWidgetBinding
import com.cool.myfashion.model.Content
import com.cool.myfashion.model.Images
import com.cool.myfashion.model.Type
import com.cool.myfashion.utils.enforceSingleScrollDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager


/**
 * Created by rahul,p
 *
 */
class DashboardAdapter(
    private val listener: (url: String, pos: Int) -> Unit,
    private val imageClickedListener: (image: Images) -> Unit
) :
    ListAdapter<Content, DashboardContentViewHolder>(ProgressDataDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardContentViewHolder {
        return when (viewType) {
            R.layout.item_image_widget -> DashboardContentImagesWidget(
                ItemImageWidgetBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            R.layout.item_slider_widget -> DashboardContentSliderWidget(
                ItemSliderWidgetBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            R.layout.item_carousel_widget -> DashboardContentCarouselWidget(
                ItemCarouselWidgetBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> DashboardContentCarouselWidget(
                ItemCarouselWidgetBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: DashboardContentViewHolder, position: Int) {
        val item = getItem(position)

        when (holder.itemViewType) {
            R.layout.item_image_widget -> {
                val imageHolder = holder as DashboardContentImagesWidget
                imageHolder.bind(item, imageClickedListener)
            }

            R.layout.item_slider_widget -> {
                val sliderHolder = holder as DashboardContentSliderWidget
                sliderHolder.bind(
                    item, imageClickedListener
                )
            }
            R.layout.item_carousel_widget -> {
                val carouseHolder = holder as DashboardContentCarouselWidget
                carouseHolder.bind(
                    item, listener, position, imageClickedListener
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val contentType = getItem(position)
        return when (contentType.type) {
            Type.Image -> R.layout.item_image_widget
            Type.Carousel -> R.layout.item_carousel_widget
            Type.Slider -> R.layout.item_slider_widget
        }
    }


}

abstract class DashboardContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class DashboardContentImagesWidget(private val binding: ItemImageWidgetBinding) :
    DashboardContentViewHolder(binding.root) {
    fun bind(
        content: Content,
        imageClickedListener: (image: Images) -> Unit
    ) {
        initAdapter(content, imageClickedListener)
    }

    private fun initAdapter(
        content: Content,
        imageClickedListener: (image: Images) -> Unit) {

        val layoutManager =
            FlexboxLayoutManager(binding.dashboardImageContentRV.context)
        layoutManager.flexWrap = FlexWrap.NOWRAP

        val mAdapter = DashboardImageAdapter(imageClickedListener)
        binding.dashboardImageContentRV.layoutManager = layoutManager
        binding.dashboardImageContentRV.isNestedScrollingEnabled = false
        mAdapter.setHasStableIds(true)
        binding.dashboardImageContentRV.adapter = mAdapter
        binding.dashboardImageContentRV.enforceSingleScrollDirection()
        mAdapter.submitList(content.images)
    }

}

class DashboardContentSliderWidget(private val binding: ItemSliderWidgetBinding) :
    DashboardContentViewHolder(binding.root) {
    fun bind(
        content: Content,
        imageClickedListener: (image: Images) -> Unit) {
        initSliderAdapter(content, imageClickedListener)
    }

    private fun initSliderAdapter(
        content: Content,
        imageClickedListener: (image: Images) -> Unit) {
        if (binding.dashboardSliderContentRV.onFlingListener == null) {
            val helper = LinearSnapHelper()
            helper.attachToRecyclerView(binding.dashboardSliderContentRV)

        }

        val adapter = DashboardSliderPagerAdapter(imageClickedListener)
        adapter.setHasStableIds(true)
        binding.dashboardSliderContentRV.adapter = adapter
        val manager = LinearLayoutManager(
            binding.dashboardSliderContentRV.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        manager.initialPrefetchItemCount = 4
        binding.dashboardSliderContentRV.layoutManager = manager
        binding.indicator.attachToRecyclerView(binding.dashboardSliderContentRV)
        binding.dashboardSliderContentRV.enforceSingleScrollDirection()

        adapter.submitList(content.images)
    }
}

class DashboardContentCarouselWidget(private val binding: ItemCarouselWidgetBinding) :
    DashboardContentViewHolder(binding.root) {
    fun bind(
        content: Content,
        listener: (url: String, pos: Int) -> Unit,
        position: Int,
        imageClickedListener: (image: Images) -> Unit) {
        if (content.images.isNullOrEmpty()) {
            val url = content.url
            val urlPath = url.split("/").last()

            listener.invoke(urlPath, position)
        } else {

            binding.dashboardCarouselTitle.text = content.title
            val adapter = DashboardImageAdapter(imageClickedListener)
            adapter.setHasStableIds(true)
            binding.dashboardCarouselContentRV.adapter = adapter

            val manager = LinearLayoutManager(
                binding.dashboardCarouselContentRV.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            manager.initialPrefetchItemCount = 4
            binding.dashboardCarouselContentRV.layoutManager = manager
            binding.dashboardCarouselContentRV.enforceSingleScrollDirection()

            adapter.submitList(content.images)

        }
    }
}

class ProgressDataDiffCallback : DiffUtil.ItemCallback<Content>() {
    override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem.type == newItem.type
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem == newItem
    }
}