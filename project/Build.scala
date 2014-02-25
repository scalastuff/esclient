import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseCreateSrc
 
/**
 * gpg --keyserver hkp://pool.sks-keyservers.net  --no-permission-warning --send-keys 331928A8
 */
object ESClientBuild extends Build {
 
  val es = "org.elasticsearch" % "elasticsearch" % "1.0.0" 
          
  lazy val esclient = Project(id = "esclient", base = file("."), settings = Project.defaultSettings ++ publishSettings ++ Seq(
    sbtPlugin := false,
    organization := "org.scalastuff",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.10.3",
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
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
    pomExtra := <scm>
                  <connection>scm:git:git@github.com:scalastuff/esclient.git</connection>
                  <url>https://github.com/scalastuff/esclient</url>
                </scm>
                <developers>
                  <developer>
                    <id>ruudditerwich</id>
                    <name>Ruud Diterwich</name>
                    <url>https://github.com/rditerwich</url>
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



