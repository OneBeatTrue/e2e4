package com.example.e2e4.presentation.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Board
import com.example.domain.models.Move
import com.example.domain.usecase.GetCurrentGameFlowUseCase
import com.example.domain.usecase.MakeMoveUseCase
import com.example.domain.usecase.ResignUseCase
import com.example.domain.usecase.RetryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val resignUseCase: ResignUseCase,
    private val retryUseCase: RetryUseCase,
    private val makeMoveUseCase: MakeMoveUseCase,
    private val getCurrentGameFlowUseCase: GetCurrentGameFlowUseCase
) : ContainerHost<GameState, GameSideEffect>, ViewModel() {

    override val container = container<GameState, GameSideEffect>(GameState())

    init {
        viewModelScope.launch {
            getCurrentGameFlowUseCase.execute().collect {
                onIntent(GameIntent.Update(it.board))
            }
        }
    }

    fun onIntent(intent: GameIntent) = when (intent) {
        is GameIntent.Choose -> chooseCell(intent.row, intent.col)
        is GameIntent.Resign -> resign()
        is GameIntent.Retry -> retry()
        is GameIntent.Update -> updateBoard(intent.board)
    }

    private fun updateBoard(board: Board) = intent {
        if (!board.isFinished()) {
            reduce {
                state.copy(
                    board = board,
                )
            }
        }
        else {
            if (!state.board.isFinished()) {
                postSideEffect(GameSideEffect.ShowNotification(if (board.isWin()) "Победа" else "Поражение"))
            }
            reduce {
                state.copy(
                    board = board,
                    chosenRow = -1,
                    chosenCol = -1
                )
            }
        }
    }

    private fun retry() = intent {
        retryUseCase.execute()
    }

    private fun resign() = intent {
        resignUseCase.execute()
    }

    private fun chooseCell(row: Int, col: Int) = intent {
        if (!state.board.isFinished()) {
//            postSideEffect(GameSideEffect.ShowNotification("Выбрана ячейка ${convertToChessCoordinates(row, col)}"))
            if (state.chosenRow != -1 && state.chosenCol != -1) {
                val move = Move(
                    fromRow = state.chosenRow,
                    fromColumn = state.chosenCol,
                    toRow = row,
                    toColumn = col
                )
                if (checkMove(move)) makeMoveUseCase.execute(move)
                reduce { state.copy(chosenRow = -1, chosenCol = -1) }
            }
            else {
                reduce { state.copy(chosenRow = row, chosenCol = col) }
            }
        }
    }

    private fun checkMove(move: Move) : Boolean {
        return true // TODO
    }
}