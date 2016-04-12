package services

import commands.{Command, CreateCanvas, Quit}
import constants.ErrorMessages
import exceptions.{CommandParseException, InvalidCommandException}
import io.CanvasIO

import scala.util.{Failure, Success}

class CanvasService(canvasIO: CanvasIO) {

  def commandLoop(accumulator: List[Command] = List.empty[Command]): List[Command] = {
    canvasIO.getCommand(accumulator) match {

      case Success(Quit)                                                => accumulator :+ Quit

      case Success(command: CreateCanvas) if accumulator.isEmpty        => commandLoop(accumulator :+ command)

      case Success(command: CreateCanvas)                               => println(ErrorMessages.MultipleCanvasCreation)
                                                                           commandLoop(accumulator)

      case Success(command: Command)      if accumulator.isEmpty        => println(ErrorMessages.CanvasNotCreated)
                                                                           commandLoop(accumulator)

      case Success(command: Command)                                    => commandLoop(accumulator :+ command)

      case Failure(exception: CommandParseException)                    => println(ErrorMessages.ParseCommand(exception))
                                                                           commandLoop(accumulator)

      case Failure(exception: InvalidCommandException)                  => println(ErrorMessages.InvalidCommand(exception))
                                                                           commandLoop(accumulator)

      case Failure(exception)                                           => println(ErrorMessages.Other(exception))
                                                                           sys.exit(1)
    }
  }
}
