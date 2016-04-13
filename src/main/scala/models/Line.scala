package models

case class Line(start: Point, end: Point) extends Entity {

  def length = if(isVertical) Math.abs(start.y - end.y)
               else if(isHorizontal) Math.abs(start.x - end.x)
               else sys.error("[Error] : Cannot determine. Out of scope.")

  def isVertical = start.x == end.x

  def isHorizontal = start.y == end.y

  def verticalShift(index: Int) = if(isVertical) Line(Point(start.x + index, start.y), Point(end.x + index, end.y))
                                  else this

  def horizontalShift(index: Int) = if(isHorizontal) Line(Point(start.x, start.y + index), Point(end.x, end.y + index))
                                    else this
}
