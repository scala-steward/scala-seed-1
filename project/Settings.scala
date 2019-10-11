import sbt.Keys.{exportJars, version, _}
import sbt.{Def, Tests, _}
import sbtassembly.AssemblyKeys.assembly

/**
 * @author Miguel Villafuerte
 */
object Settings {

  val value: Seq[Def.Setting[_]] = Seq(
    scalacOptions := {
      val default = Seq(
        "-deprecation",
        "-feature",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-language:postfixOps",
        "-language:reflectiveCalls",
        "-unchecked",
        // "-Xfatal-warnings",
        "-Xlint"
      )
      if (version.value.endsWith("SNAPSHOT")) {
        default :+ "-Xcheckinit"
      } else {
        default
      } // check against early initialization
    },

    javaOptions += "-Duser.timezone=UTC",

    fork in Test := false,

    parallelExecution in Test := false,

    testOptions in Test ++= Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
      Tests.Argument("-oDF")
    ),

    cancelable in Global := true,
    // OneJar
    exportJars := true,

    ThisBuild / useCoursier := false

  )

  val noPublish: Seq[Def.Setting[_]] = Seq(
    publish / skip := true,
    publishArtifact := false
  )

  val noAssemblyTest: Seq[Def.Setting[_]] = Seq(
    assembly / test := {}
  )

}
