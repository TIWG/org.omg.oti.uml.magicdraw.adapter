package org.omg.oti.magicdraw

import org.omg.oti.UMLExtension

trait MagicDrawUMLExtension extends UMLExtension[MagicDrawUML] with MagicDrawUMLAssociation {
  override protected def e: Uml#Extension
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}