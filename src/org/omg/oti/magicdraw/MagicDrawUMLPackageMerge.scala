package org.omg.oti.magicdraw

import org.omg.oti.UMLPackageMerge

trait MagicDrawUMLPackageMerge extends UMLPackageMerge[MagicDrawUML] with MagicDrawUMLDirectedRelationship {
  override protected def e: Uml#PackageMerge
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}