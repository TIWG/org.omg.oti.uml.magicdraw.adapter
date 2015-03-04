package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLConnectableElement 
  extends UMLConnectableElement[MagicDrawUML]
  with MagicDrawUMLTypedElement
  with MagicDrawUMLParameterableElement {

  override protected def e: Uml#ConnectableElement
  import ops._

  override def end = e.getEnd.toSet[Uml#ConnectorEnd]
  
  override def templateParameter = Option.apply( e.getTemplateParameter )
  
  override def collaborationRole_collaboration = e.get_collaborationOfCollaborationRole.toSet[Uml#Collaboration]
  
  override def represents_lifeline = e.get_lifelineOfRepresents.toSet[Uml#Lifeline]
  
  override def role_structuredClassifier = e.get_structuredClassifierOfRole.toIterable
  
}
