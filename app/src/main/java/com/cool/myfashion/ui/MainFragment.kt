package com.cool.myfashion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cool.myfashion.databinding.MainFragmentBinding
import com.cool.myfashion.ui.adapter.DashboardAdapter
import com.cool.myfashion.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
/**
 * Created by rahul,p
 *
 */
class MainFragment : Fragment() {

    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: MainFragmentBinding
    private val adapter by lazy {
        DashboardAdapter()
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

    }

    private fun initAdapter() {
        binding.dashboardContentRV.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.dashboardContentRV.adapter = this.adapter

    }

}