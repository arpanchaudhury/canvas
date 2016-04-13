package constants

import factories.CanvasServiceFactory
import models.{Canvas, Line, Point}

object UserMessages {
  val Welcome =
    """
      |Welcome to Canvas Biz! Create your own canvas with command-line...
      | """.stripMargin

  val CanvasLogo = (new CanvasServiceFactory).getService.
                      drawCanvas(List(Canvas(10, 35),
                                 Line(Point( 3, 4), Point( 6, 4)),
                                 Line(Point( 3, 4), Point( 3, 8)),
                                 Line(Point( 3, 8), Point( 6, 8)),
                                 Line(Point( 8, 4), Point( 8, 8)),
                                 Line(Point( 8, 4), Point(11, 4)),
                                 Line(Point(11, 4), Point(11, 8)),
                                 Line(Point( 8, 6), Point(11, 6)),
                                 Line(Point(13, 4), Point(17, 4)),
                                 Line(Point(14, 4), Point(14, 8)),
                                 Line(Point(17, 4), Point(17, 8)),
                                 Line(Point(19, 4), Point(19, 8)),
                                 Line(Point(19, 8), Point(22, 8)),
                                 Line(Point(22, 4), Point(22, 8)),
                                 Line(Point(24, 4), Point(24, 8)),
                                 Line(Point(24, 4), Point(27, 4)),
                                 Line(Point(27, 4), Point(27, 8)),
                                 Line(Point(24, 6), Point(27, 6)),
                                 Line(Point(29, 4), Point(32, 4)),
                                 Line(Point(29, 4), Point(29, 6)),
                                 Line(Point(29, 6), Point(32, 6)),
                                 Line(Point(32, 6), Point(32, 8)),
                                 Line(Point(29, 8), Point(32, 8)))).mkString
  val GoodBye =
    """
      |Have a nice Day...""".stripMargin
}
