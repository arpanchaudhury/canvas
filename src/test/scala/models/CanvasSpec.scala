package models

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification


class CanvasSpec extends Specification with Mockito {

  "Canvas" >> {

    "should create a canvas with boundaries" >> {
      val canvas = Canvas(5, 7)
      canvas.mkString mustEqual   """- - - - - - - - -
                                    >|               |
                                    >|               |
                                    >|               |
                                    >|               |
                                    >|               |
                                    >- - - - - - - - -""".stripMargin('>')
    }

    "should draw a vertical line in canvas" >> {
      val canvas = Canvas(5, 7)
      val verticalLine = Line(start = Point(2, 1), end = Point(2, 5))
      val canvasWithVerticalLine = canvas.drawLine(verticalLine)
      canvasWithVerticalLine.mkString mustEqual     """- - - - - - - - -
                                                      >|   *           |
                                                      >|   *           |
                                                      >|   *           |
                                                      >|   *           |
                                                      >|   *           |
                                                      >- - - - - - - - -""".stripMargin('>')
    }

    "should draw a horizontal line in canvas" >> {
      val canvas = Canvas(5, 7)
      val horizontalLine = Line(start = Point(2, 5), end = Point(4, 5))
      val canvasWithHorizontalLine = canvas.drawLine(horizontalLine)
      canvasWithHorizontalLine.mkString mustEqual     """- - - - - - - - -
                                                        >|               |
                                                        >|               |
                                                        >|               |
                                                        >|               |
                                                        >|   * * *       |
                                                        >- - - - - - - - -""".stripMargin('>')
    }

    "should draw a rectangle in canvas" >> {
      val canvas = Canvas(5, 5)
      val topLine = Line(start = Point(2, 1), end = Point(4, 1))
      val rightLine = Line(start = Point(4, 1), end = Point(4, 5))
      val rectangle = Rectangle(top = topLine, right = rightLine)
      val canvasWithRectangle = canvas.drawRectangle(rectangle)
      canvasWithRectangle.mkString mustEqual     """- - - - - - -
                                                   >|   * * *   |
                                                   >|   *   *   |
                                                   >|   *   *   |
                                                   >|   *   *   |
                                                   >|   * * *   |
                                                   >- - - - - - -""".stripMargin('>')
    }
  }
}
