/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2016, California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * *   Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * *   Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 * *   Neither the name of Caltech nor its operating division, the Jet
 *    Propulsion Laboratory, nor the names of its contributors may be
 *    used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw.uml.read

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.collection.Iterable

import org.omg.oti.uml.read.api._

import scala.Boolean
import scala.Option

trait MagicDrawUMLBehavior 
  extends MagicDrawUMLClass
  with UMLBehavior[MagicDrawUML] {

  override protected def e: Uml#Behavior
  def getMagicDrawBehavior = e
  override implicit val umlOps = ops
  import umlOps._
  
  override def isReentrant: Boolean =
    e.isReentrant
  
  override def ownedParameter: Seq[UMLParameter[Uml]] =
    e.getOwnedParameter.to[Seq]
  
  override def postcondition: Set[UMLConstraint[Uml]] =
    e.getPostcondition.to[Set]
  
  override def precondition: Set[UMLConstraint[Uml]] =
    e.getPrecondition.to[Set]
  
  override def specification: Option[UMLBehavioralFeature[Uml]] =
    for { result <- Option.apply( e.getSpecification ) } yield result
  
  override def redefinedBehavior: Set[UMLBehavior[Uml]] =
    e.getRedefinedBehavior.to[Set]
  
  override def selection_objectFlow: Set[UMLObjectFlow[Uml]] =
    e.get_objectFlowOfSelection.to[Set]
  
  override def behavior_callBehaviorAction: Set[UMLCallBehaviorAction[Uml]] =
    e.get_callBehaviorActionOfBehavior.to[Set]
  
  override def behavior_behaviorExecutionSpecification: Set[UMLBehaviorExecutionSpecification[Uml]] =
    e.get_behaviorExecutionSpecificationOfBehavior.to[Set]
  
  override def decisionInput_decisionNode: Set[UMLDecisionNode[Uml]] =
    e.get_decisionNodeOfDecisionInput.to[Set]
  
  override def reducer_reduceAction: Set[UMLReduceAction[Uml]] =
    e.get_reduceActionOfReducer.to[Set]
  
  override def selection_objectNode: Set[UMLObjectNode[Uml]] =
    e.get_objectNodeOfSelection.to[Set]
  
  override def transformation_objectFlow: Set[UMLObjectFlow[Uml]] =
    e.get_objectFlowOfTransformation.to[Set]
  
  override def redefinedBehavior_behavior: Set[UMLBehavior[Uml]] =
    e.getRedefinedBehavior.to[Set]
  
  override def behavior_opaqueExpression: Set[UMLOpaqueExpression[Uml]] =
    e.get_opaqueExpressionOfBehavior.to[Set]
  
  override def contract_connector: Set[UMLConnector[Uml]] =
    e.get_connectorOfContract.to[Set]
  
  override def ownedBehavior_behavioredClassifier
  : Option[UMLBehavioredClassifier[Uml]] =
    for { result <- Option.apply(e.get_behavioredClassifierOfOwnedBehavior) } yield result
    
  override def doActivity_state
  : Option[UMLState[Uml]] =
    for { result <- Option.apply(e.get_stateOfDoActivity) } yield result
  
  override def entry_state
  : Option[UMLState[Uml]] =
    for { result <- Option.apply(e.get_stateOfEntry) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLState.exit
	 */
	override def exit_state
  : Option[UMLState[Uml]] =
    for { result <-  Option.apply(e.get_stateOfExit) } yield result
  
}