import java.io.File
import java.net.URI
import java.util.Locale

import com.banno.license.Plugin.LicenseKeys._
import net.virtualvoid.sbt.graph.Plugin.graphSettings
import gov.nasa.jpl.sbt.MagicDrawEclipseClasspathPlugin._
import sbt.Keys._
import sbt.RichURI.fromURI
import sbt._

import scala.util.matching.Regex

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

  lazy val archivesToExtract = TaskKey[Map[File, (File, File)]]("archives-to-extract", "ZIP files to be extracted at a target directory according to the 'extract' attribute of the corresponding library dependency")

  lazy val extractArchives = TaskKey[Unit]("extract-archives", "Extracts ZIP files")

  lazy val extractSettings = {

    val type2folder = Map("jar" -> "lib", "src" -> "lib.srcs", "doc" -> "lib.javadoc")

    Seq(

      archivesToExtract <<= (libraryDependencies, update, scalaBinaryVersion, baseDirectory, streams) map { (deps, up, ver, base, s) =>

        val artifact2extract = (for {
          dep <- deps
          if !dep.configurations.toSet.contains("provided")
          tuple = (dep.name + "-" + dep.revision, dep.name)
        } yield dep.name + "_" + ver -> tuple) toMap

        val artifactArchive2extractFolder = (for {
          cReport <- up.configurations
          mReport <- cReport.modules
          (artifact, archive) <- mReport.artifacts
          if artifact.extension == "jar"
          (folder, extract) <- artifact2extract.get(artifact.name)
          subFolder = new File(folder)
          extractFolder = new File(base.getAbsolutePath + File.separator + type2folder(artifact.`type`))
          tuple = (subFolder, extractFolder)
        } yield archive -> tuple) toMap

        artifactArchive2extractFolder
      },

      extractArchives <<= (archivesToExtract, streams) map { (a2e, s) =>
        a2e foreach { case (archive, (subFolder, extractFolder)) =>
          s.log.info(s"Copy archive $archive\n=> $extractFolder")
          IO.copyFile(archive, extractFolder / archive.name, preserveLastModified = true)
        }
      },
      cleanFiles <++= baseDirectory { base => type2folder.values map (base / _) toSeq }
    )
  }

  lazy val oti_magicdraw = Project(
    "oti-magicdraw",
    file(".")).
    enablePlugins(aether.AetherPlugin, gov.nasa.jpl.sbt.MagicDrawEclipseClasspathPlugin).
    settings(otiSettings: _*).
    settings(commonSettings: _*).
    settings(magicDrawEclipseClasspathSettings: _*).
    settings(extractSettings: _*).
    settings(
      version := Versions.version,
      removeExistingHeaderBlock := true,
      libraryDependencies ++= Seq(
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-core" % Versions.oti_core_version intransitive() withSources() withJavadoc(),
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-change-migration" % Versions.oti_changeMigration_version intransitive() withSources() withJavadoc(),
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-trees" % Versions.oti_trees_version intransitive() withSources() withJavadoc()
      ),
      classDirectory in Compile := baseDirectory.value / "bin",
      shellPrompt := { state => Project.extract(state).currentRef.project + " @ " + Versions.version_suffix + "> " }
    )

}
