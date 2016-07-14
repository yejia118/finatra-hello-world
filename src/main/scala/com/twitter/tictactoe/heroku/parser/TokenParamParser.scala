package com.twitter.tictactoe.heroku.parser

import com.twitter.tictactoe.heroku.constants.TicTacToeTokens
import com.twitter.tictactoe.heroku.controllers.RequestParams

object TokenParamParser extends ParamParser {

  def validate(request: RequestParams): Status = {
    if(TicTacToeTokens.authorizedTokens.contains(request.token))
      Status(StatusType.OK, None)
    else
      Status(StatusType.ERROR, Some("Your team is not authorized to play tic tac toe game"))
  }
}
