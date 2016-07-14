package com.twitter.tictactoe.heroku.parsers

import com.twitter.tictactoe.heroku.controllers.RequestParams
import com.twitter.tictactoe.heroku.game.Game
import com.twitter.tictactoe.heroku.parser.{MoveParamParser, StatusType, Status}
import com.twitter.tictactoe.heroku.test.BaseTest

class MoveParamParserTest extends BaseTest {

  test("validate returns error if no game is in current channel") {
    val params = RequestParams(
      params = Array[String]("move", "1"),
      userName = "Bob",
      game = None
    )
    val expectedResponse = Status(
      StatusType.ERROR,
      Some("No game is currently in this channel, please type /ttt challenge <user> to start a new game!")
    )
    assert(MoveParamParser.validate(params) == expectedResponse)
  }

  test("validate returns error if the player is not valid") {
    val params = RequestParams(
      params = Array[String]("move", "1"),
      userName = "John",
      game = Some(new Game("Bob", "Amy"))
    )
    val expectedResponse = Status(
      StatusType.ERROR,
      Some("It is Bob's turn. Only Bob can make a move")
    )
    assert(MoveParamParser.validate(params) == expectedResponse)
  }

  test("validate returns error if no position is specified") {
    val params = RequestParams(
      params = Array[String]("move"),
      userName = "Bob",
      game = Some(new Game("Bob", "Amy"))
    )
    val expectedResponse = Status(
      StatusType.ERROR,
      Some("Please specify the position you intend to move to")
    )
    assert(MoveParamParser.validate(params) == expectedResponse)
  }

  test("validate returns error if position is invalid") {
    val params = RequestParams(
      params = Array[String]("move", "10"),
      userName = "Bob",
      game = Some(new Game("Bob", "Amy"))
    )
    val expectedResponse = Status(
      StatusType.ERROR,
      Some("Position 10 is invalid, please view board status, and move to an open position")
    )
    assert(MoveParamParser.validate(params) == expectedResponse)
  }

  test("validate returns ok if move is valid") {
    val params = RequestParams(
      params = Array[String]("move", "1"),
      userName = "Bob",
      game = Some(new Game("Bob", "Amy"))
    )
    val expectedResponse = Status(
      StatusType.OK,
      None
    )
    assert(MoveParamParser.validate(params) == expectedResponse)
  }
}
