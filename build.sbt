import java.io.File
import sbt.Keys._
import sbt._

import gov.nasa.jpl.imce.sbt._
import gov.nasa.jpl.imce.sbt.ProjectHelper._

useGpg := true

updateOptions := updateOptions.value.withCachedResolution(true)

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

import scala.io.Source
import scala.util.control.Exception._

def docSettings(diagrams:Boolean): Seq[Setting[_]] =
  Seq(
    sources in (Compile,doc) <<= (git.gitUncommittedChanges, sources in (Compile,compile)) map {
      (uncommitted, compileSources) =>
        if (uncommitted)
          Seq.empty
        else
          compileSources
    },

    sources in (Test,doc) <<= (git.gitUncommittedChanges, sources in (Test,compile)) map {
      (uncommitted, testSources) =>
        if (uncommitted)
          Seq.empty
        else
          testSources
    },

    scalacOptions in (Compile,doc) ++=
      (if (diagrams)
        Seq("-diagrams")
      else
        Seq()
        ) ++
        Seq(
          "-doc-title", name.value,
          "-doc-root-content", baseDirectory.value + "/rootdoc.txt"
        ),
    autoAPIMappings := ! git.gitUncommittedChanges.value,
    apiMappings <++=
      ( git.gitUncommittedChanges,
        dependencyClasspath in Compile in doc,
        IMCEKeys.nexusJavadocRepositoryRestAPIURL2RepositoryName,
        IMCEKeys.pomRepositoryPathRegex,
        streams ) map { (uncommitted, deps, repoURL2Name, repoPathRegex, s) =>
        if (uncommitted)
          Map[File, URL]()
        else
          (for {
            jar <- deps
            url <- jar.metadata.get(AttributeKey[ModuleID]("moduleId")).flatMap { moduleID =>
              val urls = for {
                (repoURL, repoName) <- repoURL2Name
                (query, match2publishF) = IMCEPlugin.nexusJavadocPOMResolveQueryURLAndPublishURL(
                  repoURL, repoName, moduleID)
                url <- nonFatalCatch[Option[URL]]
                  .withApply { (_: java.lang.Throwable) => None }
                  .apply({
                    val conn = query.openConnection.asInstanceOf[java.net.HttpURLConnection]
                    conn.setRequestMethod("GET")
                    conn.setDoOutput(true)
                    repoPathRegex
                      .findFirstMatchIn(Source.fromInputStream(conn.getInputStream).getLines.mkString)
                      .map { m =>
                        val javadocURL = match2publishF(m)
                        s.log.info(s"Javadoc for: $moduleID")
                        s.log.info(s"= mapped to: $javadocURL")
                        javadocURL
                      }
                  })
              } yield url
              urls.headOption
            }
          } yield jar.data -> url).toMap
      }
  )

resolvers := {
  val previous = resolvers.value
  if (git.gitUncommittedChanges.value)
    Seq[Resolver](Resolver.mavenLocal) ++ previous
  else
    previous
}

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

lazy val core = Project("oti-uml-magicdraw-adapter", file("."))
  .enablePlugins(IMCEGitPlugin)
  .enablePlugins(IMCEReleasePlugin)
  .settings(dynamicScriptsResourceSettings("org.omg.oti.uml.magicdraw.adapter"))
  .settings(IMCEPlugin.strictScalacFatalWarningsSettings)
  .settings(docSettings(diagrams=false))
  .settings(IMCEReleasePlugin.packageReleaseProcessSettings)
  .settings(
    IMCEKeys.licenseYearOrRange := "2014-2016",
    IMCEKeys.organizationInfo := IMCEPlugin.Organizations.oti,
    IMCEKeys.targetJDK := IMCEKeys.jdk17.value,

    organization := "org.omg.tiwg",
    organizationHomepage :=
      Some(url("http://www.omg.org/members/sysml-rtf-wiki/doku.php?id=rtf5:groups:tools_infrastructure:index")),

    buildInfoKeys ++= Seq[BuildInfoKey](BuildInfoKey.action("buildDateUTC") { buildUTCDate.value }),
    buildInfoPackage := "org.omg.oti.uml.magicdraw.adapter",

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

    scalaSource in Compile :=
      baseDirectory.value / "svn" / "src",

    resourceDirectory in Compile :=
      baseDirectory.value / "svn" / "resources",

    // disable publishing the jar produced by `test:package`
    publishArtifact in(Test, packageBin) := false,

    // disable publishing the test API jar
    publishArtifact in(Test, packageDoc) := false,

    // disable publishing the test sources jar
    publishArtifact in(Test, packageSrc) := false,

    unmanagedClasspath in Compile <++= unmanagedJars in Compile
  )
  .dependsOnSourceProjectOrLibraryArtifacts(
     "oti-uml-change_migration",
     "org.omg.oti.uml.change_migration",
     Seq(
        //  extra("artifact.kind" -> "generic.library")
       "org.omg.tiwg" %% "oti-uml-change_migration"
       % Versions_oti_uml_change_migration.version %
       "compile" withSources() withJavadoc() artifacts
       Artifact("oti-uml-change_migration", "zip", "zip", Some("resource"), Seq(), None, Map())
    )
  )
  .dependsOnSourceProjectOrLibraryArtifacts(
    "oti-uml-composite_structure_tree_analysis",
    "org.omg.oti.uml.composite_structure_tree_analysis",
    Seq(
//      //  extra("artifact.kind" -> "generic.library")
      "org.omg.tiwg" %% "oti-uml-composite_structure_tree_analysis"
        % Versions_oti_uml_composite_structure_tree_analysis.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("oti-uml-composite_structure_tree_analysis", "zip", "zip", Some("resource"), Seq(), None, Map())
    )
  )
  .dependsOnSourceProjectOrLibraryArtifacts(
    "oti-uml-canonical_xmi-loader",
    "org.omg.oti.uml.canonical_xmi.loader",
    Seq(
//      //  extra("artifact.kind" -> "generic.library")
      "org.omg.tiwg" %% "oti-uml-canonical_xmi-loader"
        % Versions_oti_uml_canonical_xmi_loader.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("oti-uml-canonical_xmi-loader", "zip", "zip", Some("resource"), Seq(), None, Map())
    )
  )
//  .dependsOnSourceProjectOrLibraryArtifacts(
//    "org-omg-oti-uml-json",
//    "org.omg.oti.uml.json",
//    Seq(
//      //      //  extra("artifact.kind" -> "generic.library")
//      "org.omg.tiwg" %% "org-omg-oti-uml-json"
//        % Versions_oti_uml_json.version %
//        "compile" withSources() withJavadoc() artifacts
//        Artifact("org-omg-oti-uml-json", "zip", "zip", Some("resource"), Seq(), None, Map())
//    )
//  )
  .dependsOnSourceProjectOrLibraryArtifacts(
    "org-omg-oti-uml-json-serialization",
    "org.omg.oti.uml.json.serialization",
    Seq(
      //      //  extra("artifact.kind" -> "generic.library")
      "org.omg.tiwg" %% "org-omg-oti-uml-json-serialization"
        % Versions_oti_uml_json_serialization.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("org-omg-oti-uml-json-serialization", "zip", "zip", Some("resource"), Seq(), None, Map())
    )
  )
  .settings(
    libraryDependencies +=
      "gov.nasa.jpl.imce.magicdraw.plugins" %% "imce_md18_0_sp6_dynamic-scripts"
        % Versions_imce_md18_0_sp6_dynamic_scripts.version %
        "compile" withSources() withJavadoc() artifacts
        Artifact("imce_md18_0_sp6_dynamic-scripts", "zip", "zip", Some("resource"), Seq(), None, Map()),

    extractArchives <<= (baseDirectory, update, streams) map {
      (base, up, s) =>

        val mdInstallDir = base / "target" / "md.package"
        if (!mdInstallDir.exists) {

          IO.createDirectory(mdInstallDir)

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

//          val mdDynamicScriptsDir = mdInstallDir / "dynamicScripts"
//          IO.createDirectory(mdDynamicScriptsDir)
//
//          val zfilter: DependencyFilter = new DependencyFilter {
//            def apply(c: String, m: ModuleID, a: Artifact): Boolean =
//              (a.`type` == "zip" || a.`type` == "resource") &&
//                a.extension == "zip" &&
//                m.organization == "org.omg.tiwg"
//          }
//          val zs: Seq[File] = up.matching(zfilter)
//          zs.foreach { zip =>
//            val files = IO.unzip(zip, mdDynamicScriptsDir)
//            s.log.info(
//              s"=> extracted ${files.size} DynamicScripts files from zip: ${zip.getName}")
//          }

          val mdBinFolder = mdInstallDir / "bin"
          require(mdBinFolder.exists, "md bin: $mdBinFolder")

        } else {
          s.log.info(
            s"=> use existing md.install.dir=$mdInstallDir")
        }

    },

    unmanagedJars in Compile <++= (baseDirectory, update, streams, extractArchives) map {
      (base, up, s, _) =>

        val mdInstallDir = base / "target" / "md.package"

        val libJars = ((mdInstallDir / "lib") ** "*.jar").get
        s.log.info(s"jar libraries: ${libJars.size}")

        //val dsJars = ((mdInstallDir / "dynamicScripts") * "*" / "lib" ** "*.jar").get
        //s.log.info(s"jar dynamic script: ${dsJars.size}")

        //val mdJars = (libJars ++ dsJars).map { jar => Attributed.blank(jar) }
        val mdJars = libJars.map { jar => Attributed.blank(jar) }

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
        val dir = base / "svn"
        val file2name = (dir ** "*.dynamicScripts").pair(rebase(dir, projectName)) ++
          (dir / "profiles" ** "*.mdzip").pair(rebase(dir, "..")) ++
          (dir / "resources" ***).pair(rebase(dir, projectName)) ++
          addIfExists(bin, projectName + "/lib/" + bin.name) ++
          addIfExists(binT, projectName + "/lib/" + binT.name) ++
          addIfExists(src, projectName + "/lib.sources/" + src.name) ++
          addIfExists(srcT, projectName + "/lib.sources/" + srcT.name) ++
          addIfExists(doc, projectName + "/lib.javadoc/" + doc.name) ++
          addIfExists(docT, projectName + "/lib.javadoc/" + docT.name)

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