package com.example.data.mapper

import com.example.data.local.entities.UserLocalEntity
import com.example.domain.models.*
import org.junit.Assert.assertEquals
import org.junit.Test
import com.github.bhlangonijr.chesslib.Piece as LibPiece

class DataToDomainMapperTest {
    @Test
    fun `toInt extension method for SideColor work properly`() {
        assertEquals(1, SideColor.White.toInt())
        assertEquals(-1, SideColor.Black.toInt())
        assertEquals(0, SideColor.None.toInt())
    }

    @Test
    fun `toSideColor extension method for Int work properly`() {
        assertEquals(SideColor.White, 1.toSideColor())
        assertEquals(SideColor.Black, (-1).toSideColor())
        assertEquals(SideColor.None, 0.toSideColor())
    }

    @Test
    fun `toBoolean extension method for SideColor work properly`() {
        assertEquals(true, SideColor.White.toBoolean())
        assertEquals(false, SideColor.Black.toBoolean())
    }

    @Test
    fun `toSideColor extension method for Boolean work properly`() {
        assertEquals(SideColor.White, true.toSideColor())
        assertEquals(SideColor.Black, false.toSideColor())
    }

    @Test
    fun `toUci and toMove should be inverse`() {
        val move = Move(Cell("a", "2"), Cell("a", "4"))
        val uci = move.toUci()
        val restored = uci.toMove()
        assertEquals(move, restored)
    }

    @Test
    fun `UserLocalEntity toUserGeneralEntity is correct`() {
        val local = UserLocalEntity("vasya", 0, 1, "fen", "e2e4", false, 0)
        val general = local.toUserGeneralEntity()
        assertEquals(local.name, general.name)
        assertEquals(local.fen, general.fen)
    }

    @Test
    fun `maps white pawn correctly`() {
        val actual = LibPiece.WHITE_PAWN.toDomainPiece()
        val expected = Piece(PieceType.Pawn, SideColor.White)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps black pawn correctly`() {
        val actual = LibPiece.BLACK_PAWN.toDomainPiece()
        val expected = Piece(PieceType.Pawn, SideColor.Black)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps white knight correctly`() {
        val actual = LibPiece.WHITE_KNIGHT.toDomainPiece()
        val expected = Piece(PieceType.Knight, SideColor.White)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps black knight correctly`() {
        val actual = LibPiece.BLACK_KNIGHT.toDomainPiece()
        val expected = Piece(PieceType.Knight, SideColor.Black)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps white bishop correctly`() {
        val actual = LibPiece.WHITE_BISHOP.toDomainPiece()
        val expected = Piece(PieceType.Bishop, SideColor.White)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps black bishop correctly`() {
        val actual = LibPiece.BLACK_BISHOP.toDomainPiece()
        val expected = Piece(PieceType.Bishop, SideColor.Black)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps white rook correctly`() {
        val actual = LibPiece.WHITE_ROOK.toDomainPiece()
        val expected = Piece(PieceType.Rook, SideColor.White)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps black rook correctly`() {
        val actual = LibPiece.BLACK_ROOK.toDomainPiece()
        val expected = Piece(PieceType.Rook, SideColor.Black)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps white queen correctly`() {
        val actual = LibPiece.WHITE_QUEEN.toDomainPiece()
        val expected = Piece(PieceType.Queen, SideColor.White)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps black queen correctly`() {
        val actual = LibPiece.BLACK_QUEEN.toDomainPiece()
        val expected = Piece(PieceType.Queen, SideColor.Black)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps white king correctly`() {
        val actual = LibPiece.WHITE_KING.toDomainPiece()
        val expected = Piece(PieceType.King, SideColor.White)
        assertEquals(expected, actual)
    }

    @Test
    fun `maps black king correctly`() {
        val actual = LibPiece.BLACK_KING.toDomainPiece()
        val expected = Piece(PieceType.King, SideColor.Black)
        assertEquals(expected, actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throws for unknown piece type`() {
        LibPiece.NONE.toDomainPiece()
    }
}