import sbt._

/**
 * @author Miguel Villafuerte
 */
object Dependencies {

  val core: Seq[ModuleID] =
  // C O R E
    Seq(
      Artifact.zio % Version.zio
    ) ++ Seq(
      // C O R E  T E S T
      Artifact.zioTest    % Version.zio,
      Artifact.zioTestSbt % Version.zio,
      Artifact.scalaTest  % Version.scalaTest
    ).map(_        % Test)

  val ui: Seq[ModuleID] =
    Seq()

  val module: Seq[ModuleID] =
    Seq()

  val docs: Seq[ModuleID]   =
    // D O C S
    Seq(
    ) ++ Seq(
      // D O C S  T E S T
      Artifact.scalaTest % Version.scalaTest
    ).map(_ % Test)

  private object Artifact {
    val scalaTest  = "org.scalatest" %% "scalatest"
    val zio        = "dev.zio"       %% "zio"
    val zioTest    = "dev.zio"       %% "zio-test"
    val zioTestSbt = "dev.zio"       %% "zio-test-sbt"
  }

  private object Version {
    val scalaTest = "3.2.6"
    val zio = "1.0.4-2"
  }

}
