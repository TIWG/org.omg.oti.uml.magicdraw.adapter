package org.omg.oti.magicdraw

import org.omg.oti.UMLConnectableElement

trait MagicDrawUMLConnectableElement extends UMLConnectableElement[MagicDrawUML] with MagicDrawUMLTypedElement {
  override protected def e: Uml#ConnectableElement
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}