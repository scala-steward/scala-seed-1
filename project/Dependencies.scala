import sbt._

/**
 * @author Miguel Villafuerte
 */
object Dependencies {

  val module: Seq[ModuleID] =
  // M O D U L E
    Seq(
    ) ++ Seq(
      // M O D U L E  T E S T
      Artifact.scalaTest % Version.scalaTest
    ).map(_ % Test)

  val docs: Seq[ModuleID] =
  // D O C S
    Seq(
    ) ++ Seq(
      // D O C S  T E S T
      Artifact.scalaTest % Version.scalaTest
    ).map(_ % Test)

  private object Artifact {
    val scalaTest = "org.scalatest" %% "scalatest"
  }

  private object Version {
    val scalaTest = "3.0.8"
  }

}

