
import sbt.Process
object Versions {
  val scala = "2.11.6"
  
  val version_prefix = "0.14.0"
  val version_suffix = {
    val svnProc = Process(command = "svn", arguments = Seq("info"))
    val sedCommand = "s/^.*Last Changed Rev: \\([[:digit:]]\\{1,\\}\\).*$/\\1/p"
    val sedProc = Process(command = "sed", arguments = Seq("-n", sedCommand))
    val svnRevision = svnProc.#|(sedProc).!!.trim
    svnRevision
  }
  
  // OTI MagicDraw Binding

  val version = version_prefix + "-" + version_suffix
  
  // OTI Core version
    
  val oti_core_prefix = "0.14.0"
  val oti_core_suffix = "769"
  val oti_core_version = oti_core_prefix+"-"+oti_core_suffix
  
  // OTI Change Migration version
    
  val oti_changeMigration_prefix = "0.14.0"
  val oti_changeMigration_suffix = "772"
  val oti_changeMigration_version = oti_changeMigration_prefix+"-"+oti_changeMigration_suffix
    
  // OTI Trees version
    
  val oti_trees_prefix = "0.14.0"
  val oti_trees_suffix = "770"
  val oti_trees_version = oti_trees_prefix+"-"+oti_trees_suffix

  // JPL MBEE Common Scala Libraries
  val jpl_mbee_common_scala_libraries_revision="650c69e6e7defc0b3e430d9c3cb290e8b3cc1f88"
  val jpl_mbee_core = "1800.02-"+jpl_mbee_common_scala_libraries_revision
  val jpl_mbee_other = "1800.02-"+jpl_mbee_common_scala_libraries_revision
  val jpl_owlapi = "1800.02-"+jpl_mbee_common_scala_libraries_revision

}
