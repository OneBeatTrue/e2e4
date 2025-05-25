package com.example.e2e4.presentation.screens.leaderboard

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetAllPlayersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val getAllPlayersUseCase: GetAllPlayersUseCase
) : ContainerHost<LeaderboardState, LeaderboardSideEffect>, ViewModel() {

    override val container = container<LeaderboardState, LeaderboardSideEffect>(LeaderboardState())

    fun onCreate() = fetchAllPlayers()

    private fun fetchAllPlayers() = intent {
        val players = runCatching { getAllPlayersUseCase.execute() }.onSuccess {
            reduce {
                state.copy(
                    players = it.sortedWith(compareBy({-it.wins}, {it.losses}, {it.name}))
                )
            }
        }.onFailure {  }
    }
}