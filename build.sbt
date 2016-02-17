import java.io.File
import java.nio.file.Files
import sbt.Keys._
import sbt._

import scala.collection.JavaConversions._

import gov.nasa.jpl.imce.sbt._

useGpg := true

ivyLoggingLevel := UpdateLogging.Full

logLevel in Compile := Level.Debug

persistLogLevel := Level.Debug

developers := List(
  Developer(
    id="rouquett",
    name="Nicolas F. Rouquette",
    email="nicolas.f.rouquette@jpl.nasa.gov",
    url=url("https://gateway.jpl.nasa.gov/personal/rouquett/default.aspx")),
  Developer(
    id="melaasar",
    name="Maged Elaasar",
    email="maged.elaasar@jpl.nasa.gov",
    url=url("https://gateway.jpl.nasa.gov/personal/melaasar/default.aspx")))

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

cleanFiles <+=
  baseDirectory { base => base / "imce.md.package" }

lazy val mdInstallDirectory = SettingKey[File]("md-install-directory", "MagicDraw Installation Directory")

mdInstallDirectory in Global := baseDirectory.value / "imce.md.package"

lazy val core = Project("oti-uml-magicdraw-adapter", file("."))
  .enablePlugins(IMCEGitPlugin)
  .enablePlugins(IMCEReleasePlugin)
  .settings(dynamicScriptsResourceSettings(Some("org.omg.oti.uml.magicdraw.adapter")))
  .settings(IMCEPlugin.strictScalacFatalWarningsSettings)
  .settings(IMCEPlugin.scalaDocSettings(diagrams=false))
  .settings(IMCEReleasePlugin.packageReleaseProcessSettings)
  .settings(
    IMCEKeys.licenseYearOrRange := "2014-2016",
    IMCEKeys.organizationInfo := IMCEPlugin.Organizations.oti,
    IMCEKeys.targetJDK := IMCEKeys.jdk17.value,

    organization := "org.omg.tiwg",
    organizationHomepage :=
      Some(url("http://www.omg.org/members/sysml-rtf-wiki/doku.php?id=rtf5:groups:tools_infrastructure:index")),

    buildInfoPackage := "org.omg.oti.uml.magicdraw.adapter",
    buildInfoKeys ++= Seq[BuildInfoKey](BuildInfoKey.action("buildDateUTC") { buildUTCDate.value }),

    mappings in (Compile, packageSrc) ++= {
      import Path.{flat, relativeTo}
      val base = (sourceManaged in Compile).value
      val srcs = (managedSources in Compile).value
      srcs x (relativeTo(base) | flat)
    },

    projectID := {
      val previous = projectID.value
      previous.extra(
        "build.date.utc" -> buildUTCDate.value,
        "artifact.kind" -> "magicdraw.library")
    },

    git.baseVersion := Versions.version,
    organizationName := "JPL, Caltech, Airbus & Object Management Group",
    organizationHomepage := Some(url("http://solitaire.omg.org/browse/TIWG")),

    scalaSource in Compile := baseDirectory.value / "svn" / "org.omg.oti.magicdraw" / "src",

    classDirectory in Compile := baseDirectory.value / "svn" / "org.omg.oti.magicdraw" / "bin",
    cleanFiles += (classDirectory in Compile).value,

    resourceDirectory in Compile := baseDirectory.value / "svn" / "org.omg.oti.magicdraw" / "resources",

    // disable publishing the jar produced by `test:package`
    publishArtifact in(Test, packageBin) := false,

    // disable publishing the test API jar
    publishArtifact in(Test, packageDoc) := false,

    // disable publishing the test sources jar
    publishArtifact in(Test, packageSrc) := false,

    unmanagedClasspath in Compile <++= unmanagedJars in Compile,
    libraryDependencies ++= Seq (
      //  extra("artifact.kind" -> "generic.library")
      "org.omg.tiwg" %% "oti-uml-change_migration"
        % Versions_oti_uml_change_migration.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("oti-uml-change_migration", "zip", "zip", Some("resource"), Seq(), None, Map()),

      //  extra("artifact.kind" -> "generic.library")
      "org.omg.tiwg" %% "oti-uml-composite_structure_tree_analysis"
        % Versions_oti_uml_composite_structure_tree_analysis.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("oti-uml-composite_structure_tree_analysis", "zip", "zip", Some("resource"), Seq(), None, Map()),

      //  extra("artifact.kind" -> "generic.library")
      "org.omg.tiwg" %% "oti-uml-canonical_xmi-loader"
        % Versions_oti_uml_canonical_xmi_loader.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("oti-uml-canonical_xmi-loader", "zip", "zip", Some("resource"), Seq(), None, Map()),

      "gov.nasa.jpl.imce.magicdraw.plugins" %% "imce_md18_0_sp5_dynamic-scripts"
        % Versions_imce_md18_0_sp5_dynamic_scripts.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("imce_md18_0_sp5_dynamic-scripts", "zip", "zip", Some("resource"), Seq(), None, Map())

    ),

    extractArchives <<= (baseDirectory, update, streams,
      mdInstallDirectory in ThisBuild) map {
      (base, up, s, mdInstallDir) =>

        if (!mdInstallDir.exists) {

          val pfilter: DependencyFilter = new DependencyFilter {
            def apply(c: String, m: ModuleID, a: Artifact): Boolean =
              (a.`type` == "zip" || a.`type` == "resource") &&
                a.extension == "zip" &&
                m.organization == "gov.nasa.jpl.cae.magicdraw.packages"
          }
          val ps: Seq[File] = up.matching(pfilter)
          ps.foreach { zip =>
            val files = IO.unzip(zip, mdInstallDir)
            s.log.info(
              s"=> created md.install.dir=$mdInstallDir with ${files.size} " +
                s"files extracted from zip: ${zip.getName}")
          }

          val mdDynamicScriptsDir = mdInstallDir / "dynamicScripts"
          IO.createDirectory(mdDynamicScriptsDir)

          val zfilter: DependencyFilter = new DependencyFilter {
            def apply(c: String, m: ModuleID, a: Artifact): Boolean =
              (a.`type` == "zip" || a.`type` == "resource") &&
                a.extension == "zip" &&
                m.organization == "org.omg.tiwg"
          }
          val zs: Seq[File] = up.matching(zfilter)
          zs.foreach { zip =>
            val files = IO.unzip(zip, mdDynamicScriptsDir)
            s.log.info(
              s"=> extracted ${files.size} DynamicScripts files from zip: ${zip.getName}")
          }

          val mdBinFolder = mdInstallDir / "bin"
          require(mdBinFolder.exists, "md bin: $mdBinFolder")

        } else {
          s.log.info(
            s"=> use existing md.install.dir=$mdInstallDir")
        }

    },

    unmanagedJars in Compile <++= (baseDirectory, update, streams,
      mdInstallDirectory in ThisBuild,
      extractArchives) map {
      (base, up, s, mdInstallDir, _) =>

        val libJars = ((mdInstallDir / "lib") ** "*.jar").get
        s.log.info(s"jar libraries: ${libJars.size}")

        val dsJars = ((mdInstallDir / "dynamicScripts") * "*" / "lib" ** "*.jar").get
        s.log.info(s"jar dynamic script: ${dsJars.size}")

        val mdJars = (libJars ++ dsJars).map { jar => Attributed.blank(jar) }

        mdJars
    },

    compile <<= (compile in Compile) dependsOn extractArchives,

    IMCEKeys.nexusJavadocRepositoryRestAPIURL2RepositoryName := Map(
      "https://oss.sonatype.org/service/local" -> "releases",
      "https://cae-nexuspro.jpl.nasa.gov/nexus/service/local" -> "JPL",
      "https://cae-nexuspro.jpl.nasa.gov/nexus/content/groups/jpl.beta.group" -> "JPL Beta Group",
      "https://cae-nexuspro.jpl.nasa.gov/nexus/content/groups/jpl.public.group" -> "JPL Public Group"),
    IMCEKeys.pomRepositoryPathRegex := """\<repositoryPath\>\s*([^\"]*)\s*\<\/repositoryPath\>""".r

  )

def dynamicScriptsResourceSettings(dynamicScriptsProjectName: Option[String] = None): Seq[Setting[_]] = {

  import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._

  def addIfExists(f: File, name: String): Seq[(File, String)] =
    if (!f.exists) Seq()
    else Seq((f, name))

  val QUALIFIED_NAME = "^[a-zA-Z][\\w_]*(\\.[a-zA-Z][\\w_]*)*$".r

  Seq(
    // the '*-resource.zip' archive will start from: 'dynamicScripts/<dynamicScriptsProjectName>'
    com.typesafe.sbt.packager.Keys.topLevelDirectory in Universal := {
      val projectName = dynamicScriptsProjectName.getOrElse(baseDirectory.value.getName)
      require(
        QUALIFIED_NAME.pattern.matcher(projectName).matches,
        s"The project name, '$projectName` is not a valid Java qualified name")
      Some(projectName)
    },

    // name the '*-resource.zip' in the same way as other artifacts
    com.typesafe.sbt.packager.Keys.packageName in Universal :=
      normalizedName.value + "_" + scalaBinaryVersion.value + "-" + version.value + "-resource",

    // contents of the '*-resource.zip' to be produced by 'universal:packageBin'
    mappings in Universal <++= (
      baseDirectory,
      packageBin in Compile,
      packageSrc in Compile,
      packageDoc in Compile,
      packageBin in Test,
      packageSrc in Test,
      packageDoc in Test,
      streams) map {
      (base, bin, src, doc, binT, srcT, docT, s) =>
        val dir = base / "svn" / "org.omg.oti.magicdraw"
        val file2name = (dir ** "*.dynamicScripts").pair(relativeTo(dir)) ++
          (dir ** "*.mdzip").pair(relativeTo(dir)) ++
          (dir / "resources" ***).pair(relativeTo(dir)) ++
          com.typesafe.sbt.packager.MappingsHelper.directory(dir / "resources") ++
          com.typesafe.sbt.packager.MappingsHelper.directory(dir / "profiles") ++
          addIfExists(bin, "lib/" + bin.name) ++
          addIfExists(binT, "lib/" + binT.name) ++
          addIfExists(src, "lib.sources/" + src.name) ++
          addIfExists(srcT, "lib.sources/" + srcT.name) ++
          addIfExists(doc, "lib.javadoc/" + doc.name) ++
          addIfExists(docT, "lib.javadoc/" + docT.name)

        s.log.info(s"file2name entries: ${file2name.size}")
        s.log.info(file2name.mkString("\n"))

        file2name
    },

    artifacts <+= (name in Universal) { n => Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map()) },
    packagedArtifacts <+= (packageBin in Universal, name in Universal) map { (p, n) =>
      Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map()) -> p
    }
  )
}