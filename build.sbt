import java.io.File
import java.nio.file.Files
import sbt.Keys._
import sbt._

import scala.collection.JavaConversions._

import gov.nasa.jpl.imce.sbt._

useGpg := true

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

mdInstallDirectory in Global :=
  baseDirectory.value / "imce.md.package" / ("imce.md18_0sp5.dynamic-scripts-" + Versions.version)

lazy val artifactZipFile = taskKey[File]("Location of the zip artifact file")

lazy val extractArchives = TaskKey[Seq[Attributed[File]]]("extract-archives", "Extracts ZIP files")

lazy val updateInstall = TaskKey[Unit]("update-install", "Update the MD Installation directory")

lazy val md5Install = TaskKey[Unit]("md5-install", "Produce an MD5 report of the MD Installation directory")

lazy val zipInstall = TaskKey[File]("zip-install", "Zip the MD Installation directory")

lazy val buildUTCDate = SettingKey[String]("build-utc-date", "The UDC Date of the build")

buildUTCDate in Global := {
  import java.util.{ Date, TimeZone }
  val formatter = new java.text.SimpleDateFormat("yyyy-MM-dd-HH:mm")
  formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
  formatter.format(new Date)
}

lazy val core = Project("oti-uml-magicdraw-adapter", file("."))
  .enablePlugins(IMCEGitPlugin)
  .enablePlugins(IMCEReleasePlugin)
  .settings(dynamicScriptsResourceSettings(Some("org.omg.oti.uml.magicdraw.adapter")))
  .settings(IMCEPlugin.strictScalacFatalWarningsSettings)
  .settings(IMCEPlugin.scalaDocSettings(diagrams=false))
  .settings(
    IMCEKeys.licenseYearOrRange := "2014-2016",
    IMCEKeys.organizationInfo := IMCEPlugin.Organizations.oti,
    IMCEKeys.targetJDK := IMCEKeys.jdk17.value,

    organization := "org.omg.tiwg",
    organizationHomepage :=
      Some(url("http://www.omg.org/members/sysml-rtf-wiki/doku.php?id=rtf5:groups:tools_infrastructure:index")),

    buildInfoPackage := "org.omg.oti.uml.magicdraw.adapter",
    buildInfoKeys ++= Seq[BuildInfoKey](BuildInfoKey.action("buildDateUTC") { buildUTCDate.value }),

    artifactZipFile := {
      baseDirectory.value / "target" / "imce_md18_0_sp5_oti-uml-magicdraw-adapter_resource.zip"
    },

    addArtifact(Artifact("imce_md18_0_sp5_oti-uml-magicdraw-adapter_resource", "zip", "zip"), artifactZipFile),

    mappings in (Compile, packageSrc) ++= {
      import Path.{flat, relativeTo}
      val base = (sourceManaged in Compile).value
      val srcs = (managedSources in Compile).value
      srcs x (relativeTo(base) | flat)
    },

    projectID := {
      val previous = projectID.value
      previous.extra("build.date.utc" -> buildUTCDate.value)
    },

    git.baseVersion := Versions.version,
    organizationName := "JPL, Caltech, Airbus & Object Management Group",
    organizationHomepage := Some(url("http://solitaire.omg.org/browse/TIWG")),

    scalaSource in Compile := baseDirectory.value / "svn" / "org.omg.oti.magicdraw" / "src",

    classDirectory in Compile := baseDirectory.value / "svn" / "org.omg.oti.magicdraw" / "bin",
    cleanFiles += (classDirectory in Compile).value,

    resourceDirectory in Compile := baseDirectory.value / "svn" / "org.omg.oti.magicdraw" / "resources",

    unmanagedClasspath in Compile <++= unmanagedJars in Compile,
    libraryDependencies ++= Seq (
      "org.omg.tiwg" %% "oti-uml-change_migration"
        % Versions.oti_uml_change_migration % "compile" withSources() withJavadoc(),

      "org.omg.tiwg" %% "oti-uml-composite_structure_tree_analysis"
        % Versions.oti_uml_composite_structure_tree_analysis % "compile" withSources() withJavadoc(),

      "org.omg.tiwg" %% "oti-uml-canonical_xmi-loader"
        % Versions.oti_uml_canonical_xmi_loader % "compile" withSources() withJavadoc(),

      "gov.nasa.jpl.imce.magicdraw.packages" %% "imce_md18_0_sp5_dynamic-scripts" 
        % Versions.dynamic_scripts_package % "compile" 
	artifacts Artifact("imce_md18_0_sp5_dynamic-scripts", "zip", "zip")

    ),

    IMCEKeys.nexusJavadocRepositoryRestAPIURL2RepositoryName := Map(
       "https://oss.sonatype.org/service/local" -> "releases",
       "https://cae-nexuspro.jpl.nasa.gov/nexus/service/local" -> "JPL",
       "https://cae-nexuspro.jpl.nasa.gov/nexus/content/groups/jpl.beta.group" -> "JPL Beta Group",
       "https://cae-nexuspro.jpl.nasa.gov/nexus/content/groups/jpl.public.group" -> "JPL Public Group"),
    IMCEKeys.pomRepositoryPathRegex := """\<repositoryPath\>\s*([^\"]*)\s*\<\/repositoryPath\>""".r,

    extractArchives <<= (baseDirectory, libraryDependencies, update, streams,
      mdInstallDirectory in Global, scalaBinaryVersion) map {
      (base, libs, up, s, mdInstallDir, sbV) =>

        if (!mdInstallDir.exists) {

          val zfilter: DependencyFilter = new DependencyFilter {
            def apply(c: String, m: ModuleID, a: Artifact): Boolean = {
              val ok1 = a.`type` == "zip" && a.extension == "zip"
              val ok2 = libs.find { dep: ModuleID =>
                ok1 && dep.organization == m.organization && m.name == dep.name + "_" + sbV
              }
              ok2.isDefined
            }
          }
          val zs: Seq[File] = up.matching(zfilter)
          zs.foreach { zip =>
            val files = IO.unzip(zip, mdInstallDir)
            s.log.info(
              s"=> created md.install.dir=$mdInstallDir with ${files.size} " +
                s"files extracted from zip: ${zip.getName}")
          }
          val mdRootFolder = mdInstallDir / s"imce.md18_0sp5.dynamic-scripts-${Versions.dynamic_scripts_package}"
          require(
            mdRootFolder.exists && mdRootFolder.canWrite,
            s"mdRootFolder: $mdRootFolder")
          IO.listFiles(mdRootFolder).foreach { f =>
            val fp = f.toPath
            Files.move(
              fp,
              mdInstallDir.toPath.resolve(fp.getFileName),
              java.nio.file.StandardCopyOption.REPLACE_EXISTING)
          }
          IO.delete(mdRootFolder)

          val mdBinFolder = mdInstallDir / "bin"
          require(mdBinFolder.exists, "md bin: $mdBinFolder")

        } else {
          s.log.info(
            s"=> use existing md.install.dir=$mdInstallDir")
        }

        val libPath = (mdInstallDir / "lib").toPath
        val mdJars = for {
          jar <- Files.walk(libPath).iterator().filter(_.toString.endsWith(".jar")).map(_.toFile)
        } yield Attributed.blank(jar)

        mdJars.toSeq
    },

    unmanagedJars in Compile <++= extractArchives,

    compile <<= (compile in Compile) dependsOn extractArchives,

    publish <<= publish dependsOn zipInstall,
    PgpKeys.publishSigned <<= PgpKeys.publishSigned dependsOn zipInstall,

    publishLocal <<= publishLocal dependsOn zipInstall,
    PgpKeys.publishLocalSigned <<= PgpKeys.publishLocalSigned dependsOn zipInstall,

    zipInstall <<=
      (baseDirectory, update, streams,
        mdInstallDirectory in Global,
        artifactZipFile,
        packageBin in Compile,
        packageSrc in Compile,
        packageDoc in Compile,
        makePom, buildUTCDate,
        scalaBinaryVersion
        ) map {
        (base, up, s, mdInstallDir, zip, libJar, libSrc, libDoc, pom, d, sbV) =>

          import com.typesafe.sbt.packager.universal._

          val root = base / "target" / "imce_md18_0_sp5_oti-uml-magicdraw-adapter_resource"
          s.log.info(s"\n*** top: $root")

          IO.copyDirectory(base / "profiles", root / "profiles/", overwrite=true, preserveLastModified=true)

          val pluginDir = root / "plugins" / "gov.nasa.jpl.magicdraw.dynamicScripts"
          IO.createDirectory(pluginDir)

          IO.copyFile(libJar, pluginDir / "lib" / libJar.getName)
          IO.copyFile(libSrc, pluginDir / "lib" / libSrc.getName)
          IO.copyFile(libDoc, pluginDir / "lib" / libDoc.getName)

          val lfilter: DependencyFilter = new DependencyFilter {
            def apply(c: String, m: ModuleID, a: Artifact): Boolean = {
              val ok1 = "compile" == c
              val ok2 = a.`type` == "jar" && a.extension == "jar"
              val ok3 =
                "gov.nasa.jpl.imce.secae" == m.organization &&
                  "jpl-dynamic-scripts-generic-dsl_" + sbV == m.name
              ok1 && ok2 && ok3
            }
          }
          val ls: Seq[File] = up.matching(lfilter)
          ls.foreach { libJar: File =>
            IO.copyFile(libJar, pluginDir / "lib" / libJar.getName)
          }

          val dfilter: DependencyFilter = new DependencyFilter {
            def apply(c: String, m: ModuleID, a: Artifact): Boolean = {
              val ok1 = "compile" == c
              val ok2 = a.`type` == "doc" && a.extension == "jar"
              val ok3 =
                "gov.nasa.jpl.imce.secae" == m.organization &&
                  "jpl-dynamic-scripts-generic-dsl_" + sbV == m.name
              ok1 && ok2 && ok3
            }
          }
          val ds: Seq[File] = up.matching(dfilter)
          ds.foreach { libDoc: File =>
            IO.copyFile(libDoc, pluginDir / "lib" / libDoc.getName)
          }

          val sfilter: DependencyFilter = new DependencyFilter {
            def apply(c: String, m: ModuleID, a: Artifact): Boolean = {
              val ok1 = "compile" == c
              val ok2 = a.`type` == "src" && a.extension == "jar"
              val ok3 =
                "gov.nasa.jpl.imce.secae" == m.organization &&
                  "jpl-dynamic-scripts-generic-dsl_" + sbV == m.name
              ok1 && ok2 && ok3
            }
          }
          val ss: Seq[File] = up.matching(sfilter)
          ss.foreach { libSrc: File =>
            IO.copyFile(libSrc, pluginDir / "lib" / libSrc.getName)
          }

          val resourceManager = root / "data" / "resourcemanager"
          IO.createDirectory(resourceManager)
          val resourceDescriptorFile = resourceManager / "MDR_Plugin_govnasajpldynamicScriptsmagicdraw_72516_descriptor.xml"
          val resourceDescriptorInfo =
            <resourceDescriptor critical="false" date={d}
                                description="IMCE Dynamic Scripts Plugin"
                                group="IMCE Resource"
                                homePage="https://github.jpl.nasa.gov/imce/jpl-dynamicscripts-magicdraw-plugin"
                                id="72516"
                                mdVersionMax="higher"
                                mdVersionMin="18.0"
                                name="IMCE Dynamic Scripts Plugin"
                                product="IMCE Dynamic Scripts Plugin"
                                restartMagicdraw="false" type="Plugin">
              <version human={Versions.version} internal={Versions.version} resource={Versions.version + "0"}/>
              <provider email="nicolas.f.rouquette@jpl.nasa.gov"
                        homePage="https://github.jpl.nasa.gov/imce/jpl-dynamicscripts-magicdraw-plugin"
                        name="IMCE"/>
              <edition>Reader</edition>
              <edition>Community</edition>
              <edition>Standard</edition>
              <edition>Professional Java</edition>
              <edition>Professional C++</edition>
              <edition>Professional C#</edition>
              <edition>Professional ArcStyler</edition>
              <edition>Professional EFFS ArcStyler</edition>
              <edition>OptimalJ</edition>
              <edition>Professional</edition>
              <edition>Architect</edition>
              <edition>Enterprise</edition>
              <requiredResource id="1440">
                <minVersion human="17.0" internal="169010"/>
              </requiredResource>
              <installation>
                <file from="plugins/gov.nasa.jpl.magicdraw.dynamicScripts/plugin.xml"
                      to="plugins/gov.nasa.jpl.magicdraw.dynamicScripts/plugin.xml"/>

                <file from={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+libJar.getName}
                      to={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+libJar.getName}/>
                <file from={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+libDoc.getName}
                      to={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+libDoc.getName}/>
                <file from={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+libSrc.getName}
                      to={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+libSrc.getName}/>
                {ls.map { l =>
                  <file from={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+l.getName}
                        to={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+l.getName}/> }
                }
                {ds.map { l =>
                  <file from={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+l.getName}
                        to={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+l.getName}/> }
                }
                {ss.map { l =>
                  <file from={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+l.getName}
                        to={"plugins/gov.nasa.jpl.magicdraw.dynamicScripts/lib/"+l.getName}/> }
                }
              </installation>
            </resourceDescriptor>

          xml.XML.save(
            filename=resourceDescriptorFile.getAbsolutePath,
            node=resourceDescriptorInfo,
            enc="UTF-8")

          val fileMappings = (root.*** --- root) pair relativeTo(root)
          ZipHelper.zipNIO(fileMappings, zip)

          s.log.info(s"\n*** Created the zip: $zip")
          zip
      }
  )
  .settings(IMCEPlugin.strictScalacFatalWarningsSettings)
  .settings(IMCEReleasePlugin.packageReleaseProcessSettings)

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
      Some("dynamicScripts/" + projectName)
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
      packageDoc in Test) map {
      (base, bin, src, doc, binT, srcT, docT) =>
        val dir = base / "svn" / "org.omg.oti.magicdraw"
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

    artifacts <+= (name in Universal) { n => Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map()) },
    packagedArtifacts <+= (packageBin in Universal, name in Universal) map { (p, n) =>
      Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map()) -> p
    }
  )
}