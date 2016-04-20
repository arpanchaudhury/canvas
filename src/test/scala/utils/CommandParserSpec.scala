package utils

import commands._
import exceptions.CommandParseException
import org.specs2.mutable.Specification

class CommandParserSpec extends Specification {
  "Command Parser" >> {
    val commandParser = new CommandParser

    "should parse Quit command" >> {
      commandParser.parse("Q")      mustEqual Quit
      commandParser.parse("q")      mustEqual Quit
      commandParser.parse("   Q ")  mustEqual Quit
    }

    "should throw exception for malformed Quit command" >> {
      commandParser.parse("a q s") must throwA[CommandParseException]
    }

    "should parse CreateCanvas command" >> {
      commandParser.parse("c 12 42")      mustEqual CreateCanvas(12, 42)
      commandParser.parse("C 42 12")      mustEqual CreateCanvas(42, 12)
      commandParser.parse("  C 30  18 ")  mustEqual CreateCanvas(30, 18)
    }

    "should throw exception for malformed CanvasCreate command" >> {
      "case 1" >> {
        commandParser.parse("c21 34") must throwA[CommandParseException]
      }

      "case 2" >> {
        commandParser.parse("ca 21 34") must throwA[CommandParseException]
      }

      "case 3" >> {
        commandParser.parse("C 21a 34") must throwA[CommandParseException]
      }
    }

    "should parse DrawLine command" >> {
      commandParser.parse("L 12 42 21 31")  mustEqual DrawLine(12, 42, 21, 31)
      commandParser.parse("l 3 1 5 3")      mustEqual DrawLine(3, 1, 5, 3)
      commandParser.parse("L  2  9  1   5") mustEqual DrawLine(2, 9, 1, 5)
    }

    "should throw exception for malformed DrawLine command" >> {
      "case 1" >> {
        commandParser.parse("l 12 42 21") must throwA[CommandParseException]
      }

      "case 2" >> {
        commandParser.parse("La 21 34 32 23") must throwA[CommandParseException]
      }

      "case 3" >> {
        commandParser.parse("L 21a 34 13 31") must throwA[CommandParseException]
      }
    }

    "should parse DrawRectangle command" >> {
      commandParser.parse("R 12 42 21 31")  mustEqual DrawRectangle(12, 42, 21, 31)
      commandParser.parse("r 3 1 5 3")      mustEqual DrawRectangle(3, 1, 5, 3)
      commandParser.parse("R  2  9  1   5") mustEqual DrawRectangle(2, 9, 1, 5)
    }

    "should throw exception for malformed DrawRectangle command" >> {
      "case 1" >> {
        commandParser.parse("r 12 42 21") must throwA[CommandParseException]
      }

      "case 2" >> {
        commandParser.parse("Ra 21 34 32 23") must throwA[CommandParseException]
      }

      "case 3" >> {
        commandParser.parse("R 21a 34 13 31") must throwA[CommandParseException]
      }
    }

    "should parse BucketFill command" >> {
      commandParser.parse("B 12 42 *")     mustEqual BucketFill(12, 42, '*')
      commandParser.parse("b 3 1 #")       mustEqual BucketFill(3, 1, '#')
      commandParser.parse(" B  2  9  ^  ") mustEqual BucketFill(2, 9, '^')
    }

    "should throw exception for malformed BucketFill command" >> {
      "case 1" >> {
        commandParser.parse("b 12 42") must throwA[CommandParseException]
      }

      "case 2" >> {
        commandParser.parse("Ba 21 34 *") must throwA[CommandParseException]
      }

      "case 3" >> {
        commandParser.parse("B 21 34 *a") must throwA[CommandParseException]
      }
    }

    "should parse Undo command" >> {
      commandParser.parse("U")      mustEqual Undo
      commandParser.parse("u")      mustEqual Undo
      commandParser.parse("   U ")  mustEqual Undo
    }

    "should throw exception for malformed Undo command" >> {
      commandParser.parse("a u s") must throwA[CommandParseException]
    }
  }
}
