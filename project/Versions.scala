
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

  /** @see http://mvnrepository.com/artifact/xml-resolver/xml-resolver/ */
  val xmlResolver = "1.2"

  // OTI MagicDraw Binding

  val version = version_prefix + "-" + version_suffix
  
  // OTI Core version
    
  val oti_core_prefix = "0.14.0"
  val oti_core_suffix = "928"
  val oti_core_version = oti_core_prefix+"-"+oti_core_suffix

  // OTI Change Migration version
    
  val oti_changeMigration_prefix = "0.14.0"
  val oti_changeMigration_suffix = "929"
  val oti_changeMigration_version = oti_changeMigration_prefix+"-"+oti_changeMigration_suffix

  // OTI Trees version
    
  val oti_trees_prefix = "0.14.0"
  val oti_trees_suffix = "930"
  val oti_trees_version = oti_trees_prefix+"-"+oti_trees_suffix

}
