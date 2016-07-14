package com.twitter.tictactoe.heroku.game

case class Player(name: String, mark: String)

object Game {
  val playerOneMark = "x"
  val playerTwoMark = "o"
}

class Game(playerNameOne: String, playerNameTwo: String) {

  val board = new Board();
  val playerOne = new Player(playerNameOne, Game.playerOneMark)
  val playerTwo = new Player(playerNameTwo, Game.playerTwoMark)
  var lastPlayedBy = playerTwo.name

  def move(playerName: String, position: Int) = {
    val mark = if (playerOne.name == playerName)  playerOne.mark else playerTwo.mark
    board.move(mark, position)
    lastPlayedBy = playerName
  }

  def isValidMove(position: Int): Boolean = {
    board.isValidMove(position)
  }

  def whoesTurn(): String = {
    if (lastPlayedBy == playerNameOne)
      playerNameTwo
    else
      playerNameOne
  }

  def isDone(): Boolean =
      board.isFull() ||
      board.isWonBy(Game.playerOneMark) ||
      board.isWonBy(Game.playerTwoMark)

  def wonBy(): String = {
    if (board.isWonBy(Game.playerOneMark)) playerOne.name
    else if (board.isWonBy(Game.playerTwoMark)) playerTwo.name
    else "no one"
  }

  override def toString = {
    val boardInfo =
      s"""
         |${playerOne.name} is ${playerOne.mark}, ${playerTwo.name} is ${playerTwo.mark}
         |current board status:
         |${board.toString}
         |
    """.stripMargin

    val turnsInfo =
      s"""
         |It is ${whoesTurn()}'s turn
         |Please enter the number of the position and make a move
       """.stripMargin

    val winningInfo =
    s"""
       |Game is finished, and ${wonBy()} won the game
       |Please type /ttt challenge <user> to restart a game
     """.stripMargin

    if (isDone())
      boardInfo + winningInfo
    else
      boardInfo + turnsInfo
  }
}
