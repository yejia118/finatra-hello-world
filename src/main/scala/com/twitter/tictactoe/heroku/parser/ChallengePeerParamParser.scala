package com.twitter.tictactoe.heroku.parser

import com.twitter.tictactoe.heroku.controllers.RequestParams

object ChallengePeerParamParser extends ParamParser {

  def validate(request: RequestParams): Status = {
    if (request.params.length < 2)
      Status(StatusType.ERROR, Some("Please speficy a person to challenge to"))
    else
      Status(StatusType.OK, None)
  }
}
