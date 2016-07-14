package com.twitter.tictactoe.heroku.parsers

import com.twitter.tictactoe.heroku.controllers.RequestParams
import com.twitter.tictactoe.heroku.parser.{ChallengePeerParamParser, StatusType, Status}
import com.twitter.tictactoe.heroku.test.BaseTest

class ChallengePeerParserTest extends BaseTest {
  test("validate returns ok if a user name is specified") {
    val params = RequestParams(
      params = Array[String]("challenge", "Amy"),
      userName = "Bob"
    )
    val expectedResponse = Status(
      StatusType.OK,
      None
    )
    assert(ChallengePeerParamParser.validate(params) == expectedResponse)
  }

  test("validate returns error if a user name is not specified") {
    val params = RequestParams(
      params = Array[String]("challenge"),
      userName = "Bob"
    )
    val expectedResponse = Status(
      StatusType.ERROR,
      Some("Please speficy a person to challenge to")
    )
    assert(ChallengePeerParamParser.validate(params) == expectedResponse)
  }
}
