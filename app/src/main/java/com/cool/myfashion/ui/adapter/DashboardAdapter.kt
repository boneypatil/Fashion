package com.cool.myfashion.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.cool.myfashion.R
import com.cool.myfashion.databinding.ItemCarouselWidgetBinding
import com.cool.myfashion.databinding.ItemImageWidgetBinding
import com.cool.myfashion.databinding.ItemSliderWidgetBinding
import com.cool.myfashion.model.Content
import com.cool.myfashion.model.Type
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * Created by rahul,p
 *
 */
class DashboardAdapter :
    ListAdapter<Content, DashboardContentViewHolder>(ProgressDataDiffCallback()) {

    var dataSize = 0
    override fun submitList(list: MutableList<Content>?) {
        super.submitList(list)
        dataSize = list?.size ?: 0
    }


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
                imageHolder.bind(item)
            }

            R.layout.item_slider_widget -> {
                val sliderHolder = holder as DashboardContentSliderWidget
                sliderHolder.bind(
                    item
                )
            }
            R.layout.item_carousel_widget -> {
                val carouseHolder = holder as DashboardContentCarouselWidget
                carouseHolder.bind(
                    item
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

    override fun getItemCount(): Int {
        return itemCount
    }
}

abstract class DashboardContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class DashboardContentImagesWidget(private val binding: ItemImageWidgetBinding) :
    DashboardContentViewHolder(binding.root) {
    fun bind(content: Content) {
        initAdapter(content)
    }
    private fun initAdapter(content: Content) {

        val layoutManager =
            FlexboxLayoutManager(binding.dashboardImageContentRV.context)
        layoutManager.flexWrap = FlexWrap.NOWRAP

     val   mAdapter = DashboardImageAdapter()
        binding.dashboardImageContentRV.layoutManager = layoutManager
        binding.dashboardImageContentRV.isNestedScrollingEnabled = false
        binding.dashboardImageContentRV.adapter = mAdapter
        mAdapter.submitList(content.images)
    }

}

class DashboardContentSliderWidget(private val binding: ItemSliderWidgetBinding) :
    DashboardContentViewHolder(binding.root) {
    fun bind(content: Content) {

        initSliderAdapter(content)

    }

  private fun   initSliderAdapter(content: Content) {
      val helper: SnapHelper = LinearSnapHelper()
      helper.attachToRecyclerView(binding.dashboardSliderContentRV)
      val adapter = DashboardSliderPagerAdapter({

      })
      binding.dashboardSliderContentRV.adapter = adapter
      binding.dashboardSliderContentRV.layoutManager =
          LinearLayoutManager(binding.dashboardSliderContentRV.context, LinearLayoutManager.HORIZONTAL, false)
      binding.indicator.attachToRecyclerView(binding.dashboardSliderContentRV)
      adapter.submitList(content.images)
    }
}

class DashboardContentCarouselWidget(private val binding: ItemCarouselWidgetBinding) :
    DashboardContentViewHolder(binding.root) {
    fun bind(content: Content) {


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