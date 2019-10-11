import sbt.Keys.{developers, homepage, licenses, organization, scalaVersion}
import sbt.{Def, Developer, url}

/**
 * @author Miguel Villafuerte
 */
object Info {

  val value: Seq[Def.Setting[_]] = Seq(
    organization := "io.github.mvillafuertem",
    scalaVersion := "2.13.1",
    homepage := Some(url("https://github.com/mvillafuertem/scala")),
    licenses := List("MIT" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "mvillafuertem",
        "Miguel Villafuerte",
        "mvillafuertem@email.com",
        url("https://github.com/mvillafuertem")
      )
    )
  )

}
