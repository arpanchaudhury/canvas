package utils

import commands._
import constants.CommandPatterns
import exceptions.CommandParserException

object CommandParser {
  def parse(line: String): Command = line.trim match {
    case CommandPatterns.Quit()                         => Quit
    case CommandPatterns.CreateCanvas(height, width)    => CreateCanvas(height.toInt, width.toInt)
    case CommandPatterns.DrawLine(x1, y1, x2, y2)       => DrawLine(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
    case CommandPatterns.DrawRectangle(x1, y1, x2, y2)  => DrawRectangle(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
    case CommandPatterns.BucketFill(x, y, c)            => BucketFill(x.toInt, y.toInt, c.head)
    case whatever                                       => throw CommandParserException("[ERROR]: Command cannot be parsed!")
  }
}
