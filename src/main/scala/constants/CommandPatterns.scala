package constants

object CommandPatterns {
  val Quit = "(?i)q".r
  val Undo = "(?i)u".r
  val CreateCanvas = "(?i)c\\s+(\\d+)\\s+(\\d+)".r
  val DrawLine = "(?i)l\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)".r
  val DrawRectangle = "(?i)r\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)".r
  val BucketFill = "(?i)b\\s+(\\d+)\\s+(\\d+)\\s+(.)".r
}
