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
  }
}
