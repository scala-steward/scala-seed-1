import org.scalablytyped.converter.plugin.ScalablyTypedConverterExternalNpmPlugin.autoImport.externalNpm
import org.scalablytyped.converter.plugin.ScalablyTypedPluginBase.autoImport.{ stFlavour, Flavour }
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.{ scalaJSLinkerConfig, scalaJSUseMainModuleInitializer, ModuleKind }
import sbt.Keys.{ exportJars, _ }
import sbt.{ Def, Tests, _ }

import scala.sys.process.Process

object Settings {

  lazy val valueJs: Seq[Def.Setting[_]] = value ++ Seq(
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= (/* disabled because it somehow triggers many warnings */
    _.withSourceMap(false)
      .withModuleKind(ModuleKind.CommonJSModule)),
    externalNpm := {
      Process("yarn", baseDirectory.value).!
      baseDirectory.value
    },
    stFlavour := Flavour.Japgolly
  )

  lazy val value: Seq[Def.Setting[_]] = Seq(
    scalaVersion := "2.13.14",
    scalacOptions := {
      val default = Seq(
        "-deprecation",
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-language:postfixOps",
        "-language:reflectiveCalls",
        "-unchecked",
        //"-Xfatal-warnings",
        "-Xlint"
      )
      if (version.value.endsWith("SNAPSHOT"))
        default :+ "-Xcheckinit"
      else
        default
      // check against early initialization
    },
    javaOptions += "-Duser.timezone=UTC",
    Test / fork                         := false,
    Test / parallelExecution            := false,
    IntegrationTest / fork              := false,
    IntegrationTest / parallelExecution := false,
    Global / cancelable                 := true,
    // OneJar
    exportJars := true
  )

  lazy val testReport: Seq[Def.Setting[_]] = Seq(
    Test / testOptions ++= Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
      Tests.Argument("-oDF")
    )
  )

  lazy val noPublish: Seq[Def.Setting[_]] = Seq(
    publish / skip := true
  )

  ThisBuild / useCoursier := false

}
