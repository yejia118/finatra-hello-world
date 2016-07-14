package com.twitter.tictactoe.heroku.parsers

import com.twitter.tictactoe.heroku.controllers.RequestParams
import com.twitter.tictactoe.heroku.game.Game
import com.twitter.tictactoe.heroku.parser.{CancelGameParamParser, StatusType, Status}
import com.twitter.tictactoe.heroku.test.BaseTest


class CancelGameParamParserTest extends BaseTest {
  test("validate returns error if no game is in current channel") {
    val params = RequestParams(
      params = Array[String]("move", "1"),
      userName = "Bob",
      game = None
    )
    val expectedResponse = Status(
      StatusType.ERROR,
      Some("There is no game in this channel to cancel. Please type / ttt challenge <user> to start a new game!")
    )
    assert(CancelGameParamParser.validate(params) == expectedResponse)
  }

  test("validate returns ok if status is valid") {
    val params = RequestParams(
      params = Array[String]("move", "1"),
      userName = "Bob",
      game = Some(new Game("Bob", "Amy"))
    )
    val expectedResponse = Status(
      StatusType.OK,
      None
    )
    assert(CancelGameParamParser.validate(params) == expectedResponse)
  }
}