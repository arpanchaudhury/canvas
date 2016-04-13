package models

case class Point(x: Int, y: Int) extends Entity {
  def < (that: Point) = this.x < that.x || (this.x == that.x && this.y < that.y)

  def isInsideCanvas(canvasHeight: Int, canvasWidth: Int) = {
    (x >= 1 && x <= canvasWidth) && (y >= 1 && y <= canvasHeight)
  }

  def neighbours: Set[Point] =
    (
      for {
        x <- this.x - 1 to this.x + 1
        y <- this.y - 1 to this.y + 1
      } yield Point(x, y)
    ).toSet - this
}