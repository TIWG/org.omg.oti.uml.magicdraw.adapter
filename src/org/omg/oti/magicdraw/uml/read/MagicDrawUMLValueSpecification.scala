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