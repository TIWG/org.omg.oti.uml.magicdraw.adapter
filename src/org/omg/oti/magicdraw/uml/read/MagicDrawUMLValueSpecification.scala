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
package org.omg.oti.magicdraw.uml.read

import scala.collection.JavaConversions._
import scala.collection.immutable._

import org.omg.oti.uml.read.api._
import scala.Option

trait MagicDrawUMLValueSpecification 
  extends UMLValueSpecification[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLTypedElement {

  override protected def e: Uml#ValueSpecification
  def getMagicDrawValueSpecification = e
  import ops._

  override def min_interval: Set[UMLInterval[Uml]] =
    e.get_intervalOfMin.toSet[Uml#Interval]
    
  override def max_interval: Set[UMLInterval[Uml]] =
    e.get_intervalOfMax.toSet[Uml#Interval]
    
  
	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLInteractionUse.argument
	 */
	override def argument_interactionUse: Option[UMLInteractionUse[Uml]] =
    Option.apply(e.get_interactionUseOfArgument())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLMessage.argument
	 */
	override def argument_message: Option[UMLMessage[Uml]] =
    Option.apply(e.get_messageOfArgument())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLChangeEvent.changeExpression
	 */
	override def changeExpression_changeEvent: Option[UMLChangeEvent[Uml]] =
    Option.apply(e.get_changeEventOfChangeExpression())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLParameter.defaultValue
	 */
	override def defaultValue_owningParameter: Option[UMLParameter[Uml]] =
    Option.apply(e.getOwningParameter)

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLProperty.defaultValue
	 */
	override def defaultValue_owningProperty: Option[UMLProperty[Uml]] =
    Option.apply(e.getOwningProperty)

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLDuration.expr
	 */
	override def expr_duration: Option[UMLDuration[Uml]] =
    Option.apply(e.get_durationOfExpr())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLTimeExpression.expr
	 */
	override def expr_timeExpression: Option[UMLTimeExpression[Uml]] =
    Option.apply(e.get_timeExpressionOfExpr())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLActivityEdge.guard
	 */
	override def guard_activityEdge: Option[UMLActivityEdge[Uml]] =
    Option.apply(e.get_activityEdgeOfGuard())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLJoinNode.joinSpec
	 */
	override def joinSpec_joinNode: Option[UMLJoinNode[Uml]] =
    Option.apply(e.get_joinNodeOfJoinSpec())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLMultiplicityElement.lowerValue
	 */
	override def lowerValue_owningLower: Option[UMLMultiplicityElement[Uml]] =
    Option.apply(e.getOwningLower)


	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLInteractionConstraint.maxint
	 */
	override def maxint_interactionConstraint: Option[UMLInteractionConstraint[Uml]] =
    Option.apply(e.get_interactionConstraintOfMaxint())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLInteractionConstraint.minint
	 */
	override def minint_interactionConstraint: Option[UMLInteractionConstraint[Uml]] =
    Option.apply(e.get_interactionConstraintOfMinint())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLExpression.operand
	 */
	override def operand_expression: Option[UMLExpression[Uml]] =
    Option.apply(e.getExpression)

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLInteractionUse.returnValue
	 */
	override def returnValue_interactionUse: Option[UMLInteractionUse[Uml]] =
    Option.apply(e.get_interactionUseOfReturnValue())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLLifeline.selector
	 */
	override def selector_lifeline: Option[UMLLifeline[Uml]] =
    Option.apply(e.get_lifelineOfSelector())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLConstraint.specification
	 */
	override def specification_owningConstraint: Option[UMLConstraint[Uml]] =
    Option.apply(e.getOwningConstraint)

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLInstanceSpecification.specification
	 */
	override def specification_owningInstanceSpec: Option[UMLInstanceSpecification[Uml]] =
    Option.apply(e.getOwningInstanceSpec)

	override def upperBound_objectNode: Option[UMLObjectNode[Uml]] =
    Option.apply(e.get_objectNodeOfUpperBound())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLMultiplicityElement.upperValue
	 */
	override def upperValue_owningUpper: Option[UMLMultiplicityElement[Uml]] =
    Option.apply(e.getOwningUpper)

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLSlot.value
	 */
	override def value_owningSlot: Option[UMLSlot[Uml]] =
    Option.apply(e.getOwningSlot)

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLValuePin.value
	 */
	override def value_valuePin: Option[UMLValuePin[Uml]] =
    Option.apply(e.get_valuePinOfValue())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLValueSpecificationAction.value
	 */
	override def value_valueSpecificationAction: Option[UMLValueSpecificationAction[Uml]] =
    Option.apply(e.get_valueSpecificationActionOfValue())

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLActivityEdge.weight
	 */
	override def weight_activityEdge: Option[UMLActivityEdge[Uml]] =
    Option.apply(e.get_activityEdgeOfWeight())

}