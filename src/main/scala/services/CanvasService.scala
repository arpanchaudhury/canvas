package services

import commands._
import constants.ErrorMessages
import exceptions.{CommandParseException, InvalidCommandException}
import io.CanvasIO
import models._
import utils.{CanvasPainter, CommandTranslator}

import scala.util.{Failure, Success}

class CanvasService(canvasIO: CanvasIO, commandTranslator: CommandTranslator, canvasPainter: CanvasPainter) {

  def run(accumulator: List[Command] = List.empty[Command]): List[Command] = {
    canvasIO.getCommand(accumulator) match {

      case Success(Quit)                                                => accumulator :+ Quit

      case Success(command: CreateCanvas) if accumulator.isEmpty        => val entities = commandTranslator.translate(accumulator :+ command)
                                                                           val canvasWithEntities = canvasPainter.paint(entities)
                                                                           canvasIO.printCanvas(canvasWithEntities)
                                                                           run(accumulator :+ command)

      case Success(command: CreateCanvas)                               => println(ErrorMessages.MultipleCanvasCreation)
                                                                           run(accumulator)

      case Success(command: Command)      if accumulator.isEmpty        => println(ErrorMessages.CanvasNotCreated)
                                                                           run(accumulator)

      case Success(command: Command)                                    => val entities = commandTranslator.translate(accumulator :+ command)
                                                                           val canvasWithEntities = canvasPainter.paint(entities)
                                                                           canvasIO.printCanvas(canvasWithEntities)
                                                                           run(accumulator :+ command)

      case Failure(exception: CommandParseException)                    => println(ErrorMessages.ParseCommand(exception))
                                                                           run(accumulator)

      case Failure(exception: InvalidCommandException)                  => println(ErrorMessages.InvalidCommand(exception))
                                                                           run(accumulator)

      case Failure(exception)                                           => println(ErrorMessages.Other(exception))
                                                                           sys.exit(1)
    }
  }
}
