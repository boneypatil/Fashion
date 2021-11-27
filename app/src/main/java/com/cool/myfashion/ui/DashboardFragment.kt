package com.cool.myfashion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cool.myfashion.base.BaseDashboardFragment
import com.cool.myfashion.base.BaseViewModel
import com.cool.myfashion.databinding.MainFragmentBinding
import com.cool.myfashion.model.CarouselDataMapper
import com.cool.myfashion.model.Content
import com.cool.myfashion.model.DashboardContentResult
import com.cool.myfashion.network.ErrorResult
import com.cool.myfashion.ui.adapter.DashboardAdapter
import com.cool.myfashion.utils.enforceSingleScrollDirection
import com.cool.myfashion.utils.show
import com.cool.myfashion.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by rahul,p
 *
 */
class DashboardFragment : BaseDashboardFragment() {

    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: MainFragmentBinding
    private var dashboardData = mutableListOf<Content>()
    private val adapter by lazy {
        DashboardAdapter({ url, pos ->
            viewModel.fetchCarouselContent(url, pos)
        }, {
            onImageSelected(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setObservers()


        viewModel.fetchDashBoardContent()
    }

    private fun initAdapter() {
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        manager.initialPrefetchItemCount = 4
        binding.dashboardContentRV.layoutManager = manager
        binding.dashboardContentRV.adapter = this.adapter
        binding.dashboardContentRV.enforceSingleScrollDirection()
    }


    private fun setObservers() {
        viewModel.getDashboardContent().observe(viewLifecycleOwner, dashboardContentObserver)
        viewModel.getCarouselContent().observe(viewLifecycleOwner, carouselContentObserver)

        viewModel.error.observe(viewLifecycleOwner, errorObserver)
        viewModel.state.observe(viewLifecycleOwner, loadingObserver)
    }

    /**
     * Errors
     */
    private val errorObserver = Observer<ErrorResult<*>> {
        handleErrorInActivity(it)
    }

    private fun handleErrorInActivity(errorResult: ErrorResult<*>) {
        handleError(errorResult)
    }

    private val loadingObserver = Observer<BaseViewModel.BaseState> { state ->
        when (state) {
            BaseViewModel.BaseState.Loading -> showLoader()
            BaseViewModel.BaseState.Success,
            BaseViewModel.BaseState.Error -> showLoader(false)
        }
    }

    private fun showLoader(show: Boolean = true) {
        binding.dashboardLoader show show
    }

    private val dashboardContentObserver = Observer<DashboardContentResult> {
        dashboardData = it.content.toMutableList()
        adapter.submitList(dashboardData)
    }

    private val carouselContentObserver = Observer<CarouselDataMapper> {
        val content = dashboardData[it.pos]
        content.images = it.images
        dashboardData[it.pos] = content
        adapter.notifyItemChanged(it.pos, content)
    }

}