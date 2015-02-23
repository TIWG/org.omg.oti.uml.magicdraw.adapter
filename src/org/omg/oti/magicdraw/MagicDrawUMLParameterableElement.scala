package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLParameterableElement 
  extends UMLParameterableElement[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#ParameterableElement
  import ops._

  override def templateParameter = Option.apply( e.getTemplateParameter )
  
  override def actual_templateParameterSubstitution = e.get_templateParameterSubstitutionOfActual.toIterable
  
  override def default_templateParameter = e.get_templateParameterOfDefault.toIterable
  
}
