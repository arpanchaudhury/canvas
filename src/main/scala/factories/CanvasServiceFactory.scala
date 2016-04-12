package factories

import io.CanvasIO
import services.CanvasService
import utils.{CommandParser, CommandValidator}

class CanvasServiceFactory {
  def getService = {
    val parser = new CommandParser
    val validator = new CommandValidator
    val canvasIO = new CanvasIO(parser, validator)
    new CanvasService(canvasIO)
  }
}
