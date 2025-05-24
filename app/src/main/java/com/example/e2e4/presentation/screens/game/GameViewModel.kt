package com.example.e2e4.presentation.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Board
import com.example.domain.models.Cell
import com.example.domain.models.Move
import com.example.domain.models.Piece
import com.example.domain.models.PieceType
import com.example.domain.models.SideColor
import com.example.domain.usecase.GetCurrentGameFlowUseCase
import com.example.domain.usecase.MakeMoveUseCase
import com.example.domain.usecase.ResignUseCase
import com.example.domain.usecase.RetryUseCase
import com.example.e2e4.R
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
                Log.d("OBT", it.toString())
                onIntent(GameIntent.Update(it.board, it.player.side))
            }
        }
    }

    fun onIntent(intent: GameIntent) = when (intent) {
        is GameIntent.Choose -> chooseCell(intent.row, intent.col)
        is GameIntent.Resign -> resign()
        is GameIntent.Retry -> retry()
        is GameIntent.Update -> updateBoard(intent.board, intent.side)
    }

    private fun updateBoard(board: Board, side: SideColor) = intent {
        if (board.isFinished() && !state.isFinished) {
            postSideEffect(GameSideEffect.ShowNotification(if (board.mate == side) "Победа" else "Поражение"))
        }
        reduce {
            state.copy(
                isWhite = (side == SideColor.White),
                pieces = convertBoardMap(board.board, (side == SideColor.White)),
                chosenRow = -1,
                chosenCol = -1,
                possibleMoves = board.possibleMoves,
                moves = emptyMap(),
                isFinished = board.isFinished(),
            )
        }
    }

    private fun retry() = intent {
        runCatching { retryUseCase.execute() }.onFailure {
            postSideEffect(
                GameSideEffect.ShowNotification(
                    "Ошибка сети"
                )
            )
        }
    }

    private fun resign() = intent {
        runCatching { resignUseCase.execute() }.onFailure {
            postSideEffect(
                GameSideEffect.ShowNotification(
                    "Ошибка сети"
                )
            )
        }
    }

    private fun chooseCell(row: Int, col: Int) = intent {
        if (!state.isFinished) {
            if (state.chosenRow != -1 || state.chosenCol != -1) {
                val move = Move(
                    convertIntsToCell(state.chosenRow, state.chosenCol, state.isWhite),
                    convertIntsToCell(row, col, state.isWhite)
                )
                if (state.possibleMoves[move.from]?.contains(move.to) == true) {
                    runCatching {
                        makeMoveUseCase.execute(
                            move
                        )
                    }.onFailure { postSideEffect(GameSideEffect.ShowNotification("Ошибка сети")) }
                }
                reduce {
                    state.copy(
                        chosenRow = -1,
                        chosenCol = -1,
                        moves = emptyMap()
                    )
                }
            } else {
                reduce {
                    state.copy(
                        chosenRow = row,
                        chosenCol = col,
                        moves = extractMovesMap(
                            state.possibleMoves,
                            convertIntsToCell(row, col, state.isWhite),
                            state.isWhite
                        )
                    )
                }
            }
        }
    }


    private fun convertIntsToCell(row: Int, col: Int, isWhite: Boolean): Cell {
        return Cell(
            row = if (isWhite) "${8 - row}" else "${row + 1}",
            column = "${'a' + col}"
        )
    }

    private fun convertCellToInts(cell: Cell, isWhite: Boolean): Pair<Int, Int> {
        val columnIndex = cell.column.lowercase().first() - 'a'
        val rowIndex = cell.row.toIntOrNull()?.let {
            when (isWhite) {
                true -> 8 - it
                false -> it - 1
            }
        } ?: -1

        return Pair(rowIndex, columnIndex)
    }

    private fun Piece.toPic() = when (this.color) {
        SideColor.White -> when (this.type) {
            PieceType.Bishop -> R.drawable.whitebishop
            PieceType.King -> R.drawable.whiteking
            PieceType.Knight -> R.drawable.whiteknight
            PieceType.Pawn -> R.drawable.whitepawn
            PieceType.Queen -> R.drawable.whitequeen
            PieceType.Rook -> R.drawable.whiterook
        }

        SideColor.Black -> when (this.type) {
            PieceType.Bishop -> R.drawable.blackbishop
            PieceType.King -> R.drawable.blackking
            PieceType.Knight -> R.drawable.blackknight
            PieceType.Pawn -> R.drawable.blackpawn
            PieceType.Queen -> R.drawable.blackqueen
            PieceType.Rook -> R.drawable.blackrook
        }

        SideColor.None -> 0
    }

    private fun extractMovesMap(
        sourceMap: Map<Cell, Collection<Cell>>,
        keyCell: Cell,
        isWhite: Boolean
    ): Map<Int, Int> {
        val targets = sourceMap[keyCell] ?: return emptyMap()

        return targets
            .map { convertCellToInts(it, isWhite) }
            .associate { (row, col) -> row to col }
    }

    private fun convertBoardMap(
        input: Map<Cell, Piece>,
        isWhite: Boolean
    ): Map<Int, MutableMap<Int, Int>> {
        val result = mutableMapOf<Int, MutableMap<Int, Int>>()

        for ((cell, piece) in input) {
            val (row, col) = convertCellToInts(cell, isWhite)
            val rowMap = result.getOrPut(row) { mutableMapOf() }
            rowMap[col] = piece.toPic()
        }

        return result
    }

//    private fun String.toIntOrDefault(default: Int = 0): Int = this.toIntOrNull() ?: default
}