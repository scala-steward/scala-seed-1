addSbtPlugin("com.eed3si9n"       % "sbt-assembly"             % "0.15.0")
addSbtPlugin("com.eed3si9n"       % "sbt-buildinfo"            % "0.10.0")
addSbtPlugin("com.lightbend.sbt"  % "sbt-javaagent"            % "0.1.6")
addSbtPlugin("com.typesafe.sbt"   % "sbt-git"                  % "1.0.0")
addSbtPlugin("com.typesafe.sbt"   % "sbt-native-packager"      % "1.8.1")
addSbtPlugin("io.spray"           % "sbt-revolver"             % "0.9.1")
addSbtPlugin("net.virtual-void"   % "sbt-dependency-graph"     % "0.9.2")
addSbtPlugin("org.jmotor.sbt"     % "sbt-dependency-updates"   % "1.2.2")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "1.5.1")
addSbtPlugin("org.scalameta"      % "sbt-mdoc"                 % "2.2.19")
addSbtPlugin("org.scalameta"      % "sbt-scalafmt"             % "2.4.2")
addSbtPlugin("org.scoverage"      % "sbt-scoverage"            % "1.6.1")

resolvers += Resolver.bintrayRepo("oyvindberg", "converter")
addSbtPlugin("org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta30")
