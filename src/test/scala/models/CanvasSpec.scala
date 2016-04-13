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

    "should paint a covered area in canvas" >> {
      val canvas = Canvas(20, 20)
      val rectangleOne = Rectangle(Line(Point(5, 1), Point(20, 1)), Line(Point(20, 1), Point(20, 10)))
      val rectangleTwo = Rectangle(Line(Point(1, 6), Point(10, 6)), Line(Point(10, 6), Point(10, 17)))
      val areaPaint = AreaPaint(Point(3, 12), '#')
      val canvasWithRectangle = canvas.drawRectangle(rectangleOne)
      val canvasWithRectangles = canvasWithRectangle.drawRectangle(rectangleTwo)
      val canvasWithAreaPainted = canvasWithRectangles.paintArea(areaPaint)

      canvasWithAreaPainted.mkString mustEqual    """- - - - - - - - - - - - - - - - - - - - - -
                                                    >|         * * * * * * * * * * * * * * * * |
                                                    >|         *                             * |
                                                    >|         *                             * |
                                                    >|         *                             * |
                                                    >|         *                             * |
                                                    >| * * * * * * * * * *                   * |
                                                    >| * # # # *         *                   * |
                                                    >| * # # # *         *                   * |
                                                    >| * # # # *         *                   * |
                                                    >| * # # # * * * * * * * * * * * * * * * * |
                                                    >| * # # # # # # # # *                     |
                                                    >| * # # # # # # # # *                     |
                                                    >| * # # # # # # # # *                     |
                                                    >| * # # # # # # # # *                     |
                                                    >| * # # # # # # # # *                     |
                                                    >| * # # # # # # # # *                     |
                                                    >| * * * * * * * * * *                     |
                                                    >|                                         |
                                                    >|                                         |
                                                    >|                                         |
                                                    >- - - - - - - - - - - - - - - - - - - - - -""".stripMargin('>')
    }
  }
}
