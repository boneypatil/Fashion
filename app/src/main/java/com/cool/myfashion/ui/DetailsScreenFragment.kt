package com.cool.myfashion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cool.myfashion.base.BaseDashboardFragment
import com.cool.myfashion.base.BaseViewModel
import com.cool.myfashion.databinding.MainFragmentBinding
import com.cool.myfashion.model.CarouselDataMapper
import com.cool.myfashion.model.Content
import com.cool.myfashion.model.DashboardContentResult
import com.cool.myfashion.model.ImagesResult
import com.cool.myfashion.network.ErrorResult
import com.cool.myfashion.ui.adapter.DashboardAdapter
import com.cool.myfashion.ui.adapter.DashboardImageAdapter
import com.cool.myfashion.utils.enforceSingleScrollDirection
import com.cool.myfashion.utils.show
import com.cool.myfashion.utils.toast
import com.cool.myfashion.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by rahul,p
 *
 */
class DetailsScreenFragment : BaseDashboardFragment() {

    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: MainFragmentBinding
    private val adapter by lazy {
        DashboardImageAdapter{}
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
    }
    private fun initAdapter() {
        binding.dashboardContentRV.layoutManager =
            GridLayoutManager(
                context,
                2,
                GridLayoutManager.VERTICAL,
                false
            )
        binding.dashboardContentRV.adapter = adapter
        binding.dashboardContentRV.isNestedScrollingEnabled = false
        binding.dashboardContentRV.setHasFixedSize(false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchDetailImageContent()
    }


    private fun setObservers() {
        viewModel.getDetailImageContent().observe(viewLifecycleOwner, imageContentObserver)

        viewModel.error.observe(viewLifecycleOwner, errorObserver)
        viewModel.state.observe(viewLifecycleOwner, loadingObserver)
    }

    /**
     * Errors
     */
    private val errorObserver = Observer<ErrorResult<*>> {
        handleError(it)
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

    private val imageContentObserver = Observer<ImagesResult> {
        adapter.submitList(it.images)
    }

}