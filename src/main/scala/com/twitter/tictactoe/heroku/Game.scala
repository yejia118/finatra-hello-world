package com.twitter.tictactoe.heroku

case class Player(name: String, mark: String)

class Game(playerNameOne: String, playerNameTwo: String) {

  val board = new Board();
  val playerOne = new Player(playerNameOne, "x")
  val playerTwo = new Player(playerNameTwo, "o")

  def move(playerName: String, position: Int) = {
    val mark = if (playerOne.name == playerName)  playerOne.mark else playerTwo.mark
    board.move(mark, position)
  }

  def whosTurn(): String = {
    val playerOneCount = board.positions.filter(_ == "x").length
    val playerTwoCount = board.positions.filter(_ == "o").length

    if (playerTwoCount >= playerOneCount)
      playerOne.name
    else
      playerTwo.name
  }

  def isDone(): Boolean = board.isFull() || board.isWonBy("x") || board.isWonBy("o")

  def wonBy(): String = {
    if (board.isWonBy("x")) playerOne.name
    else if (board.isWonBy("o")) playerTwo.name
    else "no one"
  }

  override def toString = {
    val boardInfo =
      s"""
         |${playerOne.name} is ${playerOne.mark}
         |${playerTwo.name} is ${playerTwo.mark}
         |current board status:
         |${board.toString}
         |
    """.stripMargin

    val turnsInfo =
      s"""
         |It is ${whosTurn()}'s turn
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
