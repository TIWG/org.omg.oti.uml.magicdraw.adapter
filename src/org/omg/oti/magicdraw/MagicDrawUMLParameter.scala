package org.omg.oti.magicdraw

import org.omg.oti.UMLParameter

trait MagicDrawUMLParameter extends UMLParameter[MagicDrawUML] with MagicDrawUMLConnectableElement with MagicDrawUMLMultiplicityElement {
  override protected def e: Uml#Parameter
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}