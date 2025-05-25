package com.example.domain.usecase

import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.Player
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.SideColor
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RegisterPlayerUseCaseTest {

    private lateinit var gameRepository: GameRepository
    private lateinit var useCase: RegisterPlayerUseCase

    @Before
    fun setUp() {
        gameRepository = mock()
        useCase = RegisterPlayerUseCase(gameRepository)
    }

    @Test
    fun `execute returns false when player already exists`() = runTest {
        val param = RegisterPlayerParam("testuser")
        val loginParam = LoginPlayerParam(param.name)
        val player = Player("testuser", 1, 0, SideColor.Black)

        whenever(gameRepository.getPlayer(loginParam)).thenReturn(player)

        val result = useCase.execute(param)

        assertFalse(result)
        verify(gameRepository, never()).createPlayer(any())
        verify(gameRepository, never()).updateCurrentPlayer(any())
    }

    @Test
    fun `execute registers and updates player when not exists`() = runTest {
        val param = RegisterPlayerParam("newbie")
        val loginParam = LoginPlayerParam(param.name)
        val emptyPlayer = Player.Empty
        val createdPlayer = Player("newbie", 0, 0, SideColor.White)

        whenever(gameRepository.getPlayer(loginParam)).thenReturn(emptyPlayer, createdPlayer)
        whenever(gameRepository.createPlayer(param)).thenReturn(createdPlayer)

        val result = useCase.execute(param)

        assertTrue(result)
        verify(gameRepository).createPlayer(param)
        verify(gameRepository).updateCurrentPlayer(createdPlayer)
    }
}
