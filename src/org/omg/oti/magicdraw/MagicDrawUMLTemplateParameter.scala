package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateParameter 
  extends UMLTemplateParameter[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#TemplateParameter
  import ops._

  override def default: Option[UMLParameterableElement[Uml]] = ???
  
  override def ownedParameteredElement: Option[UMLParameterableElement[Uml]] = 
    Option.apply( e.getOwnedParameteredElement )
  
  override def ownedDefault: Option[UMLParameterableElement[Uml]] = ???
  
  override def parameteredElement: Option[UMLParameterableElement[Uml]] = ???
  
}
