package models

import constants.CanvasConstants

abstract case class Canvas private[Canvas] (height: Int, width: Int) extends Entity {

  private val underlying = Array.ofDim[Char](height + 2, width + 2)

  def mkString = underlying.map(row => row.mkString(" ")).mkString("\n")

  def drawLine(line: Line): Canvas = {
    line match {
      case l if l.isVertical    =>
        for {
          r <- line.start.y to line.end.y
          c = line.start.x
        } underlying(r)(c) = CanvasConstants.Pixel
      case l if l.isHorizontal  =>
        for {
          c <- line.start.x to line.end.x
          r = line.start.y
        } underlying(r)(c) = CanvasConstants.Pixel
    }
    this
  }

  def drawRectangle(rectangle: Rectangle) = {
    drawLine(rectangle.top)
    drawLine(rectangle.right)
    drawLine(rectangle.bottom)
    drawLine(rectangle.left)
    this
  }

  def paintArea(areaPaint: AreaPaint) = {
    paintAreaHelper(areaPaint.point, areaPaint.color)
    this
  }

  private def paintAreaHelper(point: Point, color: Char): Unit = {
    if(point.isInsideCanvas(height, width) && underlying(point.y)(point.x) == ' ') {
      underlying(point.y)(point.x) = color
      point.neighbours.foreach(paintAreaHelper(_, color))
    }
    else {}
  }
}

object Canvas {
  def apply(height: Int, width: Int) = {
    val canvas = new Canvas(height, width) {}
    for {
      row    <- 0 until height  + 2
      column <- 0 until width + 2
    } {
      if(row == 0 || row == height + 1)             canvas.underlying(row)(column) = CanvasConstants.TopBorder
      else if(column == 0 || column == width + 1)   canvas.underlying(row)(column) = CanvasConstants.SideBorder
      else                                          canvas.underlying(row)(column) = CanvasConstants.Blank
    }
    canvas
  }
}
