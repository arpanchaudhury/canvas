package services

import commands._
import exceptions.{CommandParseException, InvalidCommandException}
import io.CanvasIO
import models._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import scala.util.{Failure, Success}

class CanvasServiceSpec extends Specification with Mockito {
  "Canvas Service" >> {

    "should collect commands in proper order" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand(any) returns(Success(CreateCanvas(1, 1)), Success(DrawLine(1, 1, 1, 1)),
        Success(BucketFill(1, 1, '#')), Success(DrawRectangle(1, 1, 1, 1)), Success(Quit))

      canvasService.commandLoop() mustEqual
        List(CreateCanvas(1, 1), DrawLine(1, 1, 1, 1), BucketFill(1, 1, '#'), DrawRectangle(1, 1, 1, 1), Quit)
    }

    "should ignore commands till canvas is created" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand(any) returns(Success(DrawRectangle(1, 1, 1, 1)), Success(CreateCanvas(1, 1)), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should not allow to create multiple canvases" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand(any) returns(Success(CreateCanvas(1, 1)), Success(CreateCanvas(2, 2)), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should ignore all commands after quit" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand(any) returns(Success(Quit), Success(CreateCanvas(1, 1)), Success(DrawRectangle(1, 1, 1, 1)))

      canvasService.commandLoop() mustEqual List(Quit)
    }

    "should ignore malformed commands" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand(any) returns(Failure(CommandParseException("Parse Error")), Success(CreateCanvas(1, 1)),
        Failure(CommandParseException("Parse Error")), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should ignore invalid commands" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand(any) returns(Failure(InvalidCommandException("Invalid Command Error")), Success(CreateCanvas(1, 1)),
        Failure(InvalidCommandException("Invalid Command Error")), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should translate commands into entities" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)

      val drawCanvas = CreateCanvas(20, 20)
      val drawVerticalLine = DrawLine(3, 3, 3, 9)
      val drawHorizontalLine = DrawLine(5, 9, 2, 9)
      val drawRectangle = DrawRectangle(1, 1, 11, 15)
      val drawOtherRectangle = DrawRectangle(5, 7, 8, 9)
      val bucketFill = BucketFill(2, 2, '#')
      val quit = Quit

      val commands = List(drawCanvas, drawVerticalLine, drawHorizontalLine, drawRectangle, drawOtherRectangle, bucketFill, quit)

      val canvas = Canvas(20, 20)
      val verticalLine = Line(Point(3, 3), Point(3, 9))
      val horizontalLine = Line(Point(2, 9), Point(5, 9))
      val rectangle = Rectangle(Line(Point(1, 1), Point(11, 1)), Line(Point(11, 1), Point(11, 15)))
      val otherRectangle = Rectangle(Line(Point(5, 7), Point(8, 7)), Line(Point(8, 7), Point(8, 9)))
      val areaPaint = AreaPaint(Point(2, 2), '#')

      val entities = List(canvas, verticalLine, horizontalLine, rectangle, otherRectangle, areaPaint)

      canvasService.translate(commands) mustEqual entities
    }

    "should draw translated entities inside canvas" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)

      val canvas = Canvas(20, 20)
      val verticalLine = Line(Point(3, 2), Point(3, 9))
      val horizontalLine = Line(Point(2, 9), Point(5, 9))
      val rectangle = Rectangle(Line(Point(1, 1), Point(11, 1)), Line(Point(11, 1), Point(11, 15)))
      val otherRectangle = Rectangle(Line(Point(5, 7), Point(8, 7)), Line(Point(8, 7), Point(8, 9)))
      val areaPaint = AreaPaint(Point(5, 5), '@')

      val entities = List(canvas, verticalLine, horizontalLine, rectangle, otherRectangle, areaPaint)

      canvasService.drawCanvas(entities).mkString mustEqual """- - - - - - - - - - - - - - - - - - - - - -
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
