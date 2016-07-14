package com.twitter.tictactoe.heroku.game

import com.twitter.tictactoe.heroku.test.BaseTest

class GameTest extends BaseTest{

  test("move will update the board with the right mark") {
    val game = new Game("Bob", "Amy")
    game.move("Bob", 1)
    assert(game.board.positions(1) == Game.playerOneMark)
  }

  test("isValidMove returns true is the move is valid") {
    val game = new Game("Bob", "Amy")
    assert(game.isValidMove(1))
  }

  test("isValidMove returns false for invalid move") {
    val game = new Game("Bob", "Amy")
    assert(!game.isValidMove(100))
  }

  test("whoesTurn") {
    val game = new Game("Bob", "Amy")
    game.move("Bob", 1)
    assert(game.whoesTurn() == "Amy")
  }

  test("isDone returns true if the board is full") {
    val game = new Game("Bob", "Amy")
    (0 to 8).map(game.board.positions.update(_, "x"))
    assert(game.isDone())
  }

  test("isDone returns true if one player won") {
    val game = new Game("Bob", "Amy")
    game.board.positions = Array[String]("x", "x", "x", "o", null, "o", null, null, null)
    assert(game.isDone())
  }

  test("isDone returns true if the no one won") {
    val game = new Game("Bob", "Amy")
    assert(!game.isDone())
  }

  test("wonBy returns the winner") {
    val game = new Game("Bob", "Amy")
    game.board.positions = Array[String]("x", "x", "x", "o", null, "o", null, null, null)
    assert(game.wonBy() == "Bob")
  }

  test("wonBy returns no one if no one won the game") {
    val game = new Game("Bob", "Amy")
    assert(game.wonBy() == "no one")
  }
}
