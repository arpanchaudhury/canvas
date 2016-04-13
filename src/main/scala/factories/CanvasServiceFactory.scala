package factories

import io.CanvasIO
import services.CanvasService
import utils.{CommandParser, CommandTranslator, CommandValidator}

class CanvasServiceFactory {
  def getService = {
    val parser = new CommandParser
    val validator = new CommandValidator
    val canvasIO = new CanvasIO(parser, validator)
    val commandTranslator = new CommandTranslator
    new CanvasService(canvasIO, commandTranslator)
  }
}
