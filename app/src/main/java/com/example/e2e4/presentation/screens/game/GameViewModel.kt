package com.example.e2e4.presentation.screens.game

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.RegisterPlayerUseCase
import com.example.domain.usecase.ResignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val resignUseCase: ResignUseCase,
) : ContainerHost<GameState, GameSideEffect>, ViewModel() {
    init {

    }
    override val container = container<GameState, GameSideEffect>(GameState())

    fun onIntent(intent: GameIntent) = when (intent) {
        is GameIntent.Highlight -> updateField(intent.row, intent.col)
        is GameIntent.Resign -> resign()
        is GameIntent.Retry -> retry()
    }

    private fun retry() = intent {
        reduce { state.copy(isFinished = false, chosenRow = -1, chosenCol = -1) }
    }

    private fun resign() = intent {
        postSideEffect(GameSideEffect.ShowNotification("Поражение"))
        resignUseCase.execute()
        reduce { state.copy(isFinished = true, chosenRow = -1, chosenCol = -1) }
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