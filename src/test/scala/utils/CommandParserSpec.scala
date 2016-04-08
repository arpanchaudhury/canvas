package utils

import commands._
import exceptions.CommandParserException
import org.specs2.mutable.Specification


class CommandParserSpec extends Specification {
  "Command Parser" >> {
    "should parse Quit command" >> {
      CommandParser.parse("Q")      mustEqual Quit
      CommandParser.parse("q")      mustEqual Quit
      CommandParser.parse("   Q ")  mustEqual Quit
    }

    "should throw exception for malformed Quit command" >> {
      {CommandParser.parse("a q s")} must throwA[CommandParserException]
    }

    "should parse CreateCanvas command" >> {
      CommandParser.parse("c 12 42")      mustEqual CreateCanvas(12, 42)
      CommandParser.parse("C 42 12")      mustEqual CreateCanvas(42, 12)
      CommandParser.parse("  C 30  18 ")  mustEqual CreateCanvas(30, 18)
    }

    "should throw exception for malformed CanvasCreate command" >> {
      "case 1" >> {
        {CommandParser.parse("c21 34")} must throwA[CommandParserException]
      }

      "case 2" >> {
        {CommandParser.parse("ca 21 34")} must throwA[CommandParserException]
      }

      "case 3" >> {
        {CommandParser.parse("C 21a 34")} must throwA[CommandParserException]
      }
    }

    "should parse DrawLine command" >> {
      CommandParser.parse("L 12 42 21 31")  mustEqual DrawLine(12, 42, 21, 31)
      CommandParser.parse("l 3 1 5 3")      mustEqual DrawLine(3, 1, 5, 3)
      CommandParser.parse("L  2  9  1   5") mustEqual DrawLine(2, 9, 1, 5)
    }

    "should throw exception for malformed DrawLine command" >> {
      "case 1" >> {
        {CommandParser.parse("l 12 42 21")} must throwA[CommandParserException]
      }

      "case 2" >> {
        {CommandParser.parse("La 21 34 32 23")} must throwA[CommandParserException]
      }

      "case 3" >> {
        {CommandParser.parse("L 21a 34 13 31")} must throwA[CommandParserException]
      }
    }

    "should parse DrawRectangle command" >> {
      CommandParser.parse("R 12 42 21 31")  mustEqual DrawRectangle(12, 42, 21, 31)
      CommandParser.parse("r 3 1 5 3")      mustEqual DrawRectangle(3, 1, 5, 3)
      CommandParser.parse("R  2  9  1   5") mustEqual DrawRectangle(2, 9, 1, 5)
    }

    "should throw exception for malformed DrawRectangle command" >> {
      "case 1" >> {
        {CommandParser.parse("r 12 42 21")} must throwA[CommandParserException]
      }

      "case 2" >> {
        {CommandParser.parse("Ra 21 34 32 23")} must throwA[CommandParserException]
      }

      "case 3" >> {
        {CommandParser.parse("R 21a 34 13 31")} must throwA[CommandParserException]
      }
    }

    "should parse BucketFill command" >> {
      CommandParser.parse("B 12 42 *")     mustEqual BucketFill(12, 42, '*')
      CommandParser.parse("b 3 1 #")       mustEqual BucketFill(3, 1, '#')
      CommandParser.parse(" B  2  9  ^  ") mustEqual BucketFill(2, 9, '^')
    }

    "should throw exception for malformed BucketFill command" >> {
      "case 1" >> {
        {CommandParser.parse("b 12 42")} must throwA[CommandParserException]
      }

      "case 2" >> {
        {CommandParser.parse("Ba 21 34 *")} must throwA[CommandParserException]
      }

      "case 3" >> {
        {CommandParser.parse("B 21 34 *a")} must throwA[CommandParserException]
      }
    }
  }
}
