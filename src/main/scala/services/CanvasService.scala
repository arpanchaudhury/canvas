package services

import commands._
import constants.ErrorMessages
import exceptions.{CommandParseException, InvalidCommandException}
import io.CanvasIO
import models._

import scala.util.{Failure, Success}

class CanvasService(canvasIO: CanvasIO) {

  def commandLoop(accumulator: List[Command] = List.empty[Command]): List[Command] = {
    canvasIO.getCommand(accumulator) match {

      case Success(Quit)                                                => accumulator :+ Quit

      case Success(command: CreateCanvas) if accumulator.isEmpty        => val entities = translate(accumulator :+ command)
                                                                           val canvasWithEntities = drawCanvas(entities)
                                                                           canvasIO.printCanvas(canvasWithEntities)
                                                                           commandLoop(accumulator :+ command)

      case Success(command: CreateCanvas)                               => println(ErrorMessages.MultipleCanvasCreation)
                                                                           commandLoop(accumulator)

      case Success(command: Command)      if accumulator.isEmpty        => println(ErrorMessages.CanvasNotCreated)
                                                                           commandLoop(accumulator)

      case Success(command: Command)                                    => val entities = translate(accumulator :+ command)
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

  def translate(commands: List[Command]): List[Entity] = commands.map(command => translate(command)).filter(_.isDefined).map(_.get)

  private def translate(command: Command): Option[Entity] = command match {
    case CreateCanvas(height, width)    => Some(Canvas(height, width))
    case DrawLine(x1, y1, x2, y2)       => val point1 = Point(x1, y1)
                                           val point2 = Point(x2, y2)
                                           if(point1 < point2) Some(Line(point1, point2)) else Some(Line(point2, point1))
    case DrawRectangle(x1, y1, x2, y2)  => val point1 = Point(x1, y1)
                                           val point2 = Point(x2, y2)
                                           if(point1 < point2) Some(
                                             Rectangle(
                                               Line(point1, Point(point2.x, point1.y)),
                                               Line(Point(point2.x, point1.y), point2)
                                             )
                                           )
                                           else Some(
                                             Rectangle(
                                               Line(point2, Point(point1.x, point2.y)),
                                               Line(Point(point1.x, point2.y), point1)
                                             )
                                           )
    case BucketFill(x, y, color)        => Some(AreaPaint(Point(x, y), color))
    case otherwise                      => None
  }
}
