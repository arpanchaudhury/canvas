package factories

import io.CanvasIO
import services.CanvasService
import utils.CommandParser

class CanvasServiceFactory {
  def getService = {
    val parser = new CommandParser
    val canvasIO = new CanvasIO(parser)
    new CanvasService(canvasIO)
  }
}
