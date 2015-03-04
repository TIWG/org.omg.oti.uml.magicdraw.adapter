package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLOutputPin 
  extends UMLOutputPin[MagicDrawUML]
  with MagicDrawUMLPin {

  override protected def e: Uml#OutputPin
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
	 * @opposite org.omg.oti.api.UMLClearStructuralFeatureAction.result
	 */
	override def result_clearStructuralFeatureAction: Option[UMLClearStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLConditionalNode.result
	 */
	override def result_conditionalNode: Option[UMLConditionalNode[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLCreateLinkObjectAction.result
	 */
	override def result_createLinkObjectAction: Option[UMLCreateLinkObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLCreateObjectAction.result
	 */
	override def result_createObjectAction: Option[UMLCreateObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLLoopNode.result
	 */
	override def result_loopNode: Option[UMLLoopNode[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadExtentAction.result
	 */
	override def result_readExtentAction: Option[UMLReadExtentAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadIsClassifiedObjectAction.result
	 */
	override def result_readIsClassifiedObjectAction: Option[UMLReadIsClassifiedObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadLinkAction.result
	 */
	override def result_readLinkAction: Option[UMLReadLinkAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadLinkObjectEndAction.result
	 */
	override def result_readLinkObjectEndAction: Option[UMLReadLinkObjectEndAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadLinkObjectEndQualifierAction.result
	 */
	override def result_readLinkObjectEndQualifierAction: Option[UMLReadLinkObjectEndQualifierAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadSelfAction.result
	 */
	override def result_readSelfAction: Option[UMLReadSelfAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadStructuralFeatureAction.result
	 */
	override def result_readStructuralFeatureAction: Option[UMLReadStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadVariableAction.result
	 */
	override def result_readVariableAction: Option[UMLReadVariableAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReduceAction.result
	 */
	override def result_reduceAction: Option[UMLReduceAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLTestIdentityAction.result
	 */
	override def result_testIdentityAction: Option[UMLTestIdentityAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLUnmarshallAction.result
	 */
	override def result_unmarshallAction: Option[UMLUnmarshallAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLValueSpecificationAction.result
	 */
	override def result_valueSpecificationAction: Option[UMLValueSpecificationAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLWriteStructuralFeatureAction.result
	 */
	override def result_writeStructuralFeatureAction: Option[UMLWriteStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLAcceptCallAction.returnInformation
	 */
	override def returnInformation_acceptCallAction: Option[UMLAcceptCallAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLStructuredActivityNode.structuredNodeOutput
	 */
	override def structuredNodeOutput_structuredActivityNode: Option[UMLStructuredActivityNode[Uml]] = ???
}
