package com.twitter.tictactoe.heroku.controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.tictactoe.heroku.game.Game
import com.twitter.tictactoe.heroku.parser._

case class SlackResponse(
  response_type: String,
  text: String)

case class RequestParams(
  params: Array[String],
  userName: String,
  game: Option[Game] = None)

class TicTacToeRootController extends Controller {
  var game: Option[Game] = None

  def ticTacToeRootController = { request: Request =>

    info("Accessing tic tac toe controller")

    val params = request.params.getOrElse("text", "").split(" ")
    val userName = request.params.getOrElse("user_name", "unnamed")
    params(0) match {
      case "challenge" => challengePeer(RequestParams(params, userName))
      case "move" => move(RequestParams(params, userName, game))
      case "cancel" => cancelGame(RequestParams(params, userName, game))
      case "status" => status(RequestParams(params, userName, game))
      case "help" => help()
      case _ => errorResponse()
    }
  }

  def challengePeer(request: RequestParams) = {
    val validatedStatus = ChallengePeerParamParser.validate(request)
    validatedStatus.status match {
      case StatusType.OK => challengePeerValid(request)
      case StatusType.ERROR => errorResponse(validatedStatus.errorMessage)
      case _ => errorResponse()
    }
  }

  def move(request: RequestParams) = {
    val validatedStatus = MoveParamParser.validate(request)
    validatedStatus.status match {
      case StatusType.OK => moveValid(request)
      case StatusType.ERROR => errorResponse(validatedStatus.errorMessage)
      case _ => errorResponse()
    }
  }

  def cancelGame(request: RequestParams) = {
    val validatedStatus = CancelGameParamParser.validate(request)
    validatedStatus.status match {
      case StatusType.OK => cancelGameValid(request)
      case StatusType.ERROR => errorResponse(validatedStatus.errorMessage)
      case _ => errorResponse()
    }
  }

  def status(request: RequestParams) = {
    val validatedStatus = StatusParamParser.validate(request)
    validatedStatus.status match {
      case StatusType.OK => statusValid()
      case StatusType.ERROR => errorResponse(validatedStatus.errorMessage)
      case _ => errorResponse()
    }
  }

  def help() = {
    val text =
      s"""
         |usage:
         |/ttt challenge <userName> to start a new game
         |/ttt move <board_position> to make a move
         |/ttt cancel to cancel the current game. Only the the current players have permission to do so
         |/ttt status to view current board status, and whose turn it is
         |/ttt help to get all available commands
       """.stripMargin

    SlackResponse(response_type = "in_channel", text=text)
  }

  def errorResponse(errorMessage: Option[String] = None) = {
    SlackResponse(
      response_type = "",
      text=errorMessage.getOrElse("invalid commands, please type /ttt help to check out available commands"))
  }

  private def challengePeerValid(request: RequestParams) = {
    val peerName = request.params(1)
    game = Some(new Game(playerNameOne = request.userName, playerNameTwo = peerName))

    val text =
      s"""
         |${request.userName} is challenging ${peerName}!
         |starting a new game:
         |${game.get.toString}
       """.stripMargin

    SlackResponse(response_type = "in_channel", text=text)
  }

  private def moveValid(request: RequestParams) = {
    val position = request.params(1)
    val currGame = game.get
    currGame.move(request.userName, position.toInt - 1)

    val text =
      s"""
         |${request.userName} played at position ${position}
         |${currGame.toString}
       """.stripMargin

    if (currGame.isDone()) game = None
    SlackResponse(response_type = "in_channel", text=text)
  }

  private def cancelGameValid(request: RequestParams) = {
    game = None
    val text =
      s"""
         |${request.userName} successfully cancelled the current game
         |Please type /ttt challenge <user> to start a new game!
       """.stripMargin

    SlackResponse(response_type = "in_channel", text=text)
  }

  private def statusValid() = {
    val text =
      s"""
         |${game.get.toString}
      """.stripMargin

    SlackResponse(response_type = "in_channel", text=text)
  }

  get("/tictactoe") { ticTacToeRootController }
}
