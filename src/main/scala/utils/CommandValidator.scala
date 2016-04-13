package utils

import commands._
import exceptions.InvalidCommandException
import models.{Line, Point}

class CommandValidator {

  def validate(command: Command, canvasHeight: Int, canvasWidth: Int): Command = command match {
    case DrawLine(x1, y1, x2, y2)      =>
      if((Line(Point(x1, y1), Point(x2, y2)).isVertical || Line(Point(x1, y1), Point(x2, y2)).isHorizontal) &&
        Point(x1, y1).isInsideCanvas(canvasHeight, canvasWidth) &&
        Point(x2, y2).isInsideCanvas(canvasHeight, canvasWidth)) command
      else
        throw InvalidCommandException("Line should be either vertical or horizontal and fit inside your Canvas")

    case DrawRectangle(x1, y1, x2, y2) =>
      if(Point(x1, y1).isInsideCanvas(canvasHeight, canvasWidth) &&
        Point(x2, y2).isInsideCanvas(canvasHeight, canvasWidth)) command
      else
        throw InvalidCommandException("Rectangle should fit inside your Canvas")

    case BucketFill(x, y, char)      =>
      if(Point(x, y).isInsideCanvas(canvasHeight, canvasWidth)) command
      else throw InvalidCommandException("Select a point inside your Canvas for bucket filling")

    case other                         => other
  }
}