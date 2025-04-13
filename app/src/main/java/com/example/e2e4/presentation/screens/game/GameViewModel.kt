package com.example.e2e4.presentation.screens.game

import androidx.lifecycle.ViewModel
import com.example.domain.models.CreatePlayerParam
import com.example.domain.models.GetPlayerParam
import com.example.domain.usecase.CreatePlayerUseCase
import com.example.domain.usecase.GetPlayerUseCase
import com.example.e2e4.presentation.screens.home.HomeIntent
import com.example.e2e4.presentation.screens.home.HomeSideEffect
import com.example.e2e4.presentation.screens.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
) : ContainerHost<GameState, GameSideEffect>, ViewModel() {

    override val container = container<GameState, GameSideEffect>(GameState())

    fun onIntent(intent: GameIntent) = when (intent) {
        is GameIntent.Highlight -> updateField(intent.row, intent.col)
    }

    private fun updateField(row: Int, col: Int) = intent {
        postSideEffect(GameSideEffect.ShowNotification("Выбрана ячейка ${convertToChessCoordinates(row, col)}"))
        reduce { state.copy(chosenRow = row, chosenCol = col) }
    }

    private fun convertToChessCoordinates(row: Int, col: Int): String {
        val file = 'A' + col
        val rank = 8 - row
        return "$file$rank"
    }
}