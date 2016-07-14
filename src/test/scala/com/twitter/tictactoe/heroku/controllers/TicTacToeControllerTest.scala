package com.twitter.tictactoe.heroku.controllers

import com.codahale.metrics.MetricFilter
import com.twitter.finagle.http.Status._
import com.twitter.finagle.metrics.MetricsStatsReceiver
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import com.twitter.tictactoe.heroku.server.TicTacToeServer

class TicTacToeControllerTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new TicTacToeServer)

  override def afterEach() {
    MetricsStatsReceiver.metrics.removeMatching(MetricFilter.ALL)
  }

  "Server" should {
    "return error response with invalid command" in {
      server.httpGet(
        path = "/tictactoe?text=invalidCommand&token=CyO37xuT10jQSCcegfTWqdFm",
        andExpect = Ok,
        withBody = "{\"response_type\":\"\",\"text\":\"invalid commands, please type /ttt help to check out available commands\"}")
    }
  }

  "Server" should {
    "return success response with valid command" in {
      server.httpGet(
        path = "/tictactoe?text=help&token=CyO37xuT10jQSCcegfTWqdFm",
        andExpect = Ok,
        withBody = "{\"response_type\":\"in_channel\",\"text\":\"\\nusage:\\n/ttt challenge <userName> to start a new game\\n/ttt move <board_position> to make a move\\n/ttt cancel to cancel the current game. Only the the current players have permission to do so\\n/ttt status to view current board status, and whose turn it is\\n/ttt help to get all available commands\\n       \"}")
    }
  }


}
