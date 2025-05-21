package com.example.e2e4.presentation.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Board
import com.example.domain.models.Cell
import com.example.domain.models.Move
import com.example.domain.models.Piece
import com.example.domain.models.SideColor
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
    private var possibleMoves : Map<Cell, Collection<Cell>> = emptyMap()
    private var isWhite: Boolean = true

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
                possibleMoves = board.possibleMoves
                isWhite = (board.color == SideColor.White)
                state.copy(
                    pieces = convertBoardMap(board.board),
                    chosenRow = -1,
                    chosenCol = -1,
                    moves = mapOf(),
                    isFinished = board.isFinished()
                )
            }
        } else {
            if (!state.isFinished) {
                postSideEffect(GameSideEffect.ShowNotification(if (board.isWin()) "Победа" else "Поражение"))
            }
            reduce {
                state.copy(
                    pieces = convertBoardMap(board.board),
                    chosenRow = -1,
                    chosenCol = -1,
                    moves = emptyMap(),
                    isFinished = board.isFinished()
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
        if (!state.isFinished) {
            if (state.chosenRow != -1 && state.chosenCol != -1) {
                val move = Move(
                    convertIntsToCell(state.chosenRow, state.chosenCol),
                    convertIntsToCell(row, col)
                )
                if (possibleMoves[move.from]?.contains(move.to) == true) makeMoveUseCase.execute(
                    move
                )
                reduce { state.copy(chosenRow = -1, chosenCol = -1, moves = mapOf()) }
            } else {
                reduce {
                    state.copy(
                        chosenRow = row,
                        chosenCol = col,
                        moves = extractMovesMap(
                            possibleMoves,
                            convertIntsToCell(row, col),
                        )
                    )
                }
            }
        }
    }


    private fun convertIntsToCell(row: Int, col: Int): Cell {
        return Cell(
            row = if (isWhite) "${8 - row}" else "${row + 1}",
            column = "${'a' + col}"
        )
    }

    private fun convertCellToInts(cell: Cell): Pair<Int, Int> {
        val columnIndex = cell.column.lowercase().first() - 'a'
        val rowIndex = cell.row.toIntOrNull()?.let {
            when (isWhite) {
                true -> 8 - it
                false -> it - 1
            }
        } ?: -1

        return Pair(rowIndex, columnIndex)
    }

    private fun convertPieceToPic(piece: Piece): String = "${piece.color.toString()}${piece.type.toString()}"
    private fun extractMovesMap(
        sourceMap: Map<Cell, Collection<Cell>>,
        keyCell: Cell,
    ): Map<Int, Int> {
        val targets = sourceMap[keyCell] ?: return emptyMap()

        return targets
            .map { convertCellToInts(it) }
            .associate { (row, col) -> row to col }
    }

    fun convertBoardMap(
        input: Map<Cell, Piece>
    ): Map<Int, MutableMap<Int, String>> {
        val result = mutableMapOf<Int, MutableMap<Int, String>>()

        for ((cell, piece) in input) {
            val (row, col) = convertCellToInts(cell)
            val pic = convertPieceToPic(piece)

            val rowMap = result.getOrPut(row) { mutableMapOf() }
            rowMap[col] = pic
        }

        return result
    }

//    private fun String.toIntOrDefault(default: Int = 0): Int = this.toIntOrNull() ?: default
}