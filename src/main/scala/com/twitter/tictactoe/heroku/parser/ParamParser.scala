package com.twitter.tictactoe.heroku.parser

import com.twitter.tictactoe.heroku.controllers.RequestParams
import com.twitter.tictactoe.heroku.parser.StatusType.StatusType

object StatusType extends Enumeration {
  type StatusType = Value
  val OK, ERROR = Value
}
case class Status(status: StatusType, errorMessage: Option[String])

trait ParamParser {
  def validate(request: RequestParams): Status
}
