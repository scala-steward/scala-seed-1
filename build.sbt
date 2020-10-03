lazy val commonSettingsJs = Settings.valueJs ++ Information.value

lazy val commonSettings = Settings.value ++ Settings.testReport ++ Information.value

lazy val `scala-seed` = (project in file("."))
  .aggregate(docs)
  .aggregate(module)
  // S E T T I N G S
  .settings(commonSettings)
  .settings(Settings.noPublish)

lazy val docs = (project in file("modules/docs"))
  // S E T T I N G S
  .settings(commonSettings)
  .settings(Settings.noPublish)
  .settings(MdocSettings.value)
  .settings(libraryDependencies ++= Dependencies.docs)
  // P L U G I N S
  .enablePlugins(MdocPlugin)

lazy val module = (project in file("modules/module"))
  // S E T T I N G S
  .settings(commonSettings)
  .settings(libraryDependencies ++= Dependencies.module)