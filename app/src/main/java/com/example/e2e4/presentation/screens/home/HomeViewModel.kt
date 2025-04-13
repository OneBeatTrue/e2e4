package com.example.e2e4.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.domain.models.CreatePlayerParam
import com.example.domain.models.GetPlayerParam
import com.example.domain.usecase.CreatePlayerUseCase
import com.example.domain.usecase.GetPlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlayerUseCase: GetPlayerUseCase,
    private val createPlayerUseCase: CreatePlayerUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    fun onIntent(intent: HomeIntent) = when (intent) {
        is HomeIntent.Login -> getPlayer(intent.name)
        is HomeIntent.Register -> createPlayer(intent.name)
    }

    private fun getPlayer(name: String) = intent {
        val player = getPlayerUseCase.execute(GetPlayerParam(name))
        if (player == null) {
            postSideEffect(HomeSideEffect.ShowNotification("Пользователь не найден"))
            reduce { state.copy(visible = false) }
        }
        else {
            reduce {
                state.copy(
                    name = player.name,
                    wins = player.wins,
                    losses = player.losses,
                    visible = true
                )
            }
        }
    }

    private fun createPlayer(name: String) = intent {
        val player = createPlayerUseCase.execute(CreatePlayerParam(name))
        reduce {
            state.copy(
                name = player.name,
                wins = player.wins,
                losses = player.losses,
                visible = true
            )
        }
    }
}
