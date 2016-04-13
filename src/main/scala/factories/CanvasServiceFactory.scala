package factories

import io.CanvasIO
import services.CanvasService
import utils.{CanvasPainter, CommandParser, CommandTranslator, CommandValidator}

class CanvasServiceFactory {
  def getService = {
    val parser = new CommandParser
    val validator = new CommandValidator
    val canvasIO = new CanvasIO(parser, validator)
    val commandTranslator = new CommandTranslator
    val canvasPainter = new CanvasPainter
    new CanvasService(canvasIO, commandTranslator, canvasPainter)
  }
}
