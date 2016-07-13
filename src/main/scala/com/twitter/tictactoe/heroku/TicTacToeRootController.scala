package com.twitter.tictactoe.heroku

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

case class SlackResponse(
  response_type: String,
  text: String)

case class RequestParams(
 params: Array[String],
 userName: String)

class TicTacToeRootController extends Controller {
  var game: Game = null

  def ticTacToeRootController = { request: Request =>

    info("Accessing tic tac toe controller")
    val params = request.params.getOrElse("text", "").split(" ")
    val userName = request.params.getOrElse("user_name", "unnamed")
    params(0) match {
      case "challenge" => challengePeer(RequestParams(params, userName))
      case "play" => play(RequestParams(params, userName))
      case _ => "unvalid param"
    }
  }

  def challengePeer(request: RequestParams) = {
    // TODO handle scenaro without a peername
    val peerName = request.params(1)

    game = new Game(
      playerNameOne = request.userName,
      playerNameTwo = peerName
    )

    val text =
      s"""
         |${request.userName} is challenging ${peerName}!
         |starting a new game:
         |${game.toString}
       """.stripMargin

    SlackResponse(response_type = "in_channel", text=text)
  }

  def play(request: RequestParams) = {

    val position = request.params(1)
    game.move(request.userName, position.toInt - 1)

    val text =
      s"""
         |${request.userName} played at position ${position}
         |${game.toString}
       """.stripMargin

    if (game.isDone()) game = null
    SlackResponse(response_type = "in_channel", text=text)
  }

  get("/tictactoe") { ticTacToeRootController }
}
