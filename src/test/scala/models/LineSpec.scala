package models

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class LineSpec extends Specification with Mockito {
  "Line" >> {

    val line = Line(Point(10, 20), Point(40, 50))
    val verticalLine = Line(Point(10, 20), Point(10, 50))
    val horizontalLine = Line(Point(10, 20), Point(30, 20))

    "should return correct length" >> {
      verticalLine.length mustEqual 30
      horizontalLine.length mustEqual 20
    }

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

    "should perform vertical shift a vertical line" >> {
      val shiftedLine = Line(Point(15, 20), Point(15, 50))
      verticalLine.verticalShift(5) mustEqual shiftedLine
    }

    "should not perform vertical shift a horizontal line" >> {
      horizontalLine.verticalShift(5) mustEqual horizontalLine
    }

    "should perform horizontal shift a horizontal line" >> {
      val shiftedLine = Line(Point(10, 25), Point(30, 25))
      horizontalLine.horizontalShift(5) mustEqual shiftedLine
    }

    "should not perform horizontal shift a vertical line" >> {
      verticalLine.horizontalShift(5) mustEqual verticalLine
    }
  }
}
