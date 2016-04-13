package models

case class Rectangle(top: Line, right: Line) extends Entity {
  val left = right.verticalShift(-top.length)
  val bottom = top.horizontalShift(right.length)
}
