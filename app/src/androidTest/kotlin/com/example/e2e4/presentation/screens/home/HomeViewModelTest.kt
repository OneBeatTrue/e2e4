package com.example.e2e4.presentation.screens.home

import app.cash.turbine.test
import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.Player
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.usecase.GetCurrentGameFlowUseCase
import com.example.domain.usecase.LoginPlayerUseCase
import com.example.domain.usecase.RegisterPlayerUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var loginPlayerUseCase: LoginPlayerUseCase
    private lateinit var registerPlayerUseCase: RegisterPlayerUseCase
    private lateinit var getCurrentGameFlowUseCase: GetCurrentGameFlowUseCase

    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginPlayerUseCase = mockk<LoginPlayerUseCase>(relaxed = true)
        registerPlayerUseCase = mockk<RegisterPlayerUseCase>(relaxed = true)
        getCurrentGameFlowUseCase = mockk<GetCurrentGameFlowUseCase>(relaxed = true)
        every { getCurrentGameFlowUseCase.execute() } returns emptyFlow()
        viewModel = HomeViewModel(
            loginPlayerUseCase,
            registerPlayerUseCase,
            getCurrentGameFlowUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenLoginNotFound_showNotification() = runTest {
        coEvery { (loginPlayerUseCase.execute(LoginPlayerParam("vasya"))) } returns false

        viewModel.onIntent(HomeIntent.Login("vasya"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.container.sideEffectFlow.test {
            val eff = awaitItem()
            assertTrue(eff is HomeSideEffect.ShowNotification)
            assertEquals("Пользователь не найден", (eff as HomeSideEffect.ShowNotification).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenLoginThrows_showNetworkError() = runTest {
        coEvery { (loginPlayerUseCase.execute(LoginPlayerParam("vasya"))) } throws RuntimeException("Error")

        viewModel.onIntent(HomeIntent.Login("vasya"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.container.sideEffectFlow.test {
            val eff = awaitItem()
            assertTrue(eff is HomeSideEffect.ShowNotification)
            assertEquals("Ошибка сети", (eff as HomeSideEffect.ShowNotification).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenRegisterAlreadyExists_showNotification() = runTest {
        coEvery { registerPlayerUseCase.execute(RegisterPlayerParam("vasya")) } returns false

        viewModel.onIntent(HomeIntent.Register("vasya"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.container.sideEffectFlow.test {
            val eff = awaitItem()
            assertTrue(eff is HomeSideEffect.ShowNotification)
            assertEquals(
                "Пользователь с таким именем уже существует",
                (eff as HomeSideEffect.ShowNotification).message
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun homeIntentUpdateShouldUpdate_stateAndVisibility() = runTest {
        val p = Player.Empty
        viewModel.onIntent(HomeIntent.Update(p))
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.container.stateFlow.value
        assertEquals(p, state.player)
        assertEquals(!p.isEmpty(), state.isPlayerVisible)
    }
}