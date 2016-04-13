package services

import commands._
import constants.ErrorMessages
import exceptions.{CommandParseException, InvalidCommandException}
import io.CanvasIO
import models._
import utils.CommandTranslator

import scala.util.{Failure, Success}

class CanvasService(canvasIO: CanvasIO, commandTranslator: CommandTranslator) {

  def commandLoop(accumulator: List[Command] = List.empty[Command]): List[Command] = {
    canvasIO.getCommand(accumulator) match {

      case Success(Quit)                                                => accumulator :+ Quit

      case Success(command: CreateCanvas) if accumulator.isEmpty        => val entities = commandTranslator.translate(accumulator :+ command)
                                                                           val canvasWithEntities = drawCanvas(entities)
                                                                           canvasIO.printCanvas(canvasWithEntities)
                                                                           commandLoop(accumulator :+ command)

      case Success(command: CreateCanvas)                               => println(ErrorMessages.MultipleCanvasCreation)
                                                                           commandLoop(accumulator)

      case Success(command: Command)      if accumulator.isEmpty        => println(ErrorMessages.CanvasNotCreated)
                                                                           commandLoop(accumulator)

      case Success(command: Command)                                    => val entities = commandTranslator.translate(accumulator :+ command)
                                                                           val canvasWithEntities = drawCanvas(entities)
                                                                           canvasIO.printCanvas(canvasWithEntities)
                                                                           commandLoop(accumulator :+ command)

      case Failure(exception: CommandParseException)                    => println(ErrorMessages.ParseCommand(exception))
                                                                           commandLoop(accumulator)

      case Failure(exception: InvalidCommandException)                  => println(ErrorMessages.InvalidCommand(exception))
                                                                           commandLoop(accumulator)

      case Failure(exception)                                           => println(ErrorMessages.Other(exception))
                                                                           sys.exit(1)
    }
  }

  def drawCanvas(entities: List[Entity]): Canvas = entities match {
    case Nil                => null
    case (c : Canvas) :: xs => entities.tail.foldLeft(c)((canvas, entity) => drawEntity(entity)(canvas))
    case whatever           => null
  }

  private def drawEntity(entity: Entity)(canvas: Canvas) = entity match {
    case line: Line           => canvas.drawLine(line)
    case rectangle: Rectangle => canvas.drawRectangle(rectangle)
    case areaPaint: AreaPaint => canvas.paintArea(areaPaint)
    case other                => canvas
  }
}
