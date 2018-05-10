import sbt._
import Keys._
import play.twirl.sbt.Import.TwirlKeys

object ApplicationBuild extends Build {

  val appName    = "play2-auth"

  val playVersion = play.core.PlayVersion.current

  lazy val baseSettings = Seq(
    version            := "0.13.0",
    scalaVersion       := "2.11.7",
    crossScalaVersions := Seq("2.10.4", "2.11.7"),
    organization       := "jp.t2v",
    resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers ++= {
      if (isSnapshot.value) {
        Seq("Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
      } else {
        Nil
      }
    },
    resolvers += "Sonatype Releases"  at "https://oss.sonatype.org/content/repositories/releases"
  )


  lazy val core = Project("core", base = file("module"))
    .settings(baseSettings: _*)
    .settings(
      libraryDependencies += "com.typesafe.play"  %%   "play"                   % playVersion        % "provided",
      libraryDependencies += play.PlayImport.cache,
      libraryDependencies += "jp.t2v"             %%   "stackable-controller"   % "0.4.1",
      name                    := appName
    )

  lazy val root = Project("root", base = file("."))
    .settings(baseSettings: _*)
    .settings(
      publish           := { },
      publishArtifact   := false,
      packagedArtifacts := Map.empty
    ).aggregate(core)

}
