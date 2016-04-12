package models

case class Line(start: Point, end: Point) {
  def isVertical = start.x == end.x

  def isHorizontal = start.y == end.y
}
