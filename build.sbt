val libraries = Seq(
  "org.specs2" %% "specs2-core" % "3.7.2" % "test",
  "org.specs2" %% "specs2-mock" % "3.7.2" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")

val api = project.in(file("."))
  .settings(
    name := "canvas",
    version := "1.0.0",
    scalaVersion := "2.11.8",
    libraryDependencies ++= libraries
  )
