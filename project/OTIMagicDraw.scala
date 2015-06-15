import java.io.File

import com.banno.license.Plugin.LicenseKeys._
import net.virtualvoid.sbt.graph.Plugin.graphSettings
import gov.nasa.jpl.sbt.MagicDrawEclipseClasspathPlugin._
import sbt.Keys._
import sbt._
import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._

/**
 * sbt \
 * -Dsbt.global.staging=sbt.staging \
 * -DMD_INSTALL_DIR=<dir where MagicDraw 18 is installed> \
 * -DOTI_LOCAL_REPOSITORY=<dir> where <dir> is a local Ivy repository directory
 */
object OTIMagicDraw extends Build {

  // ======================

  lazy val otiSettings = Seq(
    autoScalaLibrary := false,
    scalaVersion := Versions.scala,
    organization := "gov.nasa.jpl.mbee.omg.oti",
    organizationName := "JPL, Caltech & Object Management Group",
    organizationHomepage := Some(url("http://solitaire.omg.org/browse/TIWG")),

    // include repositories used in module configurations into the POM repositories section
    pomAllRepositories := true,

    // publish Maven POM metadata (instead of Ivy); this is important for the UpdatesPlugin's ability to find available updates.
    publishMavenStyle := true) ++
    ((Option.apply(System.getProperty("OTI_LOCAL_REPOSITORY")), Option.apply(System.getProperty("OTI_REMOTE_REPOSITORY"))) match {
      case (Some(dir), _) =>
        if (new File(dir) / "settings.xml" exists) {
          val cache = new MavenCache("JPL-OMG", new File(dir))
          Seq(
            publishTo := Some(cache),
            resolvers += cache)
        }
        else
          sys.error(s"The OTI_LOCAL_REPOSITORY folder, '$dir', does not have a 'settings.xml' file.")
      case (None, Some(url)) => {
        val repo = new MavenRepository("JPL-OMG", url)
        Seq(
          publishTo := Some(repo),
          resolvers += repo)
      }
      case _ => sys.error("Set either -DOTI_LOCAL_REPOSITORY=<dir> or -DOTI_REMOTE_REPOSITORY=<url> where <dir> is a local Maven repository directory or <url> is a remote Maven repository URL")
    })

  lazy val commonSettings =
    Defaults.coreDefaultSettings ++
      Defaults.runnerSettings ++
      Defaults.baseTasks ++
      graphSettings ++
      com.banno.license.Plugin.licenseSettings ++
      aether.AetherPlugin.autoImport.overridePublishSettings ++
      Seq(
        sourceDirectories in Compile ~= { _.filter(_.exists) },
        sourceDirectories in Test ~= { _.filter(_.exists) },
        unmanagedSourceDirectories in Compile ~= { _.filter(_.exists) },
        unmanagedSourceDirectories in Test ~= { _.filter(_.exists) },
        unmanagedResourceDirectories in Compile ~= { _.filter(_.exists) },
        unmanagedResourceDirectories in Test ~= { _.filter(_.exists) }
      )

  lazy val oti_magicdraw = Project(
    "oti-magicdraw",
    file(".")).
    enablePlugins(aether.AetherPlugin).
    enablePlugins(com.typesafe.sbt.packager.universal.UniversalPlugin).
    enablePlugins(com.timushev.sbt.updates.UpdatesPlugin).
    enablePlugins(gov.nasa.jpl.sbt.MagicDrawEclipseClasspathPlugin).
    settings(otiSettings: _*).
    settings(commonSettings: _*).
    settings(magicDrawEclipseClasspathSettings: _*).
    settings(
      version := Versions.version,
      removeExistingHeaderBlock := true,
      libraryDependencies ++= Seq(
        "xml-resolver" % "xml-resolver" % Versions.xmlResolver % "provided",
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-core" % Versions.oti_core_version intransitive() withSources() withJavadoc() artifacts Artifact("oti-core", "resource"),
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-change-migration" % Versions.oti_changeMigration_version intransitive() withSources() withJavadoc() artifacts Artifact("oti-change-migration", "resource"),
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-trees" % Versions.oti_trees_version intransitive() withSources() withJavadoc() artifacts Artifact("oti-trees", "resource")
      ),
      scalaSource in Compile := baseDirectory.value / "src",
      classDirectory in Compile := baseDirectory.value / "bin",
      unmanagedClasspath in Compile <++= unmanagedJars in Compile,

      com.typesafe.sbt.packager.Keys.topLevelDirectory in Universal := Some("dynamicScripts/org.omg.oti.magicdraw"),
      mappings in Universal <++= (baseDirectory, packageBin in Compile, packageSrc in Compile, packageDoc in Compile) map {
        (dir, bin, src, doc) =>
          (dir ** "*.dynamicScripts").pair(relativeTo(dir)) ++
            (dir ** "*.md").pair(relativeTo(dir)) ++
            com.typesafe.sbt.packager.MappingsHelper.directory(dir / "resources") ++
            Seq(
              (bin, "lib/" + bin.name),
              (src, "lib.sources/" + src.name),
              (doc, "lib.javadoc/" + doc.name)
            )
      },
      artifacts <+= (name in Universal) { n => Artifact(n, "jar", "jar", Some("resource"), Seq(), None, Map()) },
      packagedArtifacts <+= (packageBin in Universal, name in Universal) map { (p,n) =>
        Artifact(n, "jar", "jar", Some("resource"), Seq(), None, Map()) -> p
      },

      aether.AetherKeys.aetherArtifact <<=
        (aether.AetherKeys.aetherCoordinates,
          aether.AetherKeys.aetherPackageMain,
          makePom in Compile,
          packagedArtifacts in Compile) map {
          (coords: aether.MavenCoordinates, mainArtifact: File, pom: File, artifacts: Map[Artifact, File]) =>
            aether.AetherPlugin.createArtifact(artifacts, pom, coords, mainArtifact)
        },

      shellPrompt := { state => Project.extract(state).currentRef.project + " @ " + Versions.version_suffix + "> " }
    )

}
