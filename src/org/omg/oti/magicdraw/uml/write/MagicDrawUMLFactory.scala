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
package org.omg.oti.magicdraw.uml.write

import org.omg.oti.magicdraw.uml.read.{MagicDrawUML, MagicDrawUMLUtil}
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.write.api.UMLFactory

import scala.util.{Success, Try}

case class MagicDrawUMLFactory(mdUMLUtils: MagicDrawUMLUtil)
  extends UMLFactory[MagicDrawUML] {

  val f = mdUMLUtils.project.getElementsFactory

  import mdUMLUtils._

  override def createUMLAbstraction: Try[UMLAbstraction[MagicDrawUML]] =
    Success(f.createAbstractionInstance())

  override def createUMLAcceptCallAction: Try[UMLAcceptCallAction[MagicDrawUML]] =
    Success(f.createAcceptCallActionInstance())

  override def createUMLAcceptEventAction: Try[UMLAcceptEventAction[MagicDrawUML]] =
    Success(f.createAcceptEventActionInstance())

  override def createUMLActionExecutionSpecification: Try[UMLActionExecutionSpecification[Uml]] =
    Success(f.createActionExecutionSpecificationInstance())

  override def createUMLActionInputPin: Try[UMLActionInputPin[MagicDrawUML]] =
    Success(f.createActionInputPinInstance())

  override def createUMLActivity: Try[UMLActivity[MagicDrawUML]] =
    Success(f.createActivityInstance())

  override def createUMLActivityFinalNode: Try[UMLActivityFinalNode[MagicDrawUML]] =
    Success(f.createActivityFinalNodeInstance())

  override def createUMLActivityParameterNode: Try[UMLActivityParameterNode[MagicDrawUML]] =
    Success(f.createActivityParameterNodeInstance())

  override def createUMLActivityPartition: Try[UMLActivityPartition[MagicDrawUML]] =
    Success(f.createActivityPartitionInstance())

  override def createUMLActor: Try[UMLActor[MagicDrawUML]] =
    Success(f.createActorInstance())

  override def createUMLAddStructuralFeatureValueAction: Try[UMLAddStructuralFeatureValueAction[MagicDrawUML]] =
    Success(f.createAddStructuralFeatureValueActionInstance())

  override def createUMLAddVariableValueAction: Try[UMLAddVariableValueAction[MagicDrawUML]] =
    Success(f.createAddVariableValueActionInstance())

  override def createUMLAnyReceiveEvent: Try[UMLAnyReceiveEvent[MagicDrawUML]] =
    Success(f.createAnyReceiveEventInstance())

  override def createUMLArtifact: Try[UMLArtifact[MagicDrawUML]] =
    Success(f.createArtifactInstance())

  override def createUMLAssociation: Try[UMLAssociation[MagicDrawUML]] =
    Success(f.createAssociationInstance())

  override def createUMLAssociationClass: Try[UMLAssociationClass[MagicDrawUML]] =
    Success(f.createAssociationClassInstance())

  override def createUMLBehaviorExecutionSpecification: Try[UMLBehaviorExecutionSpecification[MagicDrawUML]] =
    Success(f.createBehaviorExecutionSpecificationInstance())

  override def createUMLBroadcastSignalAction: Try[UMLBroadcastSignalAction[MagicDrawUML]] =
    Success(f.createBroadcastSignalActionInstance())

  override def createUMLCallBehaviorAction: Try[UMLCallBehaviorAction[MagicDrawUML]] =
    Success(f.createCallBehaviorActionInstance())

  override def createUMLCallEvent: Try[UMLCallEvent[MagicDrawUML]] =
    Success(f.createCallEventInstance())

  override def createUMLCallOperationAction: Try[UMLCallOperationAction[MagicDrawUML]] =
    Success(f.createCallOperationActionInstance())

  override def createUMLCentralBufferNode: Try[UMLCentralBufferNode[MagicDrawUML]] =
    Success(f.createCentralBufferNodeInstance())

  override def createUMLChangeEvent: Try[UMLChangeEvent[MagicDrawUML]] =
    Success(f.createChangeEventInstance())

  override def createUMLClass: Try[UMLClass[MagicDrawUML]] =
    Success(f.createClassInstance())

  override def createUMLClassifierTemplateParameter: Try[UMLClassifierTemplateParameter[MagicDrawUML]] =
    Success(f.createClassifierTemplateParameterInstance())

  override def createUMLClause: Try[UMLClause[MagicDrawUML]] =
    Success(f.createClauseInstance())

  override def createUMLClearAssociationAction: Try[UMLClearAssociationAction[MagicDrawUML]] =
    Success(f.createClearAssociationActionInstance())

  override def createUMLClearStructuralFeatureAction: Try[UMLClearStructuralFeatureAction[MagicDrawUML]] =
    Success(f.createClearStructuralFeatureActionInstance())

  override def createUMLClearVariableAction: Try[UMLClearVariableAction[MagicDrawUML]] =
    Success(f.createClearVariableActionInstance())

  override def createUMLCollaboration: Try[UMLCollaboration[MagicDrawUML]] =
    Success(f.createCollaborationInstance())

  override def createUMLCollaborationUse: Try[UMLCollaborationUse[MagicDrawUML]] =
    Success(f.createCollaborationUseInstance())

  override def createUMLCombinedFragment: Try[UMLCombinedFragment[MagicDrawUML]] =
    Success(f.createCombinedFragmentInstance())

  override def createUMLComment: Try[UMLComment[MagicDrawUML]] =
    Success(f.createCommentInstance())

  override def createUMLCommunicationPath: Try[UMLCommunicationPath[MagicDrawUML]] =
    Success(f.createCommunicationPathInstance())

  override def createUMLComponent: Try[UMLComponent[MagicDrawUML]] =
    Success(f.createComponentInstance())

  override def createUMLComponentRealization: Try[UMLComponentRealization[MagicDrawUML]] =
    Success(f.createComponentRealizationInstance())

  override def createUMLConditionalNode: Try[UMLConditionalNode[MagicDrawUML]] =
    Success(f.createConditionalNodeInstance())

  override def createUMLConnectableElementTemplateParameter: Try[UMLConnectableElementTemplateParameter[MagicDrawUML]] =
    Success(f.createConnectableElementTemplateParameterInstance())

  override def createUMLConnectionPointReference: Try[UMLConnectionPointReference[MagicDrawUML]] =
    Success(f.createConnectionPointReferenceInstance())

  override def createUMLConnector: Try[UMLConnector[MagicDrawUML]] =
    Success(f.createConnectorInstance())

  override def createUMLConnectorEnd: Try[UMLConnectorEnd[MagicDrawUML]] =
    Success(f.createConnectorEndInstance())

  override def createUMLConsiderIgnoreFragment: Try[UMLConsiderIgnoreFragment[MagicDrawUML]] =
    Success(f.createConsiderIgnoreFragmentInstance())

  override def createUMLConstraint: Try[UMLConstraint[MagicDrawUML]] =
    Success(f.createConstraintInstance())

  override def createUMLContinuation: Try[UMLContinuation[MagicDrawUML]] =
    Success(f.createContinuationInstance())

  override def createUMLControlFlow: Try[UMLControlFlow[MagicDrawUML]] =
    Success(f.createControlFlowInstance())

  override def createUMLCreateLinkAction: Try[UMLCreateLinkAction[MagicDrawUML]] =
    Success(f.createCreateLinkActionInstance())

  override def createUMLCreateLinkObjectAction: Try[UMLCreateLinkObjectAction[MagicDrawUML]] =
    Success(f.createCreateLinkObjectActionInstance())

  override def createUMLCreateObjectAction: Try[UMLCreateObjectAction[MagicDrawUML]] =
    Success(f.createCreateObjectActionInstance())

  override def createUMLDataStoreNode: Try[UMLDataStoreNode[MagicDrawUML]] =
    Success(f.createDataStoreNodeInstance())

  override def createUMLDataType: Try[UMLDataType[MagicDrawUML]] =
    Success(f.createDataTypeInstance())

  override def createUMLDecisionNode: Try[UMLDecisionNode[MagicDrawUML]] =
    Success(f.createDecisionNodeInstance())

  override def createUMLDependency: Try[UMLDependency[MagicDrawUML]] =
    Success(f.createDependencyInstance())

  override def createUMLDeployment: Try[UMLDeployment[MagicDrawUML]] =
    Success(f.createDeploymentInstance())

  override def createUMLDeploymentSpecification: Try[UMLDeploymentSpecification[MagicDrawUML]] =
    Success(f.createDeploymentSpecificationInstance())

  override def createUMLDestroyLinkAction: Try[UMLDestroyLinkAction[MagicDrawUML]] =
    Success(f.createDestroyLinkActionInstance())

  override def createUMLDestroyObjectAction: Try[UMLDestroyObjectAction[MagicDrawUML]] =
    Success(f.createDestroyObjectActionInstance())

  override def createUMLDestructionOccurrenceSpecification: Try[UMLDestructionOccurrenceSpecification[MagicDrawUML]] =
    Success(f.createDestructionOccurrenceSpecificationInstance())

  override def createUMLDevice: Try[UMLDevice[MagicDrawUML]] =
    Success(f.createDeviceInstance())

  override def createUMLDuration: Try[UMLDuration[MagicDrawUML]] =
    Success(f.createDurationInstance())

  override def createUMLDurationConstraint: Try[UMLDurationConstraint[MagicDrawUML]] =
    Success(f.createDurationConstraintInstance())

  override def createUMLDurationInterval: Try[UMLDurationInterval[MagicDrawUML]] =
    Success(f.createDurationIntervalInstance())

  override def createUMLDurationObservation: Try[UMLDurationObservation[MagicDrawUML]] =
    Success(f.createDurationObservationInstance())

  override def createUMLElementImport: Try[UMLElementImport[MagicDrawUML]] =
    Success(f.createElementImportInstance())

  override def createUMLEnumeration: Try[UMLEnumeration[MagicDrawUML]] =
    Success(f.createEnumerationInstance())

  override def createUMLEnumerationLiteral: Try[UMLEnumerationLiteral[MagicDrawUML]] =
    Success(f.createEnumerationLiteralInstance())

  override def createUMLExceptionHandler: Try[UMLExceptionHandler[MagicDrawUML]] =
    Success(f.createExceptionHandlerInstance())

  override def createUMLExecutionEnvironment: Try[UMLExecutionEnvironment[MagicDrawUML]] =
    Success(f.createExecutionEnvironmentInstance())

  override def createUMLExecutionOccurrenceSpecification: Try[UMLExecutionOccurrenceSpecification[MagicDrawUML]] =
    Success(f.createExecutionOccurrenceSpecificationInstance())

  override def createUMLExpansionNode: Try[UMLExpansionNode[MagicDrawUML]] =
    Success(f.createExpansionNodeInstance())

  override def createUMLExpansionRegion: Try[UMLExpansionRegion[MagicDrawUML]] =
    Success(f.createExpansionRegionInstance())

  override def createUMLExpression: Try[UMLExpression[MagicDrawUML]] =
    Success(f.createExpressionInstance())

  override def createUMLExtend: Try[UMLExtend[MagicDrawUML]] =
    Success(f.createExtendInstance())

  override def createUMLExtension: Try[UMLExtension[MagicDrawUML]] =
    Success(f.createExtensionInstance())

  override def createUMLExtensionEnd: Try[UMLExtensionEnd[MagicDrawUML]] =
    Success(f.createExtensionEndInstance())

  override def createUMLExtensionPoint: Try[UMLExtensionPoint[MagicDrawUML]] =
    Success(f.createExtensionPointInstance())

  override def createUMLFinalState: Try[UMLFinalState[MagicDrawUML]] =
    Success(f.createFinalStateInstance())

  override def createUMLFlowFinalNode: Try[UMLFlowFinalNode[MagicDrawUML]] =
    Success(f.createFlowFinalNodeInstance())

  override def createUMLForkNode: Try[UMLForkNode[MagicDrawUML]] =
    Success(f.createForkNodeInstance())

  override def createUMLFunctionBehavior: Try[UMLFunctionBehavior[MagicDrawUML]] =
    Success(f.createFunctionBehaviorInstance())

  override def createUMLGate: Try[UMLGate[MagicDrawUML]] =
    Success(f.createGateInstance())

  override def createUMLGeneralOrdering: Try[UMLGeneralOrdering[MagicDrawUML]] =
    Success(f.createGeneralOrderingInstance())

  override def createUMLGeneralization: Try[UMLGeneralization[MagicDrawUML]] =
    Success(f.createGeneralizationInstance())

  override def createUMLGeneralizationSet: Try[UMLGeneralizationSet[MagicDrawUML]] =
    Success(f.createGeneralizationSetInstance())

  override def createUMLImage: Try[UMLImage[MagicDrawUML]] =
    Success(f.createImageInstance())

  override def createUMLInclude: Try[UMLInclude[MagicDrawUML]] =
    Success(f.createIncludeInstance())

  override def createUMLInformationFlow: Try[UMLInformationFlow[MagicDrawUML]] =
    Success(f.createInformationFlowInstance())

  override def createUMLInformationItem: Try[UMLInformationItem[MagicDrawUML]] =
    Success(f.createInformationItemInstance())

  override def createUMLInitialNode: Try[UMLInitialNode[MagicDrawUML]] =
    Success(f.createInitialNodeInstance())

  override def createUMLInputPin: Try[UMLInputPin[MagicDrawUML]] =
    Success(f.createInputPinInstance())

  override def createUMLInstanceSpecification: Try[UMLInstanceSpecification[MagicDrawUML]] =
    Success(f.createInstanceSpecificationInstance())

  override def createUMLInstanceValue: Try[UMLInstanceValue[MagicDrawUML]] =
    Success(f.createInstanceValueInstance())

  override def createUMLInteraction: Try[UMLInteraction[MagicDrawUML]] =
    Success(f.createInteractionInstance())

  override def createUMLInteractionConstraint: Try[UMLInteractionConstraint[MagicDrawUML]] =
    Success(f.createInteractionConstraintInstance())

  override def createUMLInteractionOperand: Try[UMLInteractionOperand[MagicDrawUML]] =
    Success(f.createInteractionOperandInstance())

  override def createUMLInteractionUse: Try[UMLInteractionUse[MagicDrawUML]] =
    Success(f.createInteractionUseInstance())

  override def createUMLInterface: Try[UMLInterface[MagicDrawUML]] =
    Success(f.createInterfaceInstance())

  override def createUMLInterfaceRealization: Try[UMLInterfaceRealization[MagicDrawUML]] =
    Success(f.createInterfaceRealizationInstance())

  override def createUMLInterruptibleActivityRegion: Try[UMLInterruptibleActivityRegion[MagicDrawUML]] =
    Success(f.createInterruptibleActivityRegionInstance())

  override def createUMLInterval: Try[UMLInterval[MagicDrawUML]] =
    Success(f.createIntervalInstance())

  override def createUMLIntervalConstraint: Try[UMLIntervalConstraint[MagicDrawUML]] =
    Success(f.createIntervalConstraintInstance())

  override def createUMLJoinNode: Try[UMLJoinNode[MagicDrawUML]] =
    Success(f.createJoinNodeInstance())

  override def createUMLLifeline: Try[UMLLifeline[MagicDrawUML]] =
    Success(f.createLifelineInstance())

  override def createUMLLinkEndCreationData: Try[UMLLinkEndCreationData[MagicDrawUML]] =
    Success(f.createLinkEndCreationDataInstance())

  override def createUMLLinkEndData: Try[UMLLinkEndData[MagicDrawUML]] =
    Success(f.createLinkEndDataInstance())

  override def createUMLLinkEndDestructionData: Try[UMLLinkEndDestructionData[MagicDrawUML]] =
    Success(f.createLinkEndDestructionDataInstance())

  override def createUMLLiteralBoolean: Try[UMLLiteralBoolean[MagicDrawUML]] =
    Success(f.createLiteralBooleanInstance())

  override def createUMLLiteralInteger: Try[UMLLiteralInteger[MagicDrawUML]] =
    Success(f.createLiteralIntegerInstance())

  override def createUMLLiteralNull: Try[UMLLiteralNull[MagicDrawUML]] =
    Success(f.createLiteralNullInstance())

  override def createUMLLiteralReal: Try[UMLLiteralReal[MagicDrawUML]] =
    Success(f.createLiteralRealInstance())

  override def createUMLLiteralString: Try[UMLLiteralString[MagicDrawUML]] =
    Success(f.createLiteralStringInstance())

  override def createUMLLiteralUnlimitedNatural: Try[UMLLiteralUnlimitedNatural[MagicDrawUML]] =
    Success(f.createLiteralUnlimitedNaturalInstance())

  override def createUMLLoopNode: Try[UMLLoopNode[MagicDrawUML]] =
    Success(f.createLoopNodeInstance())

  override def createUMLManifestation: Try[UMLManifestation[MagicDrawUML]] =
    Success(f.createManifestationInstance())

  override def createUMLMergeNode: Try[UMLMergeNode[MagicDrawUML]] =
    Success(f.createMergeNodeInstance())

  override def createUMLMessage: Try[UMLMessage[MagicDrawUML]] =
    Success(f.createMessageInstance())

  override def createUMLMessageOccurrenceSpecification: Try[UMLMessageOccurrenceSpecification[MagicDrawUML]] =
    Success(f.createMessageOccurrenceSpecificationInstance())

  override def createUMLModel: Try[UMLModel[MagicDrawUML]] =
    Success(f.createModelInstance())

  override def createUMLNode: Try[UMLNode[MagicDrawUML]] =
    Success(f.createNodeInstance())

  override def createUMLObjectFlow: Try[UMLObjectFlow[MagicDrawUML]] =
    Success(f.createObjectFlowInstance())

  override def createUMLOccurrenceSpecification: Try[UMLOccurrenceSpecification[MagicDrawUML]] =
    Success(f.createOccurrenceSpecificationInstance())

  override def createUMLOpaqueAction: Try[UMLOpaqueAction[MagicDrawUML]] =
    Success(f.createOpaqueActionInstance())

  override def createUMLOpaqueBehavior: Try[UMLOpaqueBehavior[MagicDrawUML]] =
    Success(f.createOpaqueBehaviorInstance())

  override def createUMLOpaqueExpression: Try[UMLOpaqueExpression[MagicDrawUML]] =
    Success(f.createOpaqueExpressionInstance())

  override def createUMLOperation: Try[UMLOperation[MagicDrawUML]] =
    Success(f.createOperationInstance())

  override def createUMLOperationTemplateParameter: Try[UMLOperationTemplateParameter[MagicDrawUML]] =
    Success(f.createOperationTemplateParameterInstance())

  override def createUMLOutputPin: Try[UMLOutputPin[MagicDrawUML]] =
    Success(f.createOutputPinInstance())

  override def createUMLPackage: Try[UMLPackage[MagicDrawUML]] =
    Success(f.createPackageInstance())

  override def createUMLPackageImport: Try[UMLPackageImport[MagicDrawUML]] =
    Success(f.createPackageImportInstance())

  override def createUMLPackageMerge: Try[UMLPackageMerge[MagicDrawUML]] =
    Success(f.createPackageMergeInstance())

  override def createUMLParameter: Try[UMLParameter[MagicDrawUML]] =
    Success(f.createParameterInstance())

  override def createUMLParameterSet: Try[UMLParameterSet[MagicDrawUML]] =
    Success(f.createParameterSetInstance())

  override def createUMLPartDecomposition: Try[UMLPartDecomposition[MagicDrawUML]] =
    Success(f.createPartDecompositionInstance())

  override def createUMLPort: Try[UMLPort[MagicDrawUML]] =
    Success(f.createPortInstance())

  override def createUMLPrimitiveType: Try[UMLPrimitiveType[MagicDrawUML]] =
    Success(f.createPrimitiveTypeInstance())

  override def createUMLProfile: Try[UMLProfile[MagicDrawUML]] =
    Success(f.createProfileInstance())

  override def createUMLProfileApplication: Try[UMLProfileApplication[MagicDrawUML]] =
    Success(f.createProfileApplicationInstance())

  override def createUMLProperty: Try[UMLProperty[MagicDrawUML]] =
    Success(f.createPropertyInstance())

  override def createUMLProtocolConformance: Try[UMLProtocolConformance[MagicDrawUML]] =
    Success(f.createProtocolConformanceInstance())

  override def createUMLProtocolStateMachine: Try[UMLProtocolStateMachine[MagicDrawUML]] =
    Success(f.createProtocolStateMachineInstance())

  override def createUMLProtocolTransition: Try[UMLProtocolTransition[MagicDrawUML]] =
    Success(f.createProtocolTransitionInstance())

  override def createUMLPseudostate: Try[UMLPseudostate[MagicDrawUML]] =
    Success(f.createPseudostateInstance())

  override def createUMLQualifierValue: Try[UMLQualifierValue[MagicDrawUML]] =
    Success(f.createQualifierValueInstance())

  override def createUMLRaiseExceptionAction: Try[UMLRaiseExceptionAction[MagicDrawUML]] =
    Success(f.createRaiseExceptionActionInstance())

  override def createUMLReadExtentAction: Try[UMLReadExtentAction[MagicDrawUML]] =
    Success(f.createReadExtentActionInstance())

  override def createUMLReadIsClassifiedObjectAction: Try[UMLReadIsClassifiedObjectAction[MagicDrawUML]] =
    Success(f.createReadIsClassifiedObjectActionInstance())

  override def createUMLReadLinkAction: Try[UMLReadLinkAction[MagicDrawUML]] =
    Success(f.createReadLinkActionInstance())

  override def createUMLReadLinkObjectEndAction: Try[UMLReadLinkObjectEndAction[MagicDrawUML]] =
    Success(f.createReadLinkObjectEndActionInstance())

  override def createUMLReadLinkObjectEndQualifierAction: Try[UMLReadLinkObjectEndQualifierAction[MagicDrawUML]] =
    Success(f.createReadLinkObjectEndQualifierActionInstance())

  override def createUMLReadSelfAction: Try[UMLReadSelfAction[MagicDrawUML]] =
    Success(f.createReadSelfActionInstance())

  override def createUMLReadStructuralFeatureAction: Try[UMLReadStructuralFeatureAction[MagicDrawUML]] =
    Success(f.createReadStructuralFeatureActionInstance())

  override def createUMLReadVariableAction: Try[UMLReadVariableAction[MagicDrawUML]] =
    Success(f.createReadVariableActionInstance())

  override def createUMLRealization: Try[UMLRealization[MagicDrawUML]] =
    Success(f.createRealizationInstance())

  override def createUMLReception: Try[UMLReception[MagicDrawUML]] =
    Success(f.createReceptionInstance())

  override def createUMLReclassifyObjectAction: Try[UMLReclassifyObjectAction[MagicDrawUML]] =
    Success(f.createReclassifyObjectActionInstance())

  override def createUMLRedefinableTemplateSignature: Try[UMLRedefinableTemplateSignature[MagicDrawUML]] =
    Success(f.createRedefinableTemplateSignatureInstance())

  override def createUMLReduceAction: Try[UMLReduceAction[MagicDrawUML]] =
    Success(f.createReduceActionInstance())

  override def createUMLRegion: Try[UMLRegion[MagicDrawUML]] =
    Success(f.createRegionInstance())

  override def createUMLRemoveStructuralFeatureValueAction: Try[UMLRemoveStructuralFeatureValueAction[MagicDrawUML]] =
    Success(f.createRemoveStructuralFeatureValueActionInstance())

  override def createUMLRemoveVariableValueAction: Try[UMLRemoveVariableValueAction[MagicDrawUML]] =
    Success(f.createRemoveVariableValueActionInstance())

  override def createUMLReplyAction: Try[UMLReplyAction[MagicDrawUML]] =
    Success(f.createReplyActionInstance())

  override def createUMLSendObjectAction: Try[UMLSendObjectAction[MagicDrawUML]] =
    Success(f.createSendObjectActionInstance())

  override def createUMLSendSignalAction: Try[UMLSendSignalAction[MagicDrawUML]] =
    Success(f.createSendSignalActionInstance())

  override def createUMLSequenceNode: Try[UMLSequenceNode[MagicDrawUML]] =
    Success(f.createSequenceNodeInstance())

  override def createUMLSignal: Try[UMLSignal[MagicDrawUML]] =
    Success(f.createSignalInstance())

  override def createUMLSignalEvent: Try[UMLSignalEvent[MagicDrawUML]] =
    Success(f.createSignalEventInstance())

  override def createUMLSlot: Try[UMLSlot[MagicDrawUML]] =
    Success(f.createSlotInstance())

  override def createUMLStartClassifierBehaviorAction: Try[UMLStartClassifierBehaviorAction[MagicDrawUML]] =
    Success(f.createStartClassifierBehaviorActionInstance())

  override def createUMLStartObjectBehaviorAction: Try[UMLStartObjectBehaviorAction[MagicDrawUML]] =
    Success(f.createStartObjectBehaviorActionInstance())

  override def createUMLState: Try[UMLState[MagicDrawUML]] =
    Success(f.createStateInstance())

  override def createUMLStateInvariant: Try[UMLStateInvariant[MagicDrawUML]] =
    Success(f.createStateInvariantInstance())

  override def createUMLStateMachine: Try[UMLStateMachine[MagicDrawUML]] =
    Success(f.createStateMachineInstance())

  override def createUMLStereotype: Try[UMLStereotype[MagicDrawUML]] =
    Success(f.createStereotypeInstance())

  override def createUMLStringExpression: Try[UMLStringExpression[MagicDrawUML]] =
    Success(f.createStringExpressionInstance())

  override def createUMLStructuredActivityNode: Try[UMLStructuredActivityNode[MagicDrawUML]] =
    Success(f.createStructuredActivityNodeInstance())

  override def createUMLSubstitution: Try[UMLSubstitution[MagicDrawUML]] =
    Success(f.createSubstitutionInstance())

  override def createUMLTemplateBinding: Try[UMLTemplateBinding[MagicDrawUML]] =
    Success(f.createTemplateBindingInstance())

  override def createUMLTemplateParameter: Try[UMLTemplateParameter[MagicDrawUML]] =
    Success(f.createTemplateParameterInstance())

  override def createUMLTemplateParameterSubstitution: Try[UMLTemplateParameterSubstitution[MagicDrawUML]] =
    Success(f.createTemplateParameterSubstitutionInstance())

  override def createUMLTemplateSignature: Try[UMLTemplateSignature[MagicDrawUML]] =
    Success(f.createTemplateSignatureInstance())

  override def createUMLTestIdentityAction: Try[UMLTestIdentityAction[MagicDrawUML]] =
    Success(f.createTestIdentityActionInstance())

  override def createUMLTimeConstraint: Try[UMLTimeConstraint[MagicDrawUML]] =
    Success(f.createTimeConstraintInstance())

  override def createUMLTimeEvent: Try[UMLTimeEvent[MagicDrawUML]] =
    Success(f.createTimeEventInstance())

  override def createUMLTimeExpression: Try[UMLTimeExpression[MagicDrawUML]] =
    Success(f.createTimeExpressionInstance())

  override def createUMLTimeInterval: Try[UMLTimeInterval[MagicDrawUML]] =
    Success(f.createTimeIntervalInstance())

  override def createUMLTimeObservation: Try[UMLTimeObservation[MagicDrawUML]] =
    Success(f.createTimeObservationInstance())

  override def createUMLTransition: Try[UMLTransition[MagicDrawUML]] =
    Success(f.createTransitionInstance())

  override def createUMLTrigger: Try[UMLTrigger[MagicDrawUML]] =
    Success(f.createTriggerInstance())

  override def createUMLUnmarshallAction: Try[UMLUnmarshallAction[MagicDrawUML]] =
    Success(f.createUnmarshallActionInstance())

  override def createUMLUsage: Try[UMLUsage[MagicDrawUML]] =
    Success(f.createUsageInstance())

  override def createUMLUseCase: Try[UMLUseCase[MagicDrawUML]] =
    Success(f.createUseCaseInstance())

  override def createUMLValuePin: Try[UMLValuePin[MagicDrawUML]] =
    Success(f.createValuePinInstance())

  override def createUMLValueSpecificationAction: Try[UMLValueSpecificationAction[MagicDrawUML]] =
    Success(f.createValueSpecificationActionInstance())

  override def createUMLVariable: Try[UMLVariable[MagicDrawUML]] =
    Success(f.createVariableInstance())

}