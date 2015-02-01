package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLTypedElement extends UMLTypedElement[MagicDrawUML] with MagicDrawUMLNamedElement {
  override protected def e: Uml#TypedElement
  
  import ops._
  
  def _type: Option[UMLType[Uml]] = Option.apply( e.getType )
  
}