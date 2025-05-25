package com.example.domain.usecase

import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.Player
import com.example.domain.models.SideColor
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoginPlayerUseCaseTest {
    private lateinit var gameRepository: GameRepository
    private lateinit var useCase: LoginPlayerUseCase

    @Before
    fun setUp() {
        gameRepository = mock()
        useCase = LoginPlayerUseCase(gameRepository)
    }

    @Test
    fun `execute returns true when player is not empty`() = runTest {
        val param = LoginPlayerParam("John")
        val player = Player("John", wins = 2, losses = 1, side = SideColor.White)

        whenever(gameRepository.getPlayer(param)).thenReturn(player)

        val result = useCase.execute(param)

        assertTrue(result)
        verify(gameRepository).updateCurrentPlayer(player)
    }

    @Test
    fun `execute returns false when player is empty`() = runTest {
        val param = LoginPlayerParam("Unknown")
        val emptyPlayer = Player.Empty

        whenever(gameRepository.getPlayer(param)).thenReturn(emptyPlayer)

        val result = useCase.execute(param)

        assertFalse(result)
        verify(gameRepository).updateCurrentPlayer(emptyPlayer)
    }
}