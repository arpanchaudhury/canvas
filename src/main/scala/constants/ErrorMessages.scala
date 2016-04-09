package constants

import exceptions.CommandParserException

object ErrorMessages {
  var MultipleCanvasCreation =
    s"""[Error]: Cannot create multiple canvases!
        |Please quit and restart...""".stripMargin

  val CanvasNotCreated =
    s"""[Error]: Canvas not created!
        |Please create your canvas first...""".stripMargin

  def ParseCommand(throwable: CommandParserException) =
    s"""${throwable.message}
       |Ignoring previous malformed command...""".stripMargin

  def Other(throwable: Throwable) =
    s"""${throwable.getMessage}
       |Exiting Canvas Service!""".stripMargin
}
