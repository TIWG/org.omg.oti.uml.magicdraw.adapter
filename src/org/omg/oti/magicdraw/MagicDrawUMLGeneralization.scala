package org.omg.oti.magicdraw

import org.omg.oti.UMLGeneralization

trait MagicDrawUMLGeneralization extends UMLGeneralization[MagicDrawUML] with MagicDrawUMLDirectedRelationship {
  override protected def e: Uml#Generalization
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}