package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLParameterableElement 
  extends UMLParameterableElement[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#ParameterableElement
  import ops._

  override def templateParameter: Option[UMLTemplateParameter[Uml]] = Option.apply( e.getTemplateParameter )
  
  override def actual_templateParameterSubstitution = e.get_templateParameterSubstitutionOfActual.toSet[Uml#TemplateParameterSubstitution]
  
  override def default_templateParameter = e.get_templateParameterOfDefault.toIterable
  
  override def ownedDefault_templateParameter: Iterable[UMLTemplateParameter[Uml]] =
    e.get_templateParameterOfDefault.toIterable
        
  override def owningTemplateParameter: Option[UMLTemplateParameter[Uml]] =
    Option.apply(e.getOwningTemplateParameter)
    
}
