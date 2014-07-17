name := "NodeCMS"

version := "0.0.0.ALHPA"

scalaVersion := "2.11.1"

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.4"
)

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "3.0",
  "org.springframework" % "spring-context" % "4.0.6.RELEASE"
)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.3.0",
  "org.scalatra" %% "scalatra-scalate" % "2.3.0",
  "org.scala-lang" % "scala-compiler" % "2.11.1"
)

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "2.1.0-RC1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.mchange" % "c3p0" % "0.9.2.1",
  "org.postgresql" % "postgresql" % "9.3-1101-jdbc41"
)

libraryDependencies ++= Seq(
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
  "org.eclipse.jetty" % "jetty-webapp" % "9.1.0.v20131115" % "container",
  "org.eclipse.jetty" % "jetty-plus"   % "9.1.0.v20131115" % "container"
)
