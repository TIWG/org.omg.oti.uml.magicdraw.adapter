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