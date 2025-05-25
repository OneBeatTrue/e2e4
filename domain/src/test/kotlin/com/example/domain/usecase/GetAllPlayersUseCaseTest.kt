package com.example.domain.usecase

import com.example.domain.models.Player
import com.example.domain.models.SideColor
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class GetAllPlayersUseCaseTest {
    private lateinit var gameRepository: GameRepository
    private lateinit var useCase: GetAllPlayersUseCase

    @Before
    fun setUp() {
        gameRepository = mock()
        useCase = GetAllPlayersUseCase(gameRepository)
    }

    @Test
    fun `execute returns all players from repository`(): Unit = runBlocking {
        val players = listOf(
            Player("Alice", 3, 2, SideColor.White),
            Player("Bob", 1, 5, SideColor.Black)
        )
        whenever(gameRepository.getAllPlayers()).thenReturn(players)

        val result = useCase.execute()

        assertEquals(players, result)
        verify(gameRepository).getAllPlayers()
    }
}