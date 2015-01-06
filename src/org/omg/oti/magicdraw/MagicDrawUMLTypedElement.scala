package org.omg.oti.magicdraw

import org.omg.oti.UMLTypedElement

trait MagicDrawUMLTypedElement extends UMLTypedElement[MagicDrawUML] with MagicDrawUMLNamedElement {
  override protected def e: Uml#TypedElement
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def getType = Option.apply( e.getType )
}