package org.omg.oti.magicdraw

import org.omg.oti.UMLPackageImport

trait MagicDrawUMLPackageImport extends UMLPackageImport[MagicDrawUML] with MagicDrawUMLDirectedRelationship {
  override protected def e: Uml#PackageImport
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}