package services

import commands._
import exceptions.{CommandParseException, InvalidCommandException}
import io.CanvasIO
import models._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import utils.{CanvasPainter, CommandTranslator}

import scala.util.{Failure, Success}

class CanvasServiceSpec extends Specification with Mockito {
  "Canvas Service" >> {

    "should collect commands in proper order" >> {
      val canvasIO = mock[CanvasIO]
      val commandTranslator = mock[CommandTranslator]
      val canvasPainter = mock[CanvasPainter]
      val canvasService = new CanvasService(canvasIO, commandTranslator, canvasPainter)
      canvasIO.getCommand(any) returns(Success(CreateCanvas(1, 1)), Success(DrawLine(1, 1, 1, 1)),
        Success(BucketFill(1, 1, '#')), Success(DrawRectangle(1, 1, 1, 1)), Success(Quit))

      canvasService.commandLoop() mustEqual
        List(CreateCanvas(1, 1), DrawLine(1, 1, 1, 1), BucketFill(1, 1, '#'), DrawRectangle(1, 1, 1, 1), Quit)
    }

    "should ignore commands till canvas is created" >> {
      val canvasIO = mock[CanvasIO]
      val commandTranslator = mock[CommandTranslator]
      val canvasPainter = mock[CanvasPainter]
      val canvasService = new CanvasService(canvasIO, commandTranslator, canvasPainter)
      canvasIO.getCommand(any) returns(Success(DrawRectangle(1, 1, 1, 1)), Success(CreateCanvas(1, 1)), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should not allow to create multiple canvases" >> {
      val canvasIO = mock[CanvasIO]
      val commandTranslator = mock[CommandTranslator]
      val canvasPainter = mock[CanvasPainter]
      val canvasService = new CanvasService(canvasIO, commandTranslator, canvasPainter)
      canvasIO.getCommand(any) returns(Success(CreateCanvas(1, 1)), Success(CreateCanvas(2, 2)), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should ignore all commands after quit" >> {
      val canvasIO = mock[CanvasIO]
      val commandTranslator = mock[CommandTranslator]
      val canvasPainter = mock[CanvasPainter]
      val canvasService = new CanvasService(canvasIO, commandTranslator, canvasPainter)
      canvasIO.getCommand(any) returns(Success(Quit), Success(CreateCanvas(1, 1)), Success(DrawRectangle(1, 1, 1, 1)))

      canvasService.commandLoop() mustEqual List(Quit)
    }

    "should ignore malformed commands" >> {
      val canvasIO = mock[CanvasIO]
      val commandTranslator = mock[CommandTranslator]
      val canvasPainter = mock[CanvasPainter]
      val canvasService = new CanvasService(canvasIO, commandTranslator, canvasPainter)
      canvasIO.getCommand(any) returns(Failure(CommandParseException("Parse Error")), Success(CreateCanvas(1, 1)),
        Failure(CommandParseException("Parse Error")), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should ignore invalid commands" >> {
      val canvasIO = mock[CanvasIO]
      val commandTranslator = mock[CommandTranslator]
      val canvasPainter = mock[CanvasPainter]
      val canvasService = new CanvasService(canvasIO, commandTranslator, canvasPainter)
      canvasIO.getCommand(any) returns(Failure(InvalidCommandException("Invalid Command Error")), Success(CreateCanvas(1, 1)),
        Failure(InvalidCommandException("Invalid Command Error")), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }
  }
}
