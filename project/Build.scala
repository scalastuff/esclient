import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseCreateSrc
 
object ESClientBuild extends Build {
 
  val es = "org.elasticsearch" % "elasticsearch" % "0.20.0" withSources()
          
  lazy val esclient = Project(id = "esclient", base = file("."), settings = Project.defaultSettings ++ publishSettings ++ Seq(
    sbtPlugin := false,
    organization := "org.scalastuff",
    version := "0.20.1-SNAPSHOT",
    scalaVersion := "2.10.0",
    scalacOptions += "-deprecation",
    scalacOptions += "-unchecked",
    EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
    EclipseKeys.withSource := true,
    libraryDependencies += es
    ))

  def publishSettings = Seq(
    licenses := Seq("The Apache Software Licence, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/scalastuff/esclient")),
    pomIncludeRepository := { _ => false },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomExtra := <scm>
                  <connection>scm:git:git@github.com:scalastuff/scalaleafs.git</connection>
                  <url>https://github.com/scalastuff/scalaleafs</url>
                </scm>
                <developers>
                  <developer>
                    <id>ruudditerwich</id>
                    <name>Ruud Diterwich</name>
                    <url>http://ruud.diterwich.com</url>
                  </developer>
                </developers>,
    publishTo <<= version { (v: String) =>
          val nexus = "https://oss.sonatype.org/"
          if (v.trim.endsWith("SNAPSHOT")) 
            Some("snapshots" at nexus + "content/repositories/snapshots") 
          else
            Some("releases"  at nexus + "service/local/staging/deploy/maven2")
        })        
}



