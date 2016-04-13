package utils

import models._

class CanvasPainter {
  def paint(entities: List[Entity]): Canvas = entities match {
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
