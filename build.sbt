lazy val commonSettings = Settings.value ++ Info.value

lazy val `sbt-autoversion-project` = (project in file("."))
  .aggregate(docs)
  .aggregate(module)
  // S E T T I N G S
  .settings(commonSettings)
  .settings(Settings.noPublish)

lazy val docs = (project in file("docs"))
  // S E T T I N G S
  .settings(commonSettings)
  .settings(Settings.noPublish)
  .settings(Settings.noAssemblyTest)
  .settings(MdocSettings.value)
  .settings(libraryDependencies ++= Dependencies.docs)
  // P L U G I N S
  .enablePlugins(MdocPlugin)

lazy val module = (project in file("module"))
  // S E T T I N G S
  .settings(commonSettings)
  .settings(libraryDependencies ++= Dependencies.module)