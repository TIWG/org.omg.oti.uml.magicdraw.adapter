package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateParameterSubstitution 
  extends UMLTemplateParameterSubstitution[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#TemplateParameterSubstitution
  import ops._

  // 7.4  
	override def actual: Option[UMLParameterableElement[Uml]] = ???
  
  // 7.4
  override def formal: Option[UMLTemplateParameter[Uml]] = ???
}
