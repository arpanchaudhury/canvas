package constants

import exceptions.{CommandParseException, InvalidCommandException}

object ErrorMessages {
  var MultipleCanvasCreation =
    s"""[Error]: Cannot create multiple canvases!
        |   Please quit and restart...""".stripMargin

  val CanvasNotCreated =
    s"""[Error]: Canvas not created!
        |   Please create your canvas first...""".stripMargin

  def ParseCommand(throwable: CommandParseException) =
    s"""[Error]: ${throwable.message}
       |    Ignoring previous malformed command...""".stripMargin

  def InvalidCommand(throwable: InvalidCommandException): Any =
    s"""[Error]: ${throwable.message}
       |    Ignoring previous invalid command...""".stripMargin

  def Other(throwable: Throwable) =
    s"""[Error]: ${throwable.getMessage}
       |    Exiting Canvas Service!""".stripMargin
}
