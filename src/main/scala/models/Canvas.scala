package models

abstract case class Canvas private[Canvas] (height: Int, width: Int) extends Entity {
  private val underlying = Array.ofDim[Char](height + 2, width + 2)

  def mkString = underlying.map(row => row.mkString(" ")).mkString("\n")

  def drawLine(line: Line): Canvas = {
    line match {
      case l if l.isVertical    =>
        for {
          r <- line.start.y to line.end.y
          c = line.start.x
        } underlying(r)(c) = '*'
      case l if l.isHorizontal  =>
        for {
          c <- line.start.x to line.end.x
          r = line.start.y
        } underlying(r)(c) = '*'
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
}

object Canvas {
  def apply(height: Int, width: Int) = {
    val canvas = new Canvas(height, width) {}
    for {
      row    <- 0 until height  + 2
      column <- 0 until width + 2
    } {
      if(row == 0 || row == height + 1)             canvas.underlying(row)(column) = '-'
      else if(column == 0 || column == width + 1)   canvas.underlying(row)(column) = '|'
      else                                          canvas.underlying(row)(column) = ' '
    }
    canvas
  }
}
