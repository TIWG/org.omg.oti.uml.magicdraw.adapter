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

import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._

trait MagicDrawUMLInputPin 
  extends UMLInputPin[MagicDrawUML]
  with MagicDrawUMLPin {

  override protected def e: Uml#InputPin
  def getMagicDrawInputPin = e
  import ops._

	override def value_linkEndData: Option[UMLLinkEndData[Uml]] =
    Option.apply( e.get_linkEndDataOfValue )
  
	override def value_qualifierValue: Option[UMLQualifierValue[Uml]] =
    Option.apply( e.get_qualifierValueOfValue )

    
	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLInvocationAction.argument
	 */
	override def argument_invocationAction: Option[UMLInvocationAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReduceAction.collection
	 */
	override def collection_reduceAction: Option[UMLReduceAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLLinkEndDestructionData.destroyAt
	 */
	override def destroyAt_linkEndDestructionData: Option[UMLLinkEndDestructionData[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLRaiseExceptionAction.exception
	 */
	override def exception_raiseExceptionAction: Option[UMLRaiseExceptionAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLTestIdentityAction.first
	 */
	override def first_testIdentityAction: Option[UMLTestIdentityAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLLinkAction.inputValue
	 */
	override def inputValue_linkAction: Option[UMLLinkAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLOpaqueAction.inputValue
	 */
	override def inputValue_opaqueAction: Option[UMLOpaqueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="true" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLAction.input
	 */
	override def input_action: Option[UMLAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLAddStructuralFeatureValueAction.insertAt
	 */
	override def insertAt_addStructuralFeatureValueAction: Option[UMLAddStructuralFeatureValueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLAddVariableValueAction.insertAt
	 */
	override def insertAt_addVariableValueAction: Option[UMLAddVariableValueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLLinkEndCreationData.insertAt
	 */
	override def insertAt_linkEndCreationData: Option[UMLLinkEndCreationData[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLLoopNode.loopVariableInput
	 */
	override def loopVariableInput_loopNode: Option[UMLLoopNode[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLClearAssociationAction._object
	 */
	override def object_clearAssociationAction: Option[UMLClearAssociationAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadIsClassifiedObjectAction._object
	 */
	override def object_readIsClassifiedObjectAction: Option[UMLReadIsClassifiedObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadLinkObjectEndAction._object
	 */
	override def object_readLinkObjectEndAction: Option[UMLReadLinkObjectEndAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadLinkObjectEndQualifierAction._object
	 */
	override def object_readLinkObjectEndQualifierAction: Option[UMLReadLinkObjectEndQualifierAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReclassifyObjectAction._object
	 */
	override def object_reclassifyObjectAction: Option[UMLReclassifyObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLStartClassifierBehaviorAction._object
	 */
	override def object_startClassifierBehaviorAction: Option[UMLStartClassifierBehaviorAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLStartObjectBehaviorAction._object
	 */
	override def object_startObjectBehaviorAction: Option[UMLStartObjectBehaviorAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLStructuralFeatureAction._object
	 */
	override def object_structuralFeatureAction: Option[UMLStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLUnmarshallAction._object
	 */
	override def object_unmarshallAction: Option[UMLUnmarshallAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLRemoveStructuralFeatureValueAction.removeAt
	 */
	override def removeAt_removeStructuralFeatureValueAction: Option[UMLRemoveStructuralFeatureValueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLRemoveVariableValueAction.removeAt
	 */
	override def removeAt_removeVariableValueAction: Option[UMLRemoveVariableValueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReplyAction.replyValue
	 */
	override def replyValue_replyAction: Option[UMLReplyAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLSendObjectAction.request
	 */
	override def request_sendObjectAction: Option[UMLSendObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReplyAction.returnInformation
	 */
	override def returnInformation_replyAction: Option[UMLReplyAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLTestIdentityAction.second
	 */
	override def second_testIdentityAction: Option[UMLTestIdentityAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLStructuredActivityNode.structuredNodeInput
	 */
	override def structuredNodeInput_structuredActivityNode: Option[UMLStructuredActivityNode[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLCallOperationAction.target
	 */
	override def target_callOperationAction: Option[UMLCallOperationAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLDestroyObjectAction.target
	 */
	override def target_destroyObjectAction: Option[UMLDestroyObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLSendObjectAction.target
	 */
	override def target_sendObjectAction: Option[UMLSendObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLSendSignalAction.target
	 */
	override def target_sendSignalAction: Option[UMLSendSignalAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLWriteStructuralFeatureAction.value
	 */
	override def value_writeStructuralFeatureAction: Option[UMLWriteStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLWriteVariableAction.value
	 */
	override def value_writeVariableAction: Option[UMLWriteVariableAction[Uml]] = ???


}

case class MagicDrawUMLInputPinImpl(val e: MagicDrawUML#InputPin, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLInputPin