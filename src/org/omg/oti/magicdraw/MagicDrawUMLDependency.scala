package org.omg.oti.magicdraw

import org.omg.oti.UMLDependency

trait MagicDrawUMLDependency 
extends UMLDependency[MagicDrawUML] 
with MagicDrawUMLDirectedRelationship 
with MagicDrawUMLPackageableElement {
  override protected def e: Uml#Dependency
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}