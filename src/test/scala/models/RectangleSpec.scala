package models

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification


class RectangleSpec extends Specification with Mockito {

  "Rectangle" >> {
    val topLine =   Line(start = Point(2, 1), end = Point(4, 1))
    val rightLine = Line(start = Point(4, 1), end = Point(4, 5))
    val rectangle = Rectangle(top = topLine, right = rightLine)

    "should return left boundary line" >> {
      val expectedLine = Line(start = Point(2, 1), end = Point(2, 5))
      rectangle.left mustEqual expectedLine
    }

    "should return bottom boundary line" >> {
      val expectedLine = Line(start = Point(2, 5), end = Point(4, 5))
      rectangle.bottom mustEqual expectedLine
    }
  }
}
