/*
 *
 *  License Terms
 *
 *  Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
 *  U.S. Government sponsorship acknowledged.
 *
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *
 *   *   Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *   *   Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the
 *       distribution.
 *
 *   *   Neither the name of Caltech nor its operating division, the Jet
 *       Propulsion Laboratory, nor the names of its contributors may be
 *       used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *  PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.uml.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._

trait MagicDrawUMLConstraint 
  extends UMLConstraint[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#Constraint
  import ops._
  
  override def constrainedElement: Seq[UMLElement[Uml]] =
    for { c <- e.getConstrainedElement } yield umlElement( c )
  
  override def bodyCondition_bodyContext: Option[UMLOperation[Uml]] =
    Option.apply( e.getBodyContext )
    
  override def condition_extend: Option[UMLExtend[Uml]] =
    Option.apply( e.get_extendOfCondition )
    
  override def condition_parameterSet: Option[UMLParameterSet[Uml]] =
    Option.apply( e.get_parameterSetOfCondition )
    
  override def context: Option[UMLNamespace[Uml]] =
    Option.apply( e.getContext )
    
  override def guard_transition: Option[UMLTransition[Uml]] =
    Option.apply( e.get_transitionOfGuard )
    
  override def invariant_stateInvariant: Option[UMLStateInvariant[Uml]] =
    Option.apply( e.get_stateInvariantOfInvariant )
    
  override def localPostcondition_action: Option[UMLAction[Uml]] =
    Option.apply( e.get_actionOfLocalPostcondition )

  override def localPrecondition_action: Option[UMLAction[Uml]] =
    Option.apply( e.get_actionOfLocalPrecondition )
    
  override def postCondition_owningTransition: Option[UMLProtocolTransition[Uml]] =
    Option.apply( e.getOwningTransition )
    
  override def postcondition_behavior: Option[UMLBehavior[Uml]] =
    Option.apply( e.get_behaviorOfPrecondition )
    
  override def postcondition_postContext: Option[UMLOperation[Uml]] =
    Option.apply( e.getPostContext )
    
  override def preCondition_protocolTransition: Option[UMLProtocolTransition[Uml]] =
    Option.apply( e.get_protocolTransitionOfPreCondition )
    
  override def precondition_behavior: Option[UMLBehavior[Uml]] =
    Option.apply( e.get_behaviorOfPrecondition )
    
  override def precondition_preContext: Option[UMLOperation[Uml]] =
    Option.apply( e.getPreContext )
    
  override def specification: Option[UMLValueSpecification[Uml]] =
    Option.apply( e.getSpecification )
    
  override def stateInvariant_owningState: Option[UMLState[Uml]] =
    Option.apply( e.getOwningState )
    
}