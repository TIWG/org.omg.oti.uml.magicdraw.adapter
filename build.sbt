import java.io.File
import sbt.Keys._
import sbt._

import gov.nasa.jpl.imce.sbt._
import gov.nasa.jpl.imce.sbt.ProjectHelper._

updateOptions := updateOptions.value.withCachedResolution(true)

import scala.io.Source
import scala.util.control.Exception._

resolvers := {
  val previous = resolvers.value
  if (git.gitUncommittedChanges.value)
    Seq[Resolver](Resolver.mavenLocal) ++ previous
  else
    previous
}

// @see https://github.com/jrudolph/sbt-dependency-graph/issues/113
def zipFileSelector
( a: Artifact, f: File)
: Boolean
= a.`type` == "zip" || a.extension == "zip"

def pluginFileSelector
( a: Artifact, f: File)
: Boolean
= a.name.startsWith("imce.dynamic_scripts.magicdraw.plugin") &&
  a.classifier.getOrElse("").startsWith("part")

// @see https://github.com/jrudolph/sbt-dependency-graph/issues/113
def fromConfigurationReport
(report: ConfigurationReport,
 rootInfo: sbt.ModuleID,
 selector: (Artifact, File) => Boolean)
: net.virtualvoid.sbt.graph.ModuleGraph = {
  implicit def id(sbtId: sbt.ModuleID): net.virtualvoid.sbt.graph.ModuleId
  = net.virtualvoid.sbt.graph.ModuleId(sbtId.organization, sbtId.name, sbtId.revision)

  def moduleEdges(orgArt: OrganizationArtifactReport)
  : Seq[(net.virtualvoid.sbt.graph.Module, Seq[net.virtualvoid.sbt.graph.Edge])]
  = {
    val chosenVersion = orgArt.modules.find(!_.evicted).map(_.module.revision)
    orgArt.modules.flatMap(moduleEdge(chosenVersion))
  }

  def moduleEdge(chosenVersion: Option[String])(report: ModuleReport)
  : Seq[(net.virtualvoid.sbt.graph.Module, Seq[net.virtualvoid.sbt.graph.Edge])] = {
    val evictedByVersion = if (report.evicted) chosenVersion else None

    report
      .artifacts
      .filter(selector.tupled)
      .map { case (artifact, file) =>

        (net.virtualvoid.sbt.graph.Module(
          id = report.module,
          license = report.licenses.headOption.map(_._1),
          evictedByVersion = evictedByVersion,
          jarFile = Some(file),
          error = report.problem),
          report.callers.map(caller ⇒ net.virtualvoid.sbt.graph.Edge(caller.caller, report.module)))
      }
  }

  val (nodes, edges) = report.details.flatMap(moduleEdges).unzip
  val root = net.virtualvoid.sbt.graph.Module(rootInfo)

  net.virtualvoid.sbt.graph.ModuleGraph(root +: nodes, edges.flatten)
}

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

lazy val mdInstallDirectory = SettingKey[File]("md-install-directory", "MagicDraw Installation Directory")

mdInstallDirectory in ThisBuild :=
  (baseDirectory in ThisBuild).value / "target" / "md.package"

cleanFiles += (mdInstallDirectory in ThisBuild).value

lazy val core = Project("oti-uml-magicdraw-adapter", file("."))
  .enablePlugins(IMCEGitPlugin)
  .enablePlugins(IMCEReleasePlugin)
  .settings(dynamicScriptsResourceSettings("org.omg.oti.uml.magicdraw.adapter"))
  .settings(IMCEPlugin.strictScalacFatalWarningsSettings)
  .settings(IMCEReleasePlugin.packageReleaseProcessSettings)
  .settings(
    IMCEKeys.licenseYearOrRange := "2014-2016",
    IMCEKeys.organizationInfo := IMCEPlugin.Organizations.oti,
    IMCEKeys.targetJDK := IMCEKeys.jdk18.value,

    buildInfoKeys ++= Seq[BuildInfoKey](BuildInfoKey.action("buildDateUTC") { buildUTCDate.value }),
    buildInfoPackage := "org.omg.oti.uml.magicdraw.adapter",

    mappings in (Compile, packageSrc) ++= {
      import Path.{flat, relativeTo}
      val base = (sourceManaged in Compile).value
      val srcs = (managedSources in Compile).value
      srcs pair (relativeTo(base) | flat)
    },

    projectID := {
      val previous = projectID.value
      previous.extra(
        "build.date.utc" -> buildUTCDate.value,
        "artifact.kind" -> "magicdraw.library")
    },

    git.baseVersion := Versions.version,

    resourceDirectory in Compile :=
      baseDirectory.value / "resources",

    // disable publishing the jar produced by `test:package`
    publishArtifact in(Test, packageBin) := false,

    // disable publishing the test API jar
    publishArtifact in(Test, packageDoc) := false,

    // disable publishing the test sources jar
    publishArtifact in(Test, packageSrc) := false,

    unmanagedClasspath in Compile ++= (unmanagedJars in Compile).value,

    resolvers += Resolver.bintrayRepo("jpl-imce", "gov.nasa.jpl.imce"),
    resolvers += Resolver.bintrayRepo("tiwg", "org.omg.tiwg"),

    resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases",
    scalacOptions in (Compile, compile) += s"-P:artima-supersafe:config-file:${baseDirectory.value}/project/supersafe.cfg",
    scalacOptions in (Test, compile) += s"-P:artima-supersafe:config-file:${baseDirectory.value}/project/supersafe.cfg",
    scalacOptions in (Compile, doc) += "-Xplugin-disable:artima-supersafe",
    scalacOptions in (Test, doc) += "-Xplugin-disable:artima-supersafe"

  )
  .dependsOnSourceProjectOrLibraryArtifacts(
     "oti-uml-change_migration",
     "org.omg.oti.uml.change_migration",
     Seq(
       "org.omg.tiwg" %% "org.omg.oti.uml.change_migration"
       % Versions_oti_uml_change_migration.version %
       "compile" withSources() withJavadoc() artifacts
       Artifact("org.omg.oti.uml.change_migration", "zip", "zip", "resource")
    )
  )
  .dependsOnSourceProjectOrLibraryArtifacts(
    "oti-uml-composite_structure_tree_analysis",
    "org.omg.oti.uml.composite_structure_tree_analysis",
    Seq(
      "org.omg.tiwg" %% "org.omg.oti.uml.composite_structure_tree_analysis"
        % Versions_oti_uml_composite_structure_tree_analysis.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("org.omg.oti.uml.composite_structure_tree_analysis", "zip", "zip", "resource")
    )
  )
  .dependsOnSourceProjectOrLibraryArtifacts(
    "oti-uml-canonical_xmi-loader",
    "org.omg.oti.uml.canonical_xmi.loader",
    Seq(
      "org.omg.tiwg" %% "org.omg.oti.uml.canonical_xmi.loader"
        % Versions_oti_uml_canonical_xmi_loader.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("org.omg.oti.uml.canonical_xmi.loader", "zip", "zip", "resource")
    )
  )
  .dependsOnSourceProjectOrLibraryArtifacts(
    "org-omg-oti-uml-json-serialization",
    "org.omg.oti.uml.json.serialization",
    Seq(
      "org.omg.tiwg" %% "org.omg.oti.uml.json.serialization"
        % Versions_oti_uml_json_serialization.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("org.omg.oti.uml.json.serialization", "zip", "zip", "resource")
    )
  )
  .settings(
    libraryDependencies +=
      "gov.nasa.jpl.imce" %% "imce.dynamic_scripts.magicdraw.plugin"
        % Versions_imce_md18_0_sp6_dynamic_scripts.version %
        "compile" withSources() withJavadoc() artifacts(
        Artifact("imce.dynamic_scripts.magicdraw.plugin", "zip", "zip", "part1"),
        Artifact("imce.dynamic_scripts.magicdraw.plugin", "zip", "zip", "part2")),

    extractArchives := {
      val base = baseDirectory.value
      val up = update.value
      val s = streams.value
      val mdInstallDir = (mdInstallDirectory in ThisBuild).value
      val showDownloadProgress = true
      val crossV = CrossVersion(scalaVersion.value, scalaBinaryVersion.value)(projectID.value)
      val compileDepGraph =
        net.virtualvoid.sbt.graph.DependencyGraphKeys.ignoreMissingUpdate.value.configuration("compile").get

      val pluginParts = (for {
        cReport <- up.configurations
        if cReport.configuration == "compile"
        mReport <- cReport.modules
        (artifact, archive) <- mReport.artifacts
        if artifact.name.startsWith("imce.dynamic_scripts.magicdraw.plugin")
        if artifact.classifier.getOrElse("").startsWith("part")
      } yield archive).sorted
      s.log.warn(s"Extracting Plugin from ${pluginParts.size} parts:")
      pluginParts.foreach { p => s.log.warn(p.getAbsolutePath) }

      val pparts = fromConfigurationReport(compileDepGraph, crossV, pluginFileSelector)
      for {
        module <- pparts.nodes
        if module.id.name.startsWith("imce.dynamic_scripts.magicdraw.plugin")
      } yield {
        s.log.info(s"part: $module")
      }

      if (!mdInstallDir.exists) {
        
        MagicDrawDownloader.fetchMagicDraw(
          s.log, showDownloadProgress,
          up,
          credentials.value,
          mdInstallDir, base / "target" / "no_install.zip")

        {
          val merged = File.createTempFile("plugin_merged", ".zip")
          println(s"merged: ${merged.getAbsolutePath}")

          val zip = File.createTempFile("plugin_resource", ".zip")
          println(s"zip: ${zip.getAbsolutePath}")

          val script = File.createTempFile("unzip_plugin", ".sh")
          println(s"script: ${script.getAbsolutePath}")

          val out = new java.io.PrintWriter(new java.io.FileOutputStream(script))
          out.println("#!/bin/bash")
          out.println(pluginParts.map(_.getAbsolutePath).mkString("cat ", " ", s" > ${merged.getAbsolutePath}"))
          out.println(s"zip -FF ${merged.getAbsolutePath} --out ${zip.getAbsolutePath}")
          out.println(s"unzip -q ${zip.getAbsolutePath} -d ${mdInstallDir.getAbsolutePath}")
          out.close()

          val result = sbt.Process(command = "/bin/bash", arguments = Seq[String](script.getAbsolutePath)).!
          require(0 <= result && result <= 2, s"Failed to execute script (exit=$result): ${script.getAbsolutePath}")
          s.log.warn(s"Extracted.")

        }

      } else
        s.log.warn(
          s"=> use existing md.install.dir=$mdInstallDir")
    },

    compile in Compile := (compile in Compile).dependsOn(unmanagedJars in Compile).value,

    compileIncremental in Compile := (compileIncremental in Compile).dependsOn(unmanagedJars in Compile).value,

    doc in Compile := (doc in Compile).dependsOn(unmanagedJars in Compile).value,

    unmanagedJars in Compile := (unmanagedJars in Compile).dependsOn(extractArchives).value,

    unmanagedJars in Compile ++= {
      val base = baseDirectory.value
      val up = update.value
      val s = streams.value
      val mdInstallDir = (mdInstallDirectory in ThisBuild).value

      val _ = extractArchives.value

      val libJars = ((mdInstallDir / "lib") ** "*.jar").get
      val mdJars = libJars.map { jar => Attributed.blank(jar) }

      s.log.warn(s"=> Adding ${mdJars.size} unmanaged jars")

      mdJars
    }
  )

def dynamicScriptsResourceSettings(projectName: String): Seq[Setting[_]] = {

  import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._

  def addIfExists(f: File, name: String): Seq[(File, String)] =
    if (!f.exists) Seq()
    else Seq((f, name))

  val QUALIFIED_NAME = "^[a-zA-Z][\\w_]*(\\.[a-zA-Z][\\w_]*)*$".r

  Seq(
    // the '*-resource.zip' archive will start from: 'dynamicScripts'
    com.typesafe.sbt.packager.Keys.topLevelDirectory in Universal := None,

    // name the '*-resource.zip' in the same way as other artifacts
    com.typesafe.sbt.packager.Keys.packageName in Universal :=
      normalizedName.value + "_" + scalaBinaryVersion.value + "-" + version.value + "-resource",

    // contents of the '*-resource.zip' to be produced by 'universal:packageBin'
    mappings in Universal in packageBin ++= {
      val dir = baseDirectory.value
      val bin = (packageBin in Compile).value
      val src = (packageSrc in Compile).value
      val doc = (packageDoc in Compile).value
      val binT = (packageBin in Test).value
      val srcT = (packageSrc in Test).value
      val docT = (packageDoc in Test).value

      (dir / "profiles" ** "*.mdzip").pair(rebase(dir, "..")) ++
      (dir / "resources" ***).pair(rebase(dir, projectName)) ++
      addIfExists(dir / ".classpath", projectName + "/.classpath") ++
      addIfExists(dir / "README.md", projectName + "/README.md") ++
      addIfExists(bin, projectName + "/lib/" + bin.name) ++
      addIfExists(binT, projectName + "/lib/" + binT.name) ++
      addIfExists(src, projectName + "/lib.sources/" + src.name) ++
      addIfExists(srcT, projectName + "/lib.sources/" + srcT.name) ++
      addIfExists(doc, projectName + "/lib.javadoc/" + doc.name) ++
      addIfExists(docT, projectName + "/lib.javadoc/" + docT.name)
    },

    artifacts += {
      val n = (name in Universal).value
      Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map())
    },
    packagedArtifacts += {
      val p = (packageBin in Universal).value
      val n = (name in Universal).value
      Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map()) -> p
    }
  )
}