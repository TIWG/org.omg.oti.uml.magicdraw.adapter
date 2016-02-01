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

import org.omg.oti.uml.read.api._
import scala.Option

trait MagicDrawUMLInputPin 
  extends MagicDrawUMLPin
  with UMLInputPin[MagicDrawUML] {

  override protected def e: Uml#InputPin
  def getMagicDrawInputPin = e

  override implicit val umlOps = ops
  import umlOps._

	override def value_linkEndData: Option[UMLLinkEndData[Uml]] =
    for { result <- Option( e.get_linkEndDataOfValue ) } yield result
  
	override def value_qualifierValue: Option[UMLQualifierValue[Uml]] =
    for { result <- Option( e.get_qualifierValueOfValue ) } yield result

    
	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLInvocationAction.argument
	 */
	override def argument_invocationAction
	: Option[UMLInvocationAction[Uml]] =
  for { result <- Option(e.get_invocationActionOfArgument) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLLinkEndDestructionData.destroyAt
	 */
	override def destroyAt_linkEndDestructionData
  : Option[UMLLinkEndDestructionData[Uml]] =
  for { result <- Option(e.get_linkEndDestructionDataOfDestroyAt) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLTestIdentityAction.first
	 */
	override def first_testIdentityAction
  : Option[UMLTestIdentityAction[Uml]] =
  for { result <- Option(e.get_testIdentityActionOfFirst()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="true" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLAction.input
	 */
	override def input_action
  : Option[UMLAction[Uml]] =
  for { result <- Option(e.get_actionOfInput()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLLinkEndCreationData.insertAt
	 */
	override def insertAt_linkEndCreationData
  : Option[UMLLinkEndCreationData[Uml]] =
  for { result <- Option(e.get_linkEndCreationDataOfInsertAt()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLStructuralFeatureAction._object
	 */
	override def object_structuralFeatureAction
  : Option[UMLStructuralFeatureAction[Uml]] =
  for { result <- Option(e.get_structuralFeatureActionOfObject()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLReplyAction.replyValue
	 */
	override def replyValue_replyAction
  : Option[UMLReplyAction[Uml]] =
  for { result <- Option(e.get_replyActionOfReplyValue()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLSendObjectAction.request
	 */
	override def request_sendObjectAction
  : Option[UMLSendObjectAction[Uml]] =
  for { result <- Option(e.get_sendObjectActionOfRequest()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLReplyAction.returnInformation
	 */
	override def returnInformation_replyAction
  : Option[UMLReplyAction[Uml]] =
  for { result <- Option(e.get_replyActionOfReturnInformation()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLTestIdentityAction.second
	 */
	override def second_testIdentityAction
  : Option[UMLTestIdentityAction[Uml]] =
  for { result <- Option(e.get_testIdentityActionOfSecond()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLSendObjectAction.target
	 */
	override def target_sendObjectAction
  : Option[UMLSendObjectAction[Uml]] =
  for { result <- Option(e.get_sendObjectActionOfTarget()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLWriteStructuralFeatureAction.value
	 */
	override def value_writeStructuralFeatureAction
  : Option[UMLWriteStructuralFeatureAction[Uml]] =
  for { result <- Option(e.get_writeStructuralFeatureActionOfValue()) } yield result

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * UML metamodel property: derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * Opposite of UML metamodel property: org.omg.oti.uml.read.api.UMLWriteVariableAction.value
	 */
	override def value_writeVariableAction
  : Option[UMLWriteVariableAction[Uml]] =
  for { result <- Option(e.get_writeVariableActionOfValue()) } yield result

}

case class MagicDrawUMLInputPinImpl
(e: MagicDrawUML#InputPin, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLInputPin