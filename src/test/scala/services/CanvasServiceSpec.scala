package services

import commands._
import exceptions.CommandParserException
import io.CanvasIO
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import scala.util.{Failure, Success}


class CanvasServiceSpec extends Specification with Mockito {
  "Canvas Service" >> {

    "should collect commands in proper order" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand returns(Success(CreateCanvas(1, 1)), Success(DrawLine(1, 1, 1, 1)),
        Success(BucketFill(1, 1, '#')), Success(DrawRectangle(1, 1, 1, 1)), Success(Quit))

      canvasService.commandLoop() mustEqual
        List(CreateCanvas(1, 1), DrawLine(1, 1, 1, 1), BucketFill(1, 1, '#'), DrawRectangle(1, 1, 1, 1), Quit)
    }

    "should ignore commands till canvas is created" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand returns(Success(DrawRectangle(1, 1, 1, 1)), Success(CreateCanvas(1, 1)), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should not allow to create multiple canvases" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand returns(Success(CreateCanvas(1, 1)), Success(CreateCanvas(2, 2)), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }

    "should ignore all commands after quit" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand returns(Success(Quit), Success(CreateCanvas(1, 1)), Success(DrawRectangle(1, 1, 1, 1)))

      canvasService.commandLoop() mustEqual List(Quit)
    }

    "should ignore malformed commands" >> {
      val canvasIO = mock[CanvasIO]
      val canvasService = new CanvasService(canvasIO)
      canvasIO.getCommand returns(Failure(CommandParserException("Parse Error")), Success(CreateCanvas(1, 1)),
        Failure(CommandParserException("Parse Error")), Success(Quit))

      canvasService.commandLoop() mustEqual List(CreateCanvas(1, 1), Quit)
    }
  }
}
