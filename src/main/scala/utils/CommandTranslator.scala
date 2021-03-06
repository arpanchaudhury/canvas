package utils

import commands._
import models._

import scala.collection.mutable

class CommandTranslator {
  def translate(commands: List[Command]): List[Entity] =
    removeUndoCommands(commands).map(command => translate(command)).filter(_.isDefined).map(_.get)

  def removeUndoCommands(commands: List[Command]): List[Command] = {
    val commandStack = mutable.Stack[Command]()
    commands.foreach {
      command =>
        if(commandStack.nonEmpty && command == Undo) {
          commandStack.top match {
            case createCanvas :CreateCanvas =>
            case _                          => commandStack.pop()
          }
        }
        else if(commandStack.isEmpty && command == Undo) {}
        else commandStack.push(command)
    }
    commandStack.toList.reverse
  }

  private def translate(command: Command): Option[Entity] = command match {
    case CreateCanvas(height, width)    => Some(Canvas(height, width))
    case DrawLine(x1, y1, x2, y2)       => val point1 = Point(x1, y1)
                                           val point2 = Point(x2, y2)
                                           if(point1 < point2) Some(Line(point1, point2))
                                           else Some(Line(point2, point1))
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
