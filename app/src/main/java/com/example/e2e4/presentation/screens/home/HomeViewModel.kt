package com.example.e2e4.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.Player
import com.example.domain.usecase.GetCurrentGameFlowUseCase
import com.example.domain.usecase.RegisterPlayerUseCase
import com.example.domain.usecase.LoginPlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loginPlayerUseCase: LoginPlayerUseCase,
    private val registerPlayerUseCase: RegisterPlayerUseCase,
    private val getCurrentGameFlowUseCase: GetCurrentGameFlowUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        viewModelScope.launch {
            getCurrentGameFlowUseCase.execute().collect {
                onIntent(HomeIntent.Update(it.player))
            }
        }
    }

    fun onIntent(intent: HomeIntent) = when (intent) {
        is HomeIntent.Login -> getPlayer(intent.name)
        is HomeIntent.Register -> createPlayer(intent.name)
        is HomeIntent.Update -> updatePlayer(intent.player)
    }

    private fun updatePlayer(player: Player) = intent {
        reduce {
            state.copy(
                player = player,
                isPlayerVisible = !player.isEmpty()
            )
        }
    }

    private fun getPlayer(name: String) = intent {
        val result = kotlin.runCatching { loginPlayerUseCase.execute(LoginPlayerParam(name)) }.onSuccess {
            if (!it) {
                postSideEffect(HomeSideEffect.ShowNotification("Пользователь не найден"))
            }
        }.onFailure {
            postSideEffect(HomeSideEffect.ShowNotification("Ошибка сети"))
        }
    }

    private fun createPlayer(name: String) = intent {
        runCatching { registerPlayerUseCase.execute(RegisterPlayerParam(name)) }.onFailure {
            postSideEffect(HomeSideEffect.ShowNotification("Ошибка сети"))
        }

    }
}
