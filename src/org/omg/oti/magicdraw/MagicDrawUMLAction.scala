package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLAction 
  extends UMLAction[MagicDrawUML]
  with MagicDrawUMLExecutableNode {

  override protected def e: Uml#Action
  import ops._

  override def context = Option.apply( e.getContext )
  
  override def input = e.getInput.toSeq
  
  override def isLocallyReentrant = e.isLocallyReentrant
  
  override def localPostcondition = e.getLocalPostcondition.toSet[Uml#Constraint]
  
  override def localPrecondition = e.getLocalPrecondition.toSet[Uml#Constraint]
  
  override def output = e.getOutput.toSeq
  
  override def action_interaction = Option.apply( e.get_interactionOfAction )
  
  override def fromAction_actionInputPin = Option.apply( e.get_actionInputPinOfFromAction )
  
}
