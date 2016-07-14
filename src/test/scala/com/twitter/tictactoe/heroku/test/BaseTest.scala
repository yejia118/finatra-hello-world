package com.twitter.tictactoe.heroku.test

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class BaseTest
  extends FunSuite
  with MockitoSugar
  with BeforeAndAfter