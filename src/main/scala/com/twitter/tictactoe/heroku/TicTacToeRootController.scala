package com.twitter.tictactoe.heroku

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

case class SlackResponse(
  response_type: String,
  text: String)

class TicTacToeRootController extends Controller {

  def ticTacToeRootController = { request: Request =>

    info("Accessing tic tac toe controller")
    val params = request.params.getOrElse("text", "").split(" ")
    params(0) match {
      case "challenge" => challengePeerController(params)
      case _ => "unvalid param"
    }
  }

  def challengePeerController(params: Array[String]) = {
    val peerName = params(1)
    SlackResponse(response_type = "in_channel", text=s"jia is challenging ${peerName}!")
  }

  get("/tictactoe") { ticTacToeRootController }
}
