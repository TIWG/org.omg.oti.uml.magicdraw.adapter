package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLPackageMerge 
  extends UMLPackageMerge[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  import ops._
  override protected def e: Uml#PackageMerge

  
}