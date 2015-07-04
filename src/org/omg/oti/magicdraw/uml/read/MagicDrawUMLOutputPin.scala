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

import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._

trait MagicDrawUMLOutputPin 
  extends UMLOutputPin[MagicDrawUML]
  with MagicDrawUMLPin {

  override protected def e: Uml#OutputPin
  def getMagicDrawOutputPin = e
  import ops._

	override def bodyOutput_clause: Set[UMLClause[Uml]] =
   e.get_clauseOfBodyOutput.toSet[Uml#Clause]
      
	override def decider_clause: Option[UMLClause[Uml]] =
    Option.apply( e.get_clauseOfDecider )

	override def decider_loopNode: Option[UMLLoopNode[Uml]] =
    Option.apply( e.get_loopNodeOfDecider )
  
	override def bodyOutput_loopNode: Set[UMLLoopNode[Uml]] =
    e.get_loopNodeOfBodyOutput.toSet[Uml#LoopNode]

  override def loopVariable_loopNode: Option[UMLLoopNode[Uml]] = ???
  
  override def output_action: Option[UMLAction[Uml]] = ???
  
  override def outputValue_opaqueAction: Option[UMLOpaqueAction[Uml]] = ???
  
  override def result_acceptEventAction: Option[UMLAcceptEventAction[Uml]] = ???
  
  override def result_callAction: Option[UMLCallAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLClearStructuralFeatureAction.result
	 */
	override def result_clearStructuralFeatureAction: Option[UMLClearStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLConditionalNode.result
	 */
	override def result_conditionalNode: Option[UMLConditionalNode[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLCreateLinkObjectAction.result
	 */
	override def result_createLinkObjectAction: Option[UMLCreateLinkObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLCreateObjectAction.result
	 */
	override def result_createObjectAction: Option[UMLCreateObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLLoopNode.result
	 */
	override def result_loopNode: Option[UMLLoopNode[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadExtentAction.result
	 */
	override def result_readExtentAction: Option[UMLReadExtentAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadIsClassifiedObjectAction.result
	 */
	override def result_readIsClassifiedObjectAction: Option[UMLReadIsClassifiedObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadLinkAction.result
	 */
	override def result_readLinkAction: Option[UMLReadLinkAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadLinkObjectEndAction.result
	 */
	override def result_readLinkObjectEndAction: Option[UMLReadLinkObjectEndAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadLinkObjectEndQualifierAction.result
	 */
	override def result_readLinkObjectEndQualifierAction: Option[UMLReadLinkObjectEndQualifierAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadSelfAction.result
	 */
	override def result_readSelfAction: Option[UMLReadSelfAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadStructuralFeatureAction.result
	 */
	override def result_readStructuralFeatureAction: Option[UMLReadStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReadVariableAction.result
	 */
	override def result_readVariableAction: Option[UMLReadVariableAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLReduceAction.result
	 */
	override def result_reduceAction: Option[UMLReduceAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLTestIdentityAction.result
	 */
	override def result_testIdentityAction: Option[UMLTestIdentityAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLUnmarshallAction.result
	 */
	override def result_unmarshallAction: Option[UMLUnmarshallAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLValueSpecificationAction.result
	 */
	override def result_valueSpecificationAction: Option[UMLValueSpecificationAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLWriteStructuralFeatureAction.result
	 */
	override def result_writeStructuralFeatureAction: Option[UMLWriteStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLAcceptCallAction.returnInformation
	 */
	override def returnInformation_acceptCallAction: Option[UMLAcceptCallAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.uml.read.api.UMLStructuredActivityNode.structuredNodeOutput
	 */
	override def structuredNodeOutput_structuredActivityNode: Option[UMLStructuredActivityNode[Uml]] = ???
}