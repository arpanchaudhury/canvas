package utils

import models._
import org.specs2.mutable.Specification

class CanvasPainterSpec extends Specification {

  "Canvas Painter" >> {
    val canvasPainter = new CanvasPainter

    "should draw translated entities inside canvas" >> {
      val canvas = Canvas(20, 20)
      val verticalLine = Line(Point(3, 2), Point(3, 9))
      val horizontalLine = Line(Point(2, 9), Point(5, 9))
      val rectangle = Rectangle(Line(Point(1, 1), Point(11, 1)), Line(Point(11, 1), Point(11, 15)))
      val otherRectangle = Rectangle(Line(Point(5, 7), Point(8, 7)), Line(Point(8, 7), Point(8, 9)))
      val areaPaint = AreaPaint(Point(5, 5), '@')

      val entities = List(canvas, verticalLine, horizontalLine, rectangle, otherRectangle, areaPaint)

      canvasPainter.paint(entities).mkString mustEqual """- - - - - - - - - - - - - - - - - - - - - -
                                                         >| * * * * * * * * * * *                   |
                                                         >| *   * @ @ @ @ @ @ @ *                   |
                                                         >| *   * @ @ @ @ @ @ @ *                   |
                                                         >| *   * @ @ @ @ @ @ @ *                   |
                                                         >| *   * @ @ @ @ @ @ @ *                   |
                                                         >| *   * @ @ @ @ @ @ @ *                   |
                                                         >| *   * @ * * * * @ @ *                   |
                                                         >| *   * @ *     * @ @ *                   |
                                                         >| * * * * * * * * @ @ *                   |
                                                         >| * @ @ @ @ @ @ @ @ @ *                   |
                                                         >| * @ @ @ @ @ @ @ @ @ *                   |
                                                         >| * @ @ @ @ @ @ @ @ @ *                   |
                                                         >| * @ @ @ @ @ @ @ @ @ *                   |
                                                         >| * @ @ @ @ @ @ @ @ @ *                   |
                                                         >| * * * * * * * * * * *                   |
                                                         >|                                         |
                                                         >|                                         |
                                                         >|                                         |
                                                         >|                                         |
                                                         >|                                         |
                                                         >- - - - - - - - - - - - - - - - - - - - - -""".stripMargin('>')
    }
  }
}
