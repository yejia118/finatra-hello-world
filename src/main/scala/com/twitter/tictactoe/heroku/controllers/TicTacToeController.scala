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
  token: String,
  channelId: String,
  game: Option[Game] = None)

class TicTacToeRootController extends Controller {
  // A map from channel_id to game
  val games = scala.collection.mutable.Map[String, Game]()

  def ticTacToeRootController = { request: Request =>

    info("Accessing tic tac toe controller")

    val params = request.params.getOrElse("text", "").split(" ")
    val userName = request.params.getOrElse("user_name", "unnamed")
    val token = request.params.getOrElse("token", "")
    val channelId = request.params.getOrElse("channel_id", "")
    val game = games.get(channelId)

    val parsedReq = RequestParams(
      params,
      userName,
      token,
      channelId,
      game
    )

    val tokenStatus = TokenParamParser.validate(parsedReq)
    tokenStatus.status match {
      case StatusType.OK => params(0) match {
        case "challenge" => challengePeer(parsedReq)
        case "move" => move(parsedReq)
        case "cancel" => cancelGame(parsedReq)
        case "status" => status(parsedReq)
        case "help" => help()
        case _ => errorResponse()
      }
      case StatusType.ERROR => errorResponse(tokenStatus.errorMessage)
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
      case StatusType.OK => statusValid(request)
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
    val game = new Game(playerNameOne = request.userName, playerNameTwo = peerName)
    games.put(request.channelId, game)

    val text =
      s"""
         |${request.userName} is challenging ${peerName}!
         |starting a new game:
         |${game.toString}
       """.stripMargin

    SlackResponse(response_type = "in_channel", text=text)
  }

  private def moveValid(request: RequestParams) = {
    val position = request.params(1)
    val currGame = request.game.get
    currGame.move(request.userName, position.toInt - 1)

    val text =
      s"""
         |${request.userName} played at position ${position}
         |${currGame.toString}
       """.stripMargin

    if (currGame.isDone()) games.remove(request.channelId)
    SlackResponse(response_type = "in_channel", text=text)
  }

  private def cancelGameValid(request: RequestParams) = {
    games.remove(request.channelId)
    val text =
      s"""
         |${request.userName} successfully cancelled the current game
         |Please type /ttt challenge <user> to start a new game!
       """.stripMargin

    SlackResponse(response_type = "in_channel", text=text)
  }

  private def statusValid(request: RequestParams) = {
    val text =
      s"""
         |${request.game.get.toString}
      """.stripMargin

    SlackResponse(response_type = "in_channel", text=text)
  }

  get("/tictactoe") { ticTacToeRootController }
}
