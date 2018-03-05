import sbt._
import Keys._

object build extends Build {
    val sbtAvro = Project(
        id = "sbt-avro",
        base = file("."),
        settings = Defaults.defaultSettings ++ Seq[Project.Setting[_]](
            organization := "com.tremorvideo",
            version := "0.3.2.1",
            sbtPlugin := true,
            libraryDependencies ++= Seq(
                    "org.apache.avro" % "avro" % "1.7.5",
                    "org.apache.avro" % "avro-compiler" % "1.7.5"
            ),
            scalaVersion := "2.10.2",
            scalacOptions in Compile ++= Seq("-deprecation"),
            crossScalaVersions := Seq("2.10.2"),
            description := "Sbt plugin for compiling Avro sources",

            publishTo := {
                Some(
                    Resolver.url(
                        "sbt-plugin-releases",
                        new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/")
                    )(Resolver.ivyStylePatterns)
                )
                val artifactory = "http://artifactory.service.iad1.consul:8081/artifactory/"
                val (name, url) = if (version.value.contains("-SNAPSHOT"))
                    ("Artifactory Realm", artifactory + "libs-snapshot;build.timestamp=" + new java.util.Date().getTime)
                else
                    ("Artifactory Realm", artifactory + "libs-release;build.timestamp=" + new java.util.Date().getTime)
                Some(Resolver.url(name, new URL(url)))
            },

            publishMavenStyle := false
        )
    )
}
