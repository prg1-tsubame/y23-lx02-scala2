import Programming1._

lazy val support = (project in file("support")).settings(Java)
lazy val lx02 = (project in file(".")).dependsOn(support).settings(Scala2)