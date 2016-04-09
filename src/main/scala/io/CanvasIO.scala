package io

import commands.Command
import utils.CommandParser

import scala.util.Try

class CanvasIO(commandParser: CommandParser) {
  def getCommand: Try[Command] = Try {
    print("enter command: ")
    commandParser.parse(scala.io.StdIn.readLine())
  }
}
