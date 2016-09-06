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
import scala.Option

trait MagicDrawUMLValueSpecification 
  extends MagicDrawUMLPackageableElement
  with MagicDrawUMLTypedElement
  with UMLValueSpecification[MagicDrawUML] {

  override protected def e: Uml#ValueSpecification
  def getMagicDrawValueSpecification = e
  import ops._

  override def min_interval: Set[UMLInterval[Uml]] =
    e.get_intervalOfMin.to[Set]
    
  override def max_interval: Set[UMLInterval[Uml]] =
    e.get_intervalOfMax.to[Set]
    
  
	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLInteractionUse.argument
	 */
	override def argument_interactionUse: Option[UMLInteractionUse[Uml]] =
    for { result <- Option(e.get_interactionUseOfArgument()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLActivityEdge.guard
	 */
	override def guard_activityEdge: Option[UMLActivityEdge[Uml]] =
    for { result <- Option(e.get_activityEdgeOfGuard()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLMultiplicityElement.lowerValue
	 */
	override def lowerValue_owningLower: Option[UMLMultiplicityElement[Uml]] =
    for { result <- Option(e.getOwningLower) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLInteractionConstraint.maxint
	 */
	override def maxint_interactionConstraint: Option[UMLInteractionConstraint[Uml]] =
    for { result <- Option(e.get_interactionConstraintOfMaxint()) } yield result

  /**
    * <!-- begin-model-doc -->
    * <!-- end-model-doc -->
    *
    * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
    * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLInteractionConstraint.minint
    */
  override def minint_interactionConstraint: Option[UMLInteractionConstraint[Uml]] =
    for { result <- Option(e.get_interactionConstraintOfMinint()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLInteractionUse.returnValue
	 */
	override def returnValue_interactionUse: Option[UMLInteractionUse[Uml]] =
    for { result <- Option(e.get_interactionUseOfReturnValue()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLConstraint.specification
	 */
	override def specification_owningConstraint: Option[UMLConstraint[Uml]] =
    for { result <- Option(e.getOwningConstraint) } yield result

	override def upperBound_objectNode: Option[UMLObjectNode[Uml]] =
    for { result <- Option(e.get_objectNodeOfUpperBound()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLMultiplicityElement.upperValue
	 */
	override def upperValue_owningUpper: Option[UMLMultiplicityElement[Uml]] =
    for { result <- Option(e.getOwningUpper) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLActivityEdge.weight
	 */
	override def weight_activityEdge: Option[UMLActivityEdge[Uml]] =
    for { result <- Option(e.get_activityEdgeOfWeight()) } yield result

}