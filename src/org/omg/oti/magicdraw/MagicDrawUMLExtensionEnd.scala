package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLExtensionEnd 
  extends UMLExtensionEnd[MagicDrawUML]
  with MagicDrawUMLProperty {

  override protected def e: Uml#ExtensionEnd
  import ops._
  
  override def lower: Integer = ???
  
  override def _type: Option[UMLStereotype[Uml]] = super[MagicDrawUMLProperty]._type.selectByKindOf { case s: UMLStereotype[Uml] => s }
  
}