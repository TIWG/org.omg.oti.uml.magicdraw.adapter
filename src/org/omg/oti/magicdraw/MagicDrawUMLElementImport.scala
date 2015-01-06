package org.omg.oti.magicdraw

import org.omg.oti.UMLElementImport

trait MagicDrawUMLElementImport extends UMLElementImport[MagicDrawUML] with MagicDrawUMLDirectedRelationship {
  override protected def e: Uml#ElementImport
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}