lazy val commonSettingsJs = Settings.valueJs ++ Information.value

lazy val commonSettings = Settings.value ++ Settings.testReport ++ Information.value

lazy val `scala-seed` = (project in file("."))
  .aggregate(docs)
  .aggregate(ui)
  .aggregate(server)
  // S E T T I N G S
  .settings(commonSettings)
  .settings(Settings.noPublish)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("modules/core"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= Dependencies.core)

lazy val docs = (project in file("modules/docs"))
  // S E T T I N G S
  .settings(commonSettings)
  .settings(Settings.noPublish)
  .settings(MdocSettings.value)
  .settings(libraryDependencies ++= Dependencies.docs)
  // P L U G I N S
  .enablePlugins(MdocPlugin)

lazy val ui = (project in file("modules/ui"))
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  // S E T T I N G S
  .settings(commonSettingsJs)
  .settings(libraryDependencies ++= Dependencies.ui)
  .dependsOn(core.js)

lazy val server = (project in file("modules/server"))
  // S E T T I N G S
  .settings(commonSettings)
  .settings(libraryDependencies ++= Dependencies.module)
  .dependsOn(core.jvm)
