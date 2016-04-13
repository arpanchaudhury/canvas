package utils

import commands._
import exceptions.InvalidCommandException
import org.specs2.mutable.Specification

class CommandValidatorSpec extends Specification {

  "Command Validator" >> {
    val commandValidator = new CommandValidator

    "should return the command if line is horizontal or vertical and fits inside created canvas" >> {
      val drawVerticalLine    = DrawLine(10, 20, 10, 30)
      val drawHorizontalLine  = DrawLine(10, 20, 40, 20)
      commandValidator.validate(drawVerticalLine, 100, 100)    mustEqual drawVerticalLine
      commandValidator.validate(drawHorizontalLine, 100, 100)  mustEqual drawHorizontalLine
    }

    "should raise InvalidCommand exception if line is not horizontal or vertical" >> {
      val drawLine = DrawLine(10, 15, 40, 20)
      commandValidator.validate(drawLine, 100, 100) must throwA[InvalidCommandException]
    }

    "should raise InvalidCommand exception if line does not fit inside created canvas" >> {
      val drawLine = DrawLine(0, 20, 140, 20)
      commandValidator.validate(drawLine, 100, 100) must throwA[InvalidCommandException]
    }

    "should return the command if rectangle fits in created canvas" >> {
      val drawRectangle = DrawRectangle(20, 50, 50, 20)
      commandValidator.validate(drawRectangle, 100, 100) mustEqual drawRectangle
    }

    "should raise InvalidCommand exception if rectangle does not fit inside created canvas" >> {
      val drawRectangle = DrawRectangle(0, 200, 200, 0)
      commandValidator.validate(drawRectangle, 100, 100) must throwA[InvalidCommandException]
    }

    "should return the command if bucket fill input point is inside the canvas" >> {
      val bucketFill = BucketFill(10, 30, '*')
      commandValidator.validate(bucketFill, 100, 100) mustEqual bucketFill
    }

    "should raise InvalidCommand exception if bucket fill input point is outside the canvas" >> {
      val bucketFill = BucketFill(150, 15, '*')
      commandValidator.validate(bucketFill, 100, 100) must throwA[InvalidCommandException]
    }

    "should return the other commands as-is" >> {
      val quit = Quit
      val createCanvas = CreateCanvas(200, 200)
      commandValidator.validate(quit, 100, 100)         mustEqual quit
      commandValidator.validate(createCanvas, 100, 100) mustEqual createCanvas
    }
  }
}
