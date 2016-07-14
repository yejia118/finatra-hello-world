package com.twitter.tictactoe.heroku.game

import com.twitter.tictactoe.heroku.test.BaseTest

class BoardTest extends BaseTest {

  test("move should update the board on a particular position") {
    val board = new Board();
    board.move("x", 0)
    assert(board.positions(0) == "x")
  }

  test("isFull returns true if board is full") {
    val board = new Board()
    (0 to 8).map(board.positions.update(_, "x"))
    assert(board.isFull())
  }

  test("isFull returns false if board is not full") {
    val board = new Board()
    (0 to 8).map(board.positions.update(_, null))
    assert(!board.isFull())
  }

  test("isValidMove returns true is the move is valid") {
    val board = new Board()
    assert(board.isValidMove(1))
  }

  test("isValidMove returns false for invalid move") {
    val board = new Board()
    assert(!board.isValidMove(100))
  }

  test("isWonBy") {
    val board = new Board()
    board.positions = Array[String]("x", "x", "x", "o", null, "o", null, null, null)
    assert(board.isWonBy("x"))
    assert(!board.isWonBy("o"))
  }
}
