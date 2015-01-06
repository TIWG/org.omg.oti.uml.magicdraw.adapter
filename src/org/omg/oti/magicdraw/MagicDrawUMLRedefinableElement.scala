package org.omg.oti.magicdraw

import org.omg.oti.UMLRedefinableElement

trait MagicDrawUMLRedefinableElement extends UMLRedefinableElement[MagicDrawUML] with MagicDrawUMLNamedElement {
  override protected def e: Uml#RedefinableElement
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}