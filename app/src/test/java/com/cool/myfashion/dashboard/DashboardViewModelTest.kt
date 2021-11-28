package com.cool.myfashion.dashboard

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cool.myfashion.base.BaseViewModel
import com.cool.myfashion.model.DashboardContentResult
import com.cool.myfashion.model.ImagesResult
import com.cool.myfashion.network.DashboardRepository
import com.cool.myfashion.network.ErrorResult
import com.cool.myfashion.network.Success
import com.cool.myfashion.viewmodel.DashboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito


@RunWith(JUnit4::class)
class DashboardViewModelTest {

    lateinit var viewModel: DashboardViewModel


    lateinit var repo: DashboardRepository
    lateinit var application: Application

    @ExperimentalCoroutinesApi
    private val mainDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repo = Mockito.mock(DashboardRepository::class.java)
        application = Mockito.mock(Application::class.java)

        viewModel = DashboardViewModel(
            repo = repo,
            application = application
        )
        Dispatchers.setMain(mainDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchDashBoardContent success state success`() {
        val contentResult = DashboardContentResult(listOf())
        runBlockingTest {
            Mockito.`when`(repo.getDashboardContentRepo()).thenReturn(Success(contentResult))
        }
        viewModel.fetchDashBoardContent()
        assert(viewModel.state.value == BaseViewModel.BaseState.Success)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `fetchDashBoardContent fail state error`() {
        runBlockingTest {
            Mockito.`when`(repo.getDashboardContentRepo())
                .thenReturn(ErrorResult(400, "", null))
        }
        viewModel.fetchDashBoardContent()
        assert(viewModel.state.value == BaseViewModel.BaseState.Error)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchCarouselContent success state success`() {
        val contentResult = ImagesResult(listOf())
        runBlockingTest {
            Mockito.`when`(repo.getCarouselContentRepo("brands")).thenReturn(Success(contentResult))
        }
        viewModel.fetchCarouselContent("brands",1)
        assert(viewModel.state.value == BaseViewModel.BaseState.Success)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `fetchCarouselContent fail state error`() {
        runBlockingTest {
            Mockito.`when`(repo.getCarouselContentRepo("brands"))
                .thenReturn(ErrorResult(400, "", null))
        }
        viewModel.fetchCarouselContent("brands",3)
        assert(viewModel.state.value == BaseViewModel.BaseState.Error)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `fetchDetailImageContent success state success`() {
        val contentResult = ImagesResult(listOf())
        runBlockingTest {
            Mockito.`when`(repo.getDetailImageRepo()).thenReturn(Success(contentResult))
        }
        viewModel.fetchDetailImageContent()
        assert(viewModel.state.value == BaseViewModel.BaseState.Success)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `fetchDetailImageContent fail state error`() {
        runBlockingTest {
            Mockito.`when`(repo.getDetailImageRepo())
                .thenReturn(ErrorResult(400, "", null))
        }
        viewModel.fetchDetailImageContent()
        assert(viewModel.state.value == BaseViewModel.BaseState.Error)
    }


}