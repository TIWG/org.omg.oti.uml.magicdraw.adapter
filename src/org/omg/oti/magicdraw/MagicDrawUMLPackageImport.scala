package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLPackageImport 
  extends UMLPackageImport[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  import ops._
  override protected def e: Uml#PackageImport

  // 12.12
  override def metamodelReference_profile: Option[UMLProfile[Uml]] = ???
}