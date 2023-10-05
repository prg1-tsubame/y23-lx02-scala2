import sbt._
import Keys._

object Programming1 {
  val COMMON = Seq(
      ThisBuild / version      := "0.1.0",
      ThisBuild / organization := "jp.ac.titech.is.prg1",

      Test / fork         := true,
      Test / connectInput := true,
      Test / logBuffered  := true,

      run / fork          := true,
      run / connectInput  := true,
      Global / cancelable := true,
    )

  val SCALA3 = Seq(
      ThisBuild / scalaVersion := "3.2.2",                   // scalac コンパイラのバージョン
      Compile / scalaSource := baseDirectory.value / "src",  // Scala のソース置き場のディレクトリ
      //resourceDirectory := baseDirectory.value / "resources",
      scalacOptions := Seq(
        "-explain",
        //"-deprecate",
        "-Werror",                                         // 警告をエラーとして扱う
        // "-Xlint",
        // -Wunused, -Yrangepos は Scala3 では使わないこと。詳しくは [The essential Scala build tool tutorial](https://www.scalawilliam.com/essential-sbt/) を参照のこと
      ),
    )

  val SCALA2 = Seq(
      ThisBuild / scalaVersion := "2.13.12",                   // scalac コンパイラのバージョン
      Compile / scalaSource := baseDirectory.value / "src",  // Scala のソース置き場のディレクトリ
      scalacOptions := Seq(
        "-feature",
        "-unchecked",
        "-deprecation",
      ),
  )

  val SCALA_TEST = Seq(
      Test / scalaSource := baseDirectory.value / "test",

      // 以下は [ScalaTest についての設定](https://www.scalatest.org/install)
      libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",

      // test configuration のみに scalatest を読み込む（ライブラリ依存性 / マネージ依存性）
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    )

  val SCALA_FX = Seq(
      // ScalaFX の設定についてはさんざ苦労した。詳しくは Zettelkasten を参照のこと。
      // https://mvnrepository.com/artifact/org.scalafx/scalafx
      libraryDependencies += "org.scalafx" %% "scalafx" % "19.0.0-R30",
      // https://mvnrepository.com/artifact/org.scalafx/scalafx-extras
      libraryDependencies += "org.scalafx" %% "scalafx-extras" % "0.7.0",
    )

  val Scala3 = COMMON ++ SCALA3 ++ SCALA_TEST
  val Scala3FX = SCALA3 ++ SCALA_FX

  val Scala2 = COMMON ++ SCALA2

  val JAVA = Seq(
    javacOptions ++= Seq("-encoding", "UTF-8"),
    Compile / javaSource := baseDirectory.value / "java"
    )

  val Java = COMMON ++ SCALA3 ++ JAVA
}
