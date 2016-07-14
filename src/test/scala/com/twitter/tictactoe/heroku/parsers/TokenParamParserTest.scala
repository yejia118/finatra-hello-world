package com.twitter.tictactoe.heroku.parsers

import com.twitter.tictactoe.heroku.constants.TicTacToeTokens
import com.twitter.tictactoe.heroku.controllers.RequestParams
import com.twitter.tictactoe.heroku.parser.{TokenParamParser, StatusType, Status}
import com.twitter.tictactoe.heroku.test.BaseTest

class TokenParamParserTest extends BaseTest {
  test("validate returns error if the token is not authorized") {
    val params = RequestParams(
      params = Array[String]("move", "1"),
      userName = "Bob",
      token="invalid_token",
      channelId = "1",
      game = None
    )
    val expectedResponse = Status(
      StatusType.ERROR,
      Some("Your team is not authorized to play tic tac toe game")
    )
    assert(TokenParamParser.validate(params) == expectedResponse)
  }

  test("validate returns ok if the token is authorized") {
    val params = RequestParams(
      params = Array[String]("move", "1"),
      userName = "Bob",
      token=TicTacToeTokens.authorizedTokens.head,
      channelId = "1",
      game = None
    )
    val expectedResponse = Status(
      StatusType.OK,
      None
    )
    assert(TokenParamParser.validate(params) == expectedResponse)
  }

}
