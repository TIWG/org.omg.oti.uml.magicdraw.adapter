package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLConstraint 
  extends UMLConstraint[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#Constraint
  import ops._
  
  override def constrainedElement = 
    for { c <- e.getConstrainedElement } yield umlElement( c )
  
  override def bodyCondition_bodyContext =
    Option.apply( e.getBodyContext )
    
  override def condition_extend =
    Option.apply( e.get_extendOfCondition )
    
  override def condition_parameterSet =
    Option.apply( e.get_parameterSetOfCondition )
    
  override def context =
    Option.apply( e.getContext )
    
  override def guard_transition =
    Option.apply( e.get_transitionOfGuard )
    
  override def invariant_stateInvariant =
    Option.apply( e.get_stateInvariantOfInvariant )
    
  override def localPostcondition_action =
    Option.apply( e.get_actionOfLocalPostcondition )

  override def localPrecondition_action =
    Option.apply( e.get_actionOfLocalPrecondition )
    
  override def postCondition_owningTransition =
    Option.apply( e.getOwningTransition )
    
  override def postcondition_behavior =
    Option.apply( e.get_behaviorOfPrecondition )
    
  override def postcondition_postContext =
    Option.apply( e.getPostContext )
    
  override def preCondition_protocolTransition =
    Option.apply( e.get_protocolTransitionOfPreCondition )
    
  override def precondition_behavior =
    Option.apply( e.get_behaviorOfPrecondition )
    
  override def precondition_preContext =
    Option.apply( e.getPreContext )
    
  override def specification: Option[UMLValueSpecification[Uml]] =
    Option.apply( e.getSpecification )
    
  override def stateInvariant_owningState =
    Option.apply( e.getOwningState )
    
}