package com.twitter.tictactoe.heroku.parser

import com.twitter.tictactoe.heroku.controllers.RequestParams
import com.twitter.tictactoe.heroku.game.Game

object MoveParamParser extends ParamParser {

  def validate(request: RequestParams): Status = {
    request.game match {
      case Some(g) => MoveParamParser.validateWithGame(request, g)
      case _ => Status(
        StatusType.ERROR,
        Some("No game is currently in this channel, please type /ttt challenge <user> to start a new game!"))
    }
  }

  private def validateWithGame(request: RequestParams, game: Game): Status = {
    val validPlayer = game.whoesTurn()

    if (request.userName != validPlayer)
      Status(StatusType.ERROR, Some(s"It is ${validPlayer}'s turn. Only ${validPlayer} can make a move"))
    else if (request.params.length < 2)
      Status(StatusType.ERROR, Some("Please specify the position you intend to move to"))
    else if (!isInt(request.params(1)) || !game.isValidMove(request.params(1).toInt))
      Status(
        StatusType.ERROR,
        Some(s"Position ${request.params(1)} is invalid, please view board status, and move to an open position"))
    else
      Status(StatusType.OK, None)
  }

  private def isInt(s: String): Boolean = s.matches("[+-]?\\d+")
}
