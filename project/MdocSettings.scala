import sbt.Keys.{ baseDirectory, sbtVersion, scalaVersion, version }
import sbt._
import mdoc.MdocPlugin.autoImport._

/**
 * @author Miguel Villafuerte
 */
object MdocSettings {

  val value: Seq[Def.Setting[_]] = Seq(
    mdocIn := baseDirectory.value / "src/main/mdoc/",
    mdocOut := file("."),
    mdocVariables := Map(
      "PROJECT_NAME"  -> "Scala Seed",
      "VERSION"       -> version.value,
      "SCALA_VERSION" -> scalaVersion.value,
      "JAVA_VERSION" -> System.getProperty("java.version"),
      "SBT_VERSION"   -> sbtVersion.value
    )
  )

}
