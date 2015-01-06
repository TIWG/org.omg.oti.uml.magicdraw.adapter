package org.omg.oti.magicdraw

import org.omg.oti.UMLPort

trait MagicDrawUMLPort extends UMLPort[MagicDrawUML] with MagicDrawUMLProperty {
  override protected def e: Uml#Port
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}