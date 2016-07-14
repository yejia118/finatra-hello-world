package com.twitter.tictactoe.heroku

import com.codahale.metrics.MetricFilter
import com.twitter.finagle.http.Status._
import com.twitter.finagle.metrics.MetricsStatsReceiver
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import com.twitter.tictactoe.heroku.server.TicTacToeServer

class HelloWorldFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new TicTacToeServer)

  override def afterEach() {
    MetricsStatsReceiver.metrics.removeMatching(MetricFilter.ALL)
  }

  "Server" should {
    "Say hi" in {
      server.httpGet(
        path = "/hi?text=Bob",
        andExpect = Ok,
        withBody = "Hello Bob")
    }
  }
}
