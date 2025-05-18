package com.example.e2e4.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.Player
import com.example.domain.usecase.GetCurrentPlayerFlowUseCase
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
    private val getCurrentPlayerFlowUseCase: GetCurrentPlayerFlowUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        viewModelScope.launch {
            getCurrentPlayerFlowUseCase.execute().collect {
                onIntent(HomeIntent.Update(it))
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
                player = player
            )
        }
    }

    private fun getPlayer(name: String) = intent {
        val result = loginPlayerUseCase.execute(LoginPlayerParam(name))
        if (!result) {
            postSideEffect(HomeSideEffect.ShowNotification("Пользователь не найден"))
            reduce { state.copy(isPlayerVisible = false) }
        } else {
            reduce {
                state.copy(isPlayerVisible = true)
            }
        }
    }

    private fun createPlayer(name: String) = intent {
        registerPlayerUseCase.execute(RegisterPlayerParam(name))
        reduce {
            state.copy(isPlayerVisible = true)
        }
    }
}
