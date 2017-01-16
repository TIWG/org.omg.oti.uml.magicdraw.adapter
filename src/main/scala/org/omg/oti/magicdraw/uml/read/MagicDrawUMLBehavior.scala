/*
 * Copyright 2014 California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * License Terms
 */

package org.omg.oti.magicdraw.uml.read

import scala.collection.JavaConversions._
import scala.collection.immutable._

import org.omg.oti.uml.read.api._

import scala.Boolean
import scala.Option

trait MagicDrawUMLBehavior 
  extends MagicDrawUMLClass
  with UMLBehavior[MagicDrawUML] {

  override protected def e: Uml#Behavior
  def getMagicDrawBehavior: Uml#Behavior = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  
  override def isReentrant: Boolean = e.isReentrant
  
  override def ownedParameter
  : Seq[UMLParameter[Uml]]
  = e.getOwnedParameter.to[Seq]
  
  override def postcondition
  : Set[UMLConstraint[Uml]]
  = e.getPostcondition.to[Set]
  
  override def precondition
  : Set[UMLConstraint[Uml]]
  = e.getPrecondition.to[Set]
  
  override def specification
  : Option[UMLBehavioralFeature[Uml]]
  = for { result <- Option.apply( e.getSpecification ) } yield result
  
  override def redefinedBehavior
  : Set[UMLBehavior[Uml]]
  = e.getRedefinedBehavior.to[Set]
  
  override def selection_objectFlow
  : Set[UMLObjectFlow[Uml]]
  = e.get_objectFlowOfSelection.to[Set]
  
  override def behavior_callBehaviorAction
  : Set[UMLCallBehaviorAction[Uml]]
  = e.get_callBehaviorActionOfBehavior.to[Set]
  
  override def behavior_behaviorExecutionSpecification
  : Set[UMLBehaviorExecutionSpecification[Uml]]
  = e.get_behaviorExecutionSpecificationOfBehavior.to[Set]
  
  override def decisionInput_decisionNode
  : Set[UMLDecisionNode[Uml]]
  = e.get_decisionNodeOfDecisionInput.to[Set]
  
  override def reducer_reduceAction
  : Set[UMLReduceAction[Uml]]
  = e.get_reduceActionOfReducer.to[Set]
  
  override def selection_objectNode
  : Set[UMLObjectNode[Uml]]
  = e.get_objectNodeOfSelection.to[Set]
  
  override def transformation_objectFlow
  : Set[UMLObjectFlow[Uml]]
  = e.get_objectFlowOfTransformation.to[Set]
  
  override def redefinedBehavior_behavior
  : Set[UMLBehavior[Uml]]
  = e.getRedefinedBehavior.to[Set]
  
  override def behavior_opaqueExpression
  : Set[UMLOpaqueExpression[Uml]]
  = e.get_opaqueExpressionOfBehavior.to[Set]
  
  override def contract_connector
  : Set[UMLConnector[Uml]]
  = e.get_connectorOfContract.to[Set]
  
  override def ownedBehavior_behavioredClassifier
  : Option[UMLBehavioredClassifier[Uml]]
  = for { result <- Option.apply(e.get_behavioredClassifierOfOwnedBehavior) } yield result
    
  override def doActivity_state
  : Option[UMLState[Uml]]
  = for { result <- Option.apply(e.get_stateOfDoActivity) } yield result
  
  override def entry_state
  : Option[UMLState[Uml]]
  = for { result <- Option.apply(e.get_stateOfEntry) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLState.exit
	 */
	override def exit_state
  : Option[UMLState[Uml]]
  = for { result <-  Option.apply(e.get_stateOfExit) } yield result
  
}