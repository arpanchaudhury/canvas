package utils

import commands._
import models._
import org.specs2.mutable.Specification

class CommandTranslatorSpec extends Specification {
  "Command Translator" >> {
    val commandTranslator = new CommandTranslator

    "should translate commands into entities" >> {

      val drawCanvas = CreateCanvas(20, 20)
      val drawVerticalLine = DrawLine(3, 3, 3, 9)
      val drawHorizontalLine = DrawLine(5, 9, 2, 9)
      val drawRectangle = DrawRectangle(1, 1, 11, 15)
      val drawOtherRectangle = DrawRectangle(5, 7, 8, 9)
      val bucketFillOne = BucketFill(2, 2, '#')
      val bucketFillTwo = BucketFill(4, 5, '*')
      val bucketFillThree = BucketFill(1, 1, '@')
      val undo = Undo
      val quit = Quit

      val commands = List(drawCanvas, drawVerticalLine, drawHorizontalLine, drawRectangle,
        drawOtherRectangle, undo, bucketFillOne, bucketFillTwo, bucketFillThree, undo, undo, quit)

      val canvas = Canvas(20, 20)
      val verticalLine = Line(Point(3, 3), Point(3, 9))
      val horizontalLine = Line(Point(2, 9), Point(5, 9))
      val rectangle = Rectangle(Line(Point(1, 1), Point(11, 1)), Line(Point(11, 1), Point(11, 15)))
      val areaPaint = AreaPaint(Point(2, 2), '#')

      val entities = List(canvas, verticalLine, horizontalLine, rectangle, areaPaint)

      commandTranslator.translate(commands) mustEqual entities
    }
  }
}
