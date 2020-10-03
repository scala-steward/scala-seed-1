import sbt.Keys.{developers, homepage, licenses, organization, scalaVersion, scmInfo}
import sbt.{Def, Developer, ScmInfo, url}

/**
 * @author Miguel Villafuerte
 */
object Information {

  val value: Seq[Def.Setting[_]] = Seq(
    organization := "io.github.mvillafuertem",
    homepage := Some(url("https://github.com/mvillafuertem/scala")),
    licenses := List("MIT" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "mvillafuertem",
        "Miguel Villafuerte",
        "mvillafuertem@email.com",
        url("https://github.com/mvillafuertem")
      )
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/mvillafuertem/scala"),
        "scm:git@github.com:mvillafuertem/scala.git"
      )
    )
  )

}
