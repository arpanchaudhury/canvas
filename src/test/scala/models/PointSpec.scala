package models

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class PointSpec extends Specification with Mockito {
  "Point" >> {
    "should be comparable with another point" >> {
      val p1 = Point(2, 5)
      val p2 = Point(2, 7)
      val p3 = Point(3, 5)

      p1 < p2 mustEqual true
      p2 < p1 mustEqual false
      p1 < p3 mustEqual true
      p3 < p1 mustEqual false
      p2 < p3 mustEqual true
      p3 < p2 mustEqual false
    }

    "should respond to is inside canvas queries" >> {
      val p = Point(11, 17)

      p.isInsideCanvas(20, 20) mustEqual true
      p.isInsideCanvas(10, 10) mustEqual false
    }

    "should return all neighbours of itself" >> {
      val p = Point(11, 17)

      p.neighbours mustEqual Set(Point(10, 16), Point(11, 16), Point(12, 16), Point(10, 17),
                                 Point(12, 17), Point(10, 18), Point(11, 18), Point(12, 18))
    }
  }
}
