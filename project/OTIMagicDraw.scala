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

  def addIfExists(f: File, name: String): Seq[(File, String)] =
    if (!f.exists) Seq()
    else Seq((f, name))

  val QUALIFIED_NAME = "^[a-zA-Z][\\w_]*(\\.[a-zA-Z][\\w_]*)*$".r

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
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-core"
        % Versions.oti_core_version intransitive() withSources() withJavadoc()
        artifacts Artifact("oti-core", "resource"),
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-change-migration"
        % Versions.oti_changeMigration_version intransitive() withSources() withJavadoc()
        artifacts Artifact("oti-change-migration", "resource"),
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-trees"
        % Versions.oti_trees_version intransitive() withSources() withJavadoc()
        artifacts Artifact("oti-trees", "resource"),
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-canonical-xmi"
        % Versions.oti_canonical_xmi_version intransitive() withSources() withJavadoc()
        artifacts Artifact("oti-canonical-xmi", "resource"),
        "gov.nasa.jpl.mbee.omg.oti" %% "oti-loader"
        % Versions.oti_loader_version intransitive() withSources() withJavadoc()
        artifacts Artifact("oti-loader", "resource")
      ),

      scalacOptions ++= List("-target:jvm-1.7"),

      // https://tpolecat.github.io/2014/04/11/scalac-flags.html
      scalacOptions ++= Seq(
        "-deprecation",
        "-encoding", "UTF-8", // yes, this is 2 args
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-unchecked",
        "-Xfatal-warnings",
        "-Xlint:_,-option-implicit",
        "-Yno-adapted-args",
        "-Ywarn-dead-code", // N.B. doesn't work well with the ??? hole
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard",
        "-Xfuture",
        "-Ywarn-unused-import", // 2.11 only
        "-Yno-imports" // no automatic imports at all; all symbols must be imported explicitly
      ),

      // https://github.com/puffnfresh/wartremover
      //wartremoverErrors ++= Warts.unsafe,
      //wartremoverWarnings ++= Warts.all,

      scalacOptions in(Compile, doc) ++= Seq(
        "-diagrams",
        "-doc-title", name.value,
        "-doc-root-content", baseDirectory.value + "/rootdoc.txt"
      ),

      scalaSource in Compile := baseDirectory.value / "src",
      classDirectory in Compile := baseDirectory.value / "bin",
      scalacOptions ++= List("-target:jvm-1.7", "-feature"),

      unmanagedClasspath in Compile <++= unmanagedJars in Compile,

      // the '*-resource.zip' archive will start from: 'dynamicScripts/<dynamicScriptsProjectName>'
      com.typesafe.sbt.packager.Keys.topLevelDirectory in Universal := Some("dynamicScripts/org.omg.oti.magicdraw"),

      // name the '*-resource.zip' in the same way as other artifacts
      com.typesafe.sbt.packager.Keys.packageName in Universal :=
        normalizedName.value + "_" + scalaBinaryVersion.value + "-" + version.value + "-resource",

      // contents of the '*-resource.zip' to be produced by 'universal:packageBin'
      mappings in Universal <++= (baseDirectory,
        packageBin in Compile, packageSrc in Compile, packageDoc in Compile,
        packageBin in Test, packageSrc in Test, packageDoc in Test) map {
        (dir, bin, src, doc, binT, srcT, docT) =>
          (dir ** "*.dynamicScripts").pair(relativeTo(dir)) ++
            ((dir ** "*.md") --- (dir / "sbt.staging" ***)).pair(relativeTo(dir)) ++
            (dir / "models" ** "*.mdzip").pair(relativeTo(dir)) ++
            com.typesafe.sbt.packager.MappingsHelper.directory(dir / "resources") ++
            addIfExists(bin, "lib/" + bin.name) ++
            addIfExists(binT, "lib/" + binT.name) ++
            addIfExists(src, "lib.sources/" + src.name) ++
            addIfExists(srcT, "lib.sources/" + srcT.name) ++
            addIfExists(doc, "lib.javadoc/" + doc.name) ++
            addIfExists(docT, "lib.javadoc/" + docT.name)
      },

      // add the '*-resource.zip' to the list of artifacts to publish; note that '.zip' will change to '.jar'
      artifacts <+= (name in Universal) { n => Artifact(n, "jar", "jar", Some("resource"), Seq(), None, Map()) },
      packagedArtifacts <+= (packageBin in Universal, name in Universal) map { (p, n) =>
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
