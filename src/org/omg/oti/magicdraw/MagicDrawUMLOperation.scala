package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.postfixOps

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLOperation 
  extends UMLOperation[MagicDrawUML]
  with MagicDrawUMLBehavioralFeature
  with MagicDrawUMLParameterableElement
  with MagicDrawUMLTemplateableElement {

  override protected def e: Uml#Operation
  import ops._
  
  override def bodyCondition = Option.apply( e.getBodyCondition )
  
  override def isOrdered = e.isOrdered
  
  override def isQuery = e.isQuery
  
  override def isUnique = e.isQuery
  
  override def lower = e.getLower
    
  override def ownedParameter = e.getOwnedParameter.toSeq
  
  override def postcondition = e.getPostcondition.toSet[Uml#Constraint]

  override def precondition = e.getPrecondition.toSet[Uml#Constraint]
  
  override def raisedException = e.getRaisedException.toSet[Uml#Type]
  
  override def operation_templateParameter = Option.apply( e.getTemplateParameter )
  
  override def _type = Option.apply( e.getType )
  
  override def upper = e.getUpper
  
  override def operation_callOperationAction = e.get_callOperationActionOfOperation.toSet[Uml#CallOperationAction]
  
  override def referred_protocolTransition = ???
  
  override def operation_callEvent = e.get_callEventOfOperation.toSet[Uml#CallEvent]
}