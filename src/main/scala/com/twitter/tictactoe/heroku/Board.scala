package com.twitter.tictactoe.heroku

class Board {

  val positions = Array[String](
      null, null, null,
      null, null, null,
      null, null, null);

  def move(mark: String, position: Int) = positions.update(position, mark);

  def isFull(): Boolean = !positions.exists(_ == null)

  def isValidMove(position: Int): Boolean = position < 0 || position > 8 || positions(position) != null

  def isWonBy(mark: String): Boolean = Board.winSets.exists(winSet => winSet.forall(positions(_) == mark))

  override def toString() = {
    val positionsStr: Array[String] = positions.zipWithIndex.map{ case (p, i) =>
      if (p != null) p else (i+1).toString
    }
    s"""
       ||${positionsStr(0)}|${positionsStr(1)}|${positionsStr(2)}|
       ||${positionsStr(3)}|${positionsStr(4)}|${positionsStr(5)}|
       ||${positionsStr(6)}|${positionsStr(7)}|${positionsStr(8)}|
    """.stripMargin
  }

}

object Board
{
  val winSets =
    Set(
      Array(0, 1, 2),
      Array(3, 4, 5),
      Array(6, 7, 8),
      Array(0, 3, 6),
      Array(1, 4, 7),
      Array(2, 5, 8),
      Array(0, 4, 8),
      Array(2, 4, 6))

}
