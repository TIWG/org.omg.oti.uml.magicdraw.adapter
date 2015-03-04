package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInputPin 
  extends UMLInputPin[MagicDrawUML]
  with MagicDrawUMLPin {

  override protected def e: Uml#InputPin
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
	 * @opposite org.omg.oti.api.UMLInvocationAction.argument
	 */
	override def argument_invocationAction: Option[UMLInvocationAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReduceAction.collection
	 */
	override def collection_reduceAction: Option[UMLReduceAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLLinkEndDestructionData.destroyAt
	 */
	override def destroyAt_linkEndDestructionData: Option[UMLLinkEndDestructionData[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLRaiseExceptionAction.exception
	 */
	override def exception_raiseExceptionAction: Option[UMLRaiseExceptionAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLTestIdentityAction.first
	 */
	override def first_testIdentityAction: Option[UMLTestIdentityAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLLinkAction.inputValue
	 */
	override def inputValue_linkAction: Option[UMLLinkAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLOpaqueAction.inputValue
	 */
	override def inputValue_opaqueAction: Option[UMLOpaqueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="true" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLAction.input
	 */
	override def input_action: Option[UMLAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLAddStructuralFeatureValueAction.insertAt
	 */
	override def insertAt_addStructuralFeatureValueAction: Option[UMLAddStructuralFeatureValueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLAddVariableValueAction.insertAt
	 */
	override def insertAt_addVariableValueAction: Option[UMLAddVariableValueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLLinkEndCreationData.insertAt
	 */
	override def insertAt_linkEndCreationData: Option[UMLLinkEndCreationData[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLLoopNode.loopVariableInput
	 */
	override def loopVariableInput_loopNode: Option[UMLLoopNode[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLClearAssociationAction._object
	 */
	override def object_clearAssociationAction: Option[UMLClearAssociationAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadIsClassifiedObjectAction._object
	 */
	override def object_readIsClassifiedObjectAction: Option[UMLReadIsClassifiedObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadLinkObjectEndAction._object
	 */
	override def object_readLinkObjectEndAction: Option[UMLReadLinkObjectEndAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReadLinkObjectEndQualifierAction._object
	 */
	override def object_readLinkObjectEndQualifierAction: Option[UMLReadLinkObjectEndQualifierAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReclassifyObjectAction._object
	 */
	override def object_reclassifyObjectAction: Option[UMLReclassifyObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLStartClassifierBehaviorAction._object
	 */
	override def object_startClassifierBehaviorAction: Option[UMLStartClassifierBehaviorAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLStartObjectBehaviorAction._object
	 */
	override def object_startObjectBehaviorAction: Option[UMLStartObjectBehaviorAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLStructuralFeatureAction._object
	 */
	override def object_structuralFeatureAction: Option[UMLStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLUnmarshallAction._object
	 */
	override def object_unmarshallAction: Option[UMLUnmarshallAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLRemoveStructuralFeatureValueAction.removeAt
	 */
	override def removeAt_removeStructuralFeatureValueAction: Option[UMLRemoveStructuralFeatureValueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLRemoveVariableValueAction.removeAt
	 */
	override def removeAt_removeVariableValueAction: Option[UMLRemoveVariableValueAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReplyAction.replyValue
	 */
	override def replyValue_replyAction: Option[UMLReplyAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLSendObjectAction.request
	 */
	override def request_sendObjectAction: Option[UMLSendObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLReplyAction.returnInformation
	 */
	override def returnInformation_replyAction: Option[UMLReplyAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLTestIdentityAction.second
	 */
	override def second_testIdentityAction: Option[UMLTestIdentityAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLStructuredActivityNode.structuredNodeInput
	 */
	override def structuredNodeInput_structuredActivityNode: Option[UMLStructuredActivityNode[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLCallOperationAction.target
	 */
	override def target_callOperationAction: Option[UMLCallOperationAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLDestroyObjectAction.target
	 */
	override def target_destroyObjectAction: Option[UMLDestroyObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLSendObjectAction.target
	 */
	override def target_sendObjectAction: Option[UMLSendObjectAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLSendSignalAction.target
	 */
	override def target_sendSignalAction: Option[UMLSendSignalAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLWriteStructuralFeatureAction.value
	 */
	override def value_writeStructuralFeatureAction: Option[UMLWriteStructuralFeatureAction[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLWriteVariableAction.value
	 */
	override def value_writeVariableAction: Option[UMLWriteVariableAction[Uml]] = ???

}
