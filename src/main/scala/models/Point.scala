package models

case class Point(x: Int, y: Int) extends Entity {
  def < (that: Point) = this.x < that.x || (this.x == that.x && this.y < that.y)
}