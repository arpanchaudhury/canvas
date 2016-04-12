package io

import commands.{Command, CreateCanvas}
import constants.ErrorMessages
import utils.{CommandParser, CommandValidator}

import scala.util.{Failure, Success, Try}

class CanvasIO(commandParser: CommandParser, commandValidator: CommandValidator) {
  def getCommand(accumulator: List[Command]): Try[Command] = Try {
    print("enter command: ")
    commandParser.parse(scala.io.StdIn.readLine())
  } flatMap { command =>
    accumulator match {
      case Nil                             => Success(command)
      case (first: CreateCanvas) :: rest   => Try(commandValidator.validate(command, first.height, first.width))
      case unprecedented                   => Failure(sys.error(ErrorMessages.CanvasNotCreated))
    }
  }
}
