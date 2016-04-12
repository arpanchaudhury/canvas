package models

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class LineSpec extends Specification with Mockito {
  "Line" >> {

    val line = Line(Point(10, 20), Point(40, 50))
    val verticalLine = Line(Point(10, 20), Point(10, 50))
    val horizontalLine = Line(Point(10, 20), Point(30, 20))

    "should respond to is vertical queries" >> {
      line.isVertical             mustEqual false
      verticalLine.isVertical     mustEqual true
      horizontalLine.isVertical   mustEqual false
    }

    "should respond to is horizontal queries" >> {
      line.isHorizontal           mustEqual false
      verticalLine.isHorizontal   mustEqual false
      horizontalLine.isHorizontal mustEqual true
    }
  }
}
