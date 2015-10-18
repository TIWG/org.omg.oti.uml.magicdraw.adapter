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

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.write.api.UMLFactory

import scalaz._

case class MagicDrawUMLFactory(mdUMLUtils: MagicDrawUMLUtil)
  extends UMLFactory[MagicDrawUML] {

  val f = mdUMLUtils.project.getElementsFactory

  import mdUMLUtils._

  override def createUMLAbstraction: \/[NonEmptyList[UMLError.UException],UMLAbstraction[MagicDrawUML]] =
    \/-(f.createAbstractionInstance())

  override def createUMLAcceptCallAction: \/[NonEmptyList[UMLError.UException],UMLAcceptCallAction[MagicDrawUML]] =
    \/-(f.createAcceptCallActionInstance())

  override def createUMLAcceptEventAction: \/[NonEmptyList[UMLError.UException],UMLAcceptEventAction[MagicDrawUML]] =
    \/-(f.createAcceptEventActionInstance())

  override def createUMLActionExecutionSpecification: \/[NonEmptyList[UMLError.UException],UMLActionExecutionSpecification[Uml]] =
    \/-(f.createActionExecutionSpecificationInstance())

  override def createUMLActionInputPin: \/[NonEmptyList[UMLError.UException],UMLActionInputPin[MagicDrawUML]] =
    \/-(f.createActionInputPinInstance())

  override def createUMLActivity: \/[NonEmptyList[UMLError.UException],UMLActivity[MagicDrawUML]] =
    \/-(f.createActivityInstance())

  override def createUMLActivityFinalNode: \/[NonEmptyList[UMLError.UException],UMLActivityFinalNode[MagicDrawUML]] =
    \/-(f.createActivityFinalNodeInstance())

  override def createUMLActivityParameterNode: \/[NonEmptyList[UMLError.UException],UMLActivityParameterNode[MagicDrawUML]] =
    \/-(f.createActivityParameterNodeInstance())

  override def createUMLActivityPartition: \/[NonEmptyList[UMLError.UException],UMLActivityPartition[MagicDrawUML]] =
    \/-(f.createActivityPartitionInstance())

  override def createUMLActor: \/[NonEmptyList[UMLError.UException],UMLActor[MagicDrawUML]] =
    \/-(f.createActorInstance())

  override def createUMLAddStructuralFeatureValueAction: \/[NonEmptyList[UMLError.UException],UMLAddStructuralFeatureValueAction[MagicDrawUML]] =
    \/-(f.createAddStructuralFeatureValueActionInstance())

  override def createUMLAddVariableValueAction: \/[NonEmptyList[UMLError.UException],UMLAddVariableValueAction[MagicDrawUML]] =
    \/-(f.createAddVariableValueActionInstance())

  override def createUMLAnyReceiveEvent: \/[NonEmptyList[UMLError.UException],UMLAnyReceiveEvent[MagicDrawUML]] =
    \/-(f.createAnyReceiveEventInstance())

  override def createUMLArtifact: \/[NonEmptyList[UMLError.UException],UMLArtifact[MagicDrawUML]] =
    \/-(f.createArtifactInstance())

  override def createUMLAssociation: \/[NonEmptyList[UMLError.UException],UMLAssociation[MagicDrawUML]] =
    \/-(f.createAssociationInstance())

  override def createUMLAssociationClass: \/[NonEmptyList[UMLError.UException],UMLAssociationClass[MagicDrawUML]] =
    \/-(f.createAssociationClassInstance())

  override def createUMLBehaviorExecutionSpecification: \/[NonEmptyList[UMLError.UException],UMLBehaviorExecutionSpecification[MagicDrawUML]] =
    \/-(f.createBehaviorExecutionSpecificationInstance())

  override def createUMLBroadcastSignalAction: \/[NonEmptyList[UMLError.UException],UMLBroadcastSignalAction[MagicDrawUML]] =
    \/-(f.createBroadcastSignalActionInstance())

  override def createUMLCallBehaviorAction: \/[NonEmptyList[UMLError.UException],UMLCallBehaviorAction[MagicDrawUML]] =
    \/-(f.createCallBehaviorActionInstance())

  override def createUMLCallEvent: \/[NonEmptyList[UMLError.UException],UMLCallEvent[MagicDrawUML]] =
    \/-(f.createCallEventInstance())

  override def createUMLCallOperationAction: \/[NonEmptyList[UMLError.UException],UMLCallOperationAction[MagicDrawUML]] =
    \/-(f.createCallOperationActionInstance())

  override def createUMLCentralBufferNode: \/[NonEmptyList[UMLError.UException],UMLCentralBufferNode[MagicDrawUML]] =
    \/-(f.createCentralBufferNodeInstance())

  override def createUMLChangeEvent: \/[NonEmptyList[UMLError.UException],UMLChangeEvent[MagicDrawUML]] =
    \/-(f.createChangeEventInstance())

  override def createUMLClass: \/[NonEmptyList[UMLError.UException],UMLClass[MagicDrawUML]] =
    \/-(f.createClassInstance())

  override def createUMLClassifierTemplateParameter: \/[NonEmptyList[UMLError.UException],UMLClassifierTemplateParameter[MagicDrawUML]] =
    \/-(f.createClassifierTemplateParameterInstance())

  override def createUMLClause: \/[NonEmptyList[UMLError.UException],UMLClause[MagicDrawUML]] =
    \/-(f.createClauseInstance())

  override def createUMLClearAssociationAction: \/[NonEmptyList[UMLError.UException],UMLClearAssociationAction[MagicDrawUML]] =
    \/-(f.createClearAssociationActionInstance())

  override def createUMLClearStructuralFeatureAction: \/[NonEmptyList[UMLError.UException],UMLClearStructuralFeatureAction[MagicDrawUML]] =
    \/-(f.createClearStructuralFeatureActionInstance())

  override def createUMLClearVariableAction: \/[NonEmptyList[UMLError.UException],UMLClearVariableAction[MagicDrawUML]] =
    \/-(f.createClearVariableActionInstance())

  override def createUMLCollaboration: \/[NonEmptyList[UMLError.UException],UMLCollaboration[MagicDrawUML]] =
    \/-(f.createCollaborationInstance())

  override def createUMLCollaborationUse: \/[NonEmptyList[UMLError.UException],UMLCollaborationUse[MagicDrawUML]] =
    \/-(f.createCollaborationUseInstance())

  override def createUMLCombinedFragment: \/[NonEmptyList[UMLError.UException],UMLCombinedFragment[MagicDrawUML]] =
    \/-(f.createCombinedFragmentInstance())

  override def createUMLComment: \/[NonEmptyList[UMLError.UException],UMLComment[MagicDrawUML]] =
    \/-(f.createCommentInstance())

  override def createUMLCommunicationPath: \/[NonEmptyList[UMLError.UException],UMLCommunicationPath[MagicDrawUML]] =
    \/-(f.createCommunicationPathInstance())

  override def createUMLComponent: \/[NonEmptyList[UMLError.UException],UMLComponent[MagicDrawUML]] =
    \/-(f.createComponentInstance())

  override def createUMLComponentRealization: \/[NonEmptyList[UMLError.UException],UMLComponentRealization[MagicDrawUML]] =
    \/-(f.createComponentRealizationInstance())

  override def createUMLConditionalNode: \/[NonEmptyList[UMLError.UException],UMLConditionalNode[MagicDrawUML]] =
    \/-(f.createConditionalNodeInstance())

  override def createUMLConnectableElementTemplateParameter: \/[NonEmptyList[UMLError.UException],UMLConnectableElementTemplateParameter[MagicDrawUML]] =
    \/-(f.createConnectableElementTemplateParameterInstance())

  override def createUMLConnectionPointReference: \/[NonEmptyList[UMLError.UException],UMLConnectionPointReference[MagicDrawUML]] =
    \/-(f.createConnectionPointReferenceInstance())

  override def createUMLConnector: \/[NonEmptyList[UMLError.UException],UMLConnector[MagicDrawUML]] =
    \/-(f.createConnectorInstance())

  override def createUMLConnectorEnd: \/[NonEmptyList[UMLError.UException],UMLConnectorEnd[MagicDrawUML]] =
    \/-(f.createConnectorEndInstance())

  override def createUMLConsiderIgnoreFragment: \/[NonEmptyList[UMLError.UException],UMLConsiderIgnoreFragment[MagicDrawUML]] =
    \/-(f.createConsiderIgnoreFragmentInstance())

  override def createUMLConstraint: \/[NonEmptyList[UMLError.UException],UMLConstraint[MagicDrawUML]] =
    \/-(f.createConstraintInstance())

  override def createUMLContinuation: \/[NonEmptyList[UMLError.UException],UMLContinuation[MagicDrawUML]] =
    \/-(f.createContinuationInstance())

  override def createUMLControlFlow: \/[NonEmptyList[UMLError.UException],UMLControlFlow[MagicDrawUML]] =
    \/-(f.createControlFlowInstance())

  override def createUMLCreateLinkAction: \/[NonEmptyList[UMLError.UException],UMLCreateLinkAction[MagicDrawUML]] =
    \/-(f.createCreateLinkActionInstance())

  override def createUMLCreateLinkObjectAction: \/[NonEmptyList[UMLError.UException],UMLCreateLinkObjectAction[MagicDrawUML]] =
    \/-(f.createCreateLinkObjectActionInstance())

  override def createUMLCreateObjectAction: \/[NonEmptyList[UMLError.UException],UMLCreateObjectAction[MagicDrawUML]] =
    \/-(f.createCreateObjectActionInstance())

  override def createUMLDataStoreNode: \/[NonEmptyList[UMLError.UException],UMLDataStoreNode[MagicDrawUML]] =
    \/-(f.createDataStoreNodeInstance())

  override def createUMLDataType: \/[NonEmptyList[UMLError.UException],UMLDataType[MagicDrawUML]] =
    \/-(f.createDataTypeInstance())

  override def createUMLDecisionNode: \/[NonEmptyList[UMLError.UException],UMLDecisionNode[MagicDrawUML]] =
    \/-(f.createDecisionNodeInstance())

  override def createUMLDependency: \/[NonEmptyList[UMLError.UException],UMLDependency[MagicDrawUML]] =
    \/-(f.createDependencyInstance())

  override def createUMLDeployment: \/[NonEmptyList[UMLError.UException],UMLDeployment[MagicDrawUML]] =
    \/-(f.createDeploymentInstance())

  override def createUMLDeploymentSpecification: \/[NonEmptyList[UMLError.UException],UMLDeploymentSpecification[MagicDrawUML]] =
    \/-(f.createDeploymentSpecificationInstance())

  override def createUMLDestroyLinkAction: \/[NonEmptyList[UMLError.UException],UMLDestroyLinkAction[MagicDrawUML]] =
    \/-(f.createDestroyLinkActionInstance())

  override def createUMLDestroyObjectAction: \/[NonEmptyList[UMLError.UException],UMLDestroyObjectAction[MagicDrawUML]] =
    \/-(f.createDestroyObjectActionInstance())

  override def createUMLDestructionOccurrenceSpecification: \/[NonEmptyList[UMLError.UException],UMLDestructionOccurrenceSpecification[MagicDrawUML]] =
    \/-(f.createDestructionOccurrenceSpecificationInstance())

  override def createUMLDevice: \/[NonEmptyList[UMLError.UException],UMLDevice[MagicDrawUML]] =
    \/-(f.createDeviceInstance())

  override def createUMLDuration: \/[NonEmptyList[UMLError.UException],UMLDuration[MagicDrawUML]] =
    \/-(f.createDurationInstance())

  override def createUMLDurationConstraint: \/[NonEmptyList[UMLError.UException],UMLDurationConstraint[MagicDrawUML]] =
    \/-(f.createDurationConstraintInstance())

  override def createUMLDurationInterval: \/[NonEmptyList[UMLError.UException],UMLDurationInterval[MagicDrawUML]] =
    \/-(f.createDurationIntervalInstance())

  override def createUMLDurationObservation: \/[NonEmptyList[UMLError.UException],UMLDurationObservation[MagicDrawUML]] =
    \/-(f.createDurationObservationInstance())

  override def createUMLElementImport: \/[NonEmptyList[UMLError.UException],UMLElementImport[MagicDrawUML]] =
    \/-(f.createElementImportInstance())

  override def createUMLEnumeration: \/[NonEmptyList[UMLError.UException],UMLEnumeration[MagicDrawUML]] =
    \/-(f.createEnumerationInstance())

  override def createUMLEnumerationLiteral: \/[NonEmptyList[UMLError.UException],UMLEnumerationLiteral[MagicDrawUML]] =
    \/-(f.createEnumerationLiteralInstance())

  override def createUMLExceptionHandler: \/[NonEmptyList[UMLError.UException],UMLExceptionHandler[MagicDrawUML]] =
    \/-(f.createExceptionHandlerInstance())

  override def createUMLExecutionEnvironment: \/[NonEmptyList[UMLError.UException],UMLExecutionEnvironment[MagicDrawUML]] =
    \/-(f.createExecutionEnvironmentInstance())

  override def createUMLExecutionOccurrenceSpecification: \/[NonEmptyList[UMLError.UException],UMLExecutionOccurrenceSpecification[MagicDrawUML]] =
    \/-(f.createExecutionOccurrenceSpecificationInstance())

  override def createUMLExpansionNode: \/[NonEmptyList[UMLError.UException],UMLExpansionNode[MagicDrawUML]] =
    \/-(f.createExpansionNodeInstance())

  override def createUMLExpansionRegion: \/[NonEmptyList[UMLError.UException],UMLExpansionRegion[MagicDrawUML]] =
    \/-(f.createExpansionRegionInstance())

  override def createUMLExpression: \/[NonEmptyList[UMLError.UException],UMLExpression[MagicDrawUML]] =
    \/-(f.createExpressionInstance())

  override def createUMLExtend: \/[NonEmptyList[UMLError.UException],UMLExtend[MagicDrawUML]] =
    \/-(f.createExtendInstance())

  override def createUMLExtension: \/[NonEmptyList[UMLError.UException],UMLExtension[MagicDrawUML]] =
    \/-(f.createExtensionInstance())

  override def createUMLExtensionEnd: \/[NonEmptyList[UMLError.UException],UMLExtensionEnd[MagicDrawUML]] =
    \/-(f.createExtensionEndInstance())

  override def createUMLExtensionPoint: \/[NonEmptyList[UMLError.UException],UMLExtensionPoint[MagicDrawUML]] =
    \/-(f.createExtensionPointInstance())

  override def createUMLFinalState: \/[NonEmptyList[UMLError.UException],UMLFinalState[MagicDrawUML]] =
    \/-(f.createFinalStateInstance())

  override def createUMLFlowFinalNode: \/[NonEmptyList[UMLError.UException],UMLFlowFinalNode[MagicDrawUML]] =
    \/-(f.createFlowFinalNodeInstance())

  override def createUMLForkNode: \/[NonEmptyList[UMLError.UException],UMLForkNode[MagicDrawUML]] =
    \/-(f.createForkNodeInstance())

  override def createUMLFunctionBehavior: \/[NonEmptyList[UMLError.UException],UMLFunctionBehavior[MagicDrawUML]] =
    \/-(f.createFunctionBehaviorInstance())

  override def createUMLGate: \/[NonEmptyList[UMLError.UException],UMLGate[MagicDrawUML]] =
    \/-(f.createGateInstance())

  override def createUMLGeneralOrdering: \/[NonEmptyList[UMLError.UException],UMLGeneralOrdering[MagicDrawUML]] =
    \/-(f.createGeneralOrderingInstance())

  override def createUMLGeneralization: \/[NonEmptyList[UMLError.UException],UMLGeneralization[MagicDrawUML]] =
    \/-(f.createGeneralizationInstance())

  override def createUMLGeneralizationSet: \/[NonEmptyList[UMLError.UException],UMLGeneralizationSet[MagicDrawUML]] =
    \/-(f.createGeneralizationSetInstance())

  override def createUMLImage: \/[NonEmptyList[UMLError.UException],UMLImage[MagicDrawUML]] =
    \/-(f.createImageInstance())

  override def createUMLInclude: \/[NonEmptyList[UMLError.UException],UMLInclude[MagicDrawUML]] =
    \/-(f.createIncludeInstance())

  override def createUMLInformationFlow: \/[NonEmptyList[UMLError.UException],UMLInformationFlow[MagicDrawUML]] =
    \/-(f.createInformationFlowInstance())

  override def createUMLInformationItem: \/[NonEmptyList[UMLError.UException],UMLInformationItem[MagicDrawUML]] =
    \/-(f.createInformationItemInstance())

  override def createUMLInitialNode: \/[NonEmptyList[UMLError.UException],UMLInitialNode[MagicDrawUML]] =
    \/-(f.createInitialNodeInstance())

  override def createUMLInputPin: \/[NonEmptyList[UMLError.UException],UMLInputPin[MagicDrawUML]] =
    \/-(f.createInputPinInstance())

  override def createUMLInstanceSpecification: \/[NonEmptyList[UMLError.UException],UMLInstanceSpecification[MagicDrawUML]] =
    \/-(f.createInstanceSpecificationInstance())

  override def createUMLInstanceValue: \/[NonEmptyList[UMLError.UException],UMLInstanceValue[MagicDrawUML]] =
    \/-(f.createInstanceValueInstance())

  override def createUMLInteraction: \/[NonEmptyList[UMLError.UException],UMLInteraction[MagicDrawUML]] =
    \/-(f.createInteractionInstance())

  override def createUMLInteractionConstraint: \/[NonEmptyList[UMLError.UException],UMLInteractionConstraint[MagicDrawUML]] =
    \/-(f.createInteractionConstraintInstance())

  override def createUMLInteractionOperand: \/[NonEmptyList[UMLError.UException],UMLInteractionOperand[MagicDrawUML]] =
    \/-(f.createInteractionOperandInstance())

  override def createUMLInteractionUse: \/[NonEmptyList[UMLError.UException],UMLInteractionUse[MagicDrawUML]] =
    \/-(f.createInteractionUseInstance())

  override def createUMLInterface: \/[NonEmptyList[UMLError.UException],UMLInterface[MagicDrawUML]] =
    \/-(f.createInterfaceInstance())

  override def createUMLInterfaceRealization: \/[NonEmptyList[UMLError.UException],UMLInterfaceRealization[MagicDrawUML]] =
    \/-(f.createInterfaceRealizationInstance())

  override def createUMLInterruptibleActivityRegion: \/[NonEmptyList[UMLError.UException],UMLInterruptibleActivityRegion[MagicDrawUML]] =
    \/-(f.createInterruptibleActivityRegionInstance())

  override def createUMLInterval: \/[NonEmptyList[UMLError.UException],UMLInterval[MagicDrawUML]] =
    \/-(f.createIntervalInstance())

  override def createUMLIntervalConstraint: \/[NonEmptyList[UMLError.UException],UMLIntervalConstraint[MagicDrawUML]] =
    \/-(f.createIntervalConstraintInstance())

  override def createUMLJoinNode: \/[NonEmptyList[UMLError.UException],UMLJoinNode[MagicDrawUML]] =
    \/-(f.createJoinNodeInstance())

  override def createUMLLifeline: \/[NonEmptyList[UMLError.UException],UMLLifeline[MagicDrawUML]] =
    \/-(f.createLifelineInstance())

  override def createUMLLinkEndCreationData: \/[NonEmptyList[UMLError.UException],UMLLinkEndCreationData[MagicDrawUML]] =
    \/-(f.createLinkEndCreationDataInstance())

  override def createUMLLinkEndData: \/[NonEmptyList[UMLError.UException],UMLLinkEndData[MagicDrawUML]] =
    \/-(f.createLinkEndDataInstance())

  override def createUMLLinkEndDestructionData: \/[NonEmptyList[UMLError.UException],UMLLinkEndDestructionData[MagicDrawUML]] =
    \/-(f.createLinkEndDestructionDataInstance())

  override def createUMLLiteralBoolean: \/[NonEmptyList[UMLError.UException],UMLLiteralBoolean[MagicDrawUML]] =
    \/-(f.createLiteralBooleanInstance())

  override def createUMLLiteralInteger: \/[NonEmptyList[UMLError.UException],UMLLiteralInteger[MagicDrawUML]] =
    \/-(f.createLiteralIntegerInstance())

  override def createUMLLiteralNull: \/[NonEmptyList[UMLError.UException],UMLLiteralNull[MagicDrawUML]] =
    \/-(f.createLiteralNullInstance())

  override def createUMLLiteralReal: \/[NonEmptyList[UMLError.UException],UMLLiteralReal[MagicDrawUML]] =
    \/-(f.createLiteralRealInstance())

  override def createUMLLiteralString: \/[NonEmptyList[UMLError.UException],UMLLiteralString[MagicDrawUML]] =
    \/-(f.createLiteralStringInstance())

  override def createUMLLiteralUnlimitedNatural: \/[NonEmptyList[UMLError.UException],UMLLiteralUnlimitedNatural[MagicDrawUML]] =
    \/-(f.createLiteralUnlimitedNaturalInstance())

  override def createUMLLoopNode: \/[NonEmptyList[UMLError.UException],UMLLoopNode[MagicDrawUML]] =
    \/-(f.createLoopNodeInstance())

  override def createUMLManifestation: \/[NonEmptyList[UMLError.UException],UMLManifestation[MagicDrawUML]] =
    \/-(f.createManifestationInstance())

  override def createUMLMergeNode: \/[NonEmptyList[UMLError.UException],UMLMergeNode[MagicDrawUML]] =
    \/-(f.createMergeNodeInstance())

  override def createUMLMessage: \/[NonEmptyList[UMLError.UException],UMLMessage[MagicDrawUML]] =
    \/-(f.createMessageInstance())

  override def createUMLMessageOccurrenceSpecification: \/[NonEmptyList[UMLError.UException],UMLMessageOccurrenceSpecification[MagicDrawUML]] =
    \/-(f.createMessageOccurrenceSpecificationInstance())

  override def createUMLModel: \/[NonEmptyList[UMLError.UException],UMLModel[MagicDrawUML]] =
    \/-(f.createModelInstance())

  override def createUMLNode: \/[NonEmptyList[UMLError.UException],UMLNode[MagicDrawUML]] =
    \/-(f.createNodeInstance())

  override def createUMLObjectFlow: \/[NonEmptyList[UMLError.UException],UMLObjectFlow[MagicDrawUML]] =
    \/-(f.createObjectFlowInstance())

  override def createUMLOccurrenceSpecification: \/[NonEmptyList[UMLError.UException],UMLOccurrenceSpecification[MagicDrawUML]] =
    \/-(f.createOccurrenceSpecificationInstance())

  override def createUMLOpaqueAction: \/[NonEmptyList[UMLError.UException],UMLOpaqueAction[MagicDrawUML]] =
    \/-(f.createOpaqueActionInstance())

  override def createUMLOpaqueBehavior: \/[NonEmptyList[UMLError.UException],UMLOpaqueBehavior[MagicDrawUML]] =
    \/-(f.createOpaqueBehaviorInstance())

  override def createUMLOpaqueExpression: \/[NonEmptyList[UMLError.UException],UMLOpaqueExpression[MagicDrawUML]] =
    \/-(f.createOpaqueExpressionInstance())

  override def createUMLOperation: \/[NonEmptyList[UMLError.UException],UMLOperation[MagicDrawUML]] =
    \/-(f.createOperationInstance())

  override def createUMLOperationTemplateParameter: \/[NonEmptyList[UMLError.UException],UMLOperationTemplateParameter[MagicDrawUML]] =
    \/-(f.createOperationTemplateParameterInstance())

  override def createUMLOutputPin: \/[NonEmptyList[UMLError.UException],UMLOutputPin[MagicDrawUML]] =
    \/-(f.createOutputPinInstance())

  override def createUMLPackage: \/[NonEmptyList[UMLError.UException],UMLPackage[MagicDrawUML]] =
    \/-(f.createPackageInstance())

  override def createUMLPackageImport: \/[NonEmptyList[UMLError.UException],UMLPackageImport[MagicDrawUML]] =
    \/-(f.createPackageImportInstance())

  override def createUMLPackageMerge: \/[NonEmptyList[UMLError.UException],UMLPackageMerge[MagicDrawUML]] =
    \/-(f.createPackageMergeInstance())

  override def createUMLParameter: \/[NonEmptyList[UMLError.UException],UMLParameter[MagicDrawUML]] =
    \/-(f.createParameterInstance())

  override def createUMLParameterSet: \/[NonEmptyList[UMLError.UException],UMLParameterSet[MagicDrawUML]] =
    \/-(f.createParameterSetInstance())

  override def createUMLPartDecomposition: \/[NonEmptyList[UMLError.UException],UMLPartDecomposition[MagicDrawUML]] =
    \/-(f.createPartDecompositionInstance())

  override def createUMLPort: \/[NonEmptyList[UMLError.UException],UMLPort[MagicDrawUML]] =
    \/-(f.createPortInstance())

  override def createUMLPrimitiveType: \/[NonEmptyList[UMLError.UException],UMLPrimitiveType[MagicDrawUML]] =
    \/-(f.createPrimitiveTypeInstance())

  override def createUMLProfile: \/[NonEmptyList[UMLError.UException],UMLProfile[MagicDrawUML]] =
    \/-(f.createProfileInstance())

  override def createUMLProfileApplication: \/[NonEmptyList[UMLError.UException],UMLProfileApplication[MagicDrawUML]] =
    \/-(f.createProfileApplicationInstance())

  override def createUMLProperty: \/[NonEmptyList[UMLError.UException],UMLProperty[MagicDrawUML]] =
    \/-(f.createPropertyInstance())

  override def createUMLProtocolConformance: \/[NonEmptyList[UMLError.UException],UMLProtocolConformance[MagicDrawUML]] =
    \/-(f.createProtocolConformanceInstance())

  override def createUMLProtocolStateMachine: \/[NonEmptyList[UMLError.UException],UMLProtocolStateMachine[MagicDrawUML]] =
    \/-(f.createProtocolStateMachineInstance())

  override def createUMLProtocolTransition: \/[NonEmptyList[UMLError.UException],UMLProtocolTransition[MagicDrawUML]] =
    \/-(f.createProtocolTransitionInstance())

  override def createUMLPseudostate: \/[NonEmptyList[UMLError.UException],UMLPseudostate[MagicDrawUML]] =
    \/-(f.createPseudostateInstance())

  override def createUMLQualifierValue: \/[NonEmptyList[UMLError.UException],UMLQualifierValue[MagicDrawUML]] =
    \/-(f.createQualifierValueInstance())

  override def createUMLRaiseExceptionAction: \/[NonEmptyList[UMLError.UException],UMLRaiseExceptionAction[MagicDrawUML]] =
    \/-(f.createRaiseExceptionActionInstance())

  override def createUMLReadExtentAction: \/[NonEmptyList[UMLError.UException],UMLReadExtentAction[MagicDrawUML]] =
    \/-(f.createReadExtentActionInstance())

  override def createUMLReadIsClassifiedObjectAction: \/[NonEmptyList[UMLError.UException],UMLReadIsClassifiedObjectAction[MagicDrawUML]] =
    \/-(f.createReadIsClassifiedObjectActionInstance())

  override def createUMLReadLinkAction: \/[NonEmptyList[UMLError.UException],UMLReadLinkAction[MagicDrawUML]] =
    \/-(f.createReadLinkActionInstance())

  override def createUMLReadLinkObjectEndAction: \/[NonEmptyList[UMLError.UException],UMLReadLinkObjectEndAction[MagicDrawUML]] =
    \/-(f.createReadLinkObjectEndActionInstance())

  override def createUMLReadLinkObjectEndQualifierAction: \/[NonEmptyList[UMLError.UException],UMLReadLinkObjectEndQualifierAction[MagicDrawUML]] =
    \/-(f.createReadLinkObjectEndQualifierActionInstance())

  override def createUMLReadSelfAction: \/[NonEmptyList[UMLError.UException],UMLReadSelfAction[MagicDrawUML]] =
    \/-(f.createReadSelfActionInstance())

  override def createUMLReadStructuralFeatureAction: \/[NonEmptyList[UMLError.UException],UMLReadStructuralFeatureAction[MagicDrawUML]] =
    \/-(f.createReadStructuralFeatureActionInstance())

  override def createUMLReadVariableAction: \/[NonEmptyList[UMLError.UException],UMLReadVariableAction[MagicDrawUML]] =
    \/-(f.createReadVariableActionInstance())

  override def createUMLRealization: \/[NonEmptyList[UMLError.UException],UMLRealization[MagicDrawUML]] =
    \/-(f.createRealizationInstance())

  override def createUMLReception: \/[NonEmptyList[UMLError.UException],UMLReception[MagicDrawUML]] =
    \/-(f.createReceptionInstance())

  override def createUMLReclassifyObjectAction: \/[NonEmptyList[UMLError.UException],UMLReclassifyObjectAction[MagicDrawUML]] =
    \/-(f.createReclassifyObjectActionInstance())

  override def createUMLRedefinableTemplateSignature: \/[NonEmptyList[UMLError.UException],UMLRedefinableTemplateSignature[MagicDrawUML]] =
    \/-(f.createRedefinableTemplateSignatureInstance())

  override def createUMLReduceAction: \/[NonEmptyList[UMLError.UException],UMLReduceAction[MagicDrawUML]] =
    \/-(f.createReduceActionInstance())

  override def createUMLRegion: \/[NonEmptyList[UMLError.UException],UMLRegion[MagicDrawUML]] =
    \/-(f.createRegionInstance())

  override def createUMLRemoveStructuralFeatureValueAction: \/[NonEmptyList[UMLError.UException],UMLRemoveStructuralFeatureValueAction[MagicDrawUML]] =
    \/-(f.createRemoveStructuralFeatureValueActionInstance())

  override def createUMLRemoveVariableValueAction: \/[NonEmptyList[UMLError.UException],UMLRemoveVariableValueAction[MagicDrawUML]] =
    \/-(f.createRemoveVariableValueActionInstance())

  override def createUMLReplyAction: \/[NonEmptyList[UMLError.UException],UMLReplyAction[MagicDrawUML]] =
    \/-(f.createReplyActionInstance())

  override def createUMLSendObjectAction: \/[NonEmptyList[UMLError.UException],UMLSendObjectAction[MagicDrawUML]] =
    \/-(f.createSendObjectActionInstance())

  override def createUMLSendSignalAction: \/[NonEmptyList[UMLError.UException],UMLSendSignalAction[MagicDrawUML]] =
    \/-(f.createSendSignalActionInstance())

  override def createUMLSequenceNode: \/[NonEmptyList[UMLError.UException],UMLSequenceNode[MagicDrawUML]] =
    \/-(f.createSequenceNodeInstance())

  override def createUMLSignal: \/[NonEmptyList[UMLError.UException],UMLSignal[MagicDrawUML]] =
    \/-(f.createSignalInstance())

  override def createUMLSignalEvent: \/[NonEmptyList[UMLError.UException],UMLSignalEvent[MagicDrawUML]] =
    \/-(f.createSignalEventInstance())

  override def createUMLSlot: \/[NonEmptyList[UMLError.UException],UMLSlot[MagicDrawUML]] =
    \/-(f.createSlotInstance())

  override def createUMLStartClassifierBehaviorAction: \/[NonEmptyList[UMLError.UException],UMLStartClassifierBehaviorAction[MagicDrawUML]] =
    \/-(f.createStartClassifierBehaviorActionInstance())

  override def createUMLStartObjectBehaviorAction: \/[NonEmptyList[UMLError.UException],UMLStartObjectBehaviorAction[MagicDrawUML]] =
    \/-(f.createStartObjectBehaviorActionInstance())

  override def createUMLState: \/[NonEmptyList[UMLError.UException],UMLState[MagicDrawUML]] =
    \/-(f.createStateInstance())

  override def createUMLStateInvariant: \/[NonEmptyList[UMLError.UException],UMLStateInvariant[MagicDrawUML]] =
    \/-(f.createStateInvariantInstance())

  override def createUMLStateMachine: \/[NonEmptyList[UMLError.UException],UMLStateMachine[MagicDrawUML]] =
    \/-(f.createStateMachineInstance())

  override def createUMLStereotype: \/[NonEmptyList[UMLError.UException],UMLStereotype[MagicDrawUML]] =
    \/-(f.createStereotypeInstance())

  override def createUMLStringExpression: \/[NonEmptyList[UMLError.UException],UMLStringExpression[MagicDrawUML]] =
    \/-(f.createStringExpressionInstance())

  override def createUMLStructuredActivityNode: \/[NonEmptyList[UMLError.UException],UMLStructuredActivityNode[MagicDrawUML]] =
    \/-(f.createStructuredActivityNodeInstance())

  override def createUMLSubstitution: \/[NonEmptyList[UMLError.UException],UMLSubstitution[MagicDrawUML]] =
    \/-(f.createSubstitutionInstance())

  override def createUMLTemplateBinding: \/[NonEmptyList[UMLError.UException],UMLTemplateBinding[MagicDrawUML]] =
    \/-(f.createTemplateBindingInstance())

  override def createUMLTemplateParameter: \/[NonEmptyList[UMLError.UException],UMLTemplateParameter[MagicDrawUML]] =
    \/-(f.createTemplateParameterInstance())

  override def createUMLTemplateParameterSubstitution: \/[NonEmptyList[UMLError.UException],UMLTemplateParameterSubstitution[MagicDrawUML]] =
    \/-(f.createTemplateParameterSubstitutionInstance())

  override def createUMLTemplateSignature: \/[NonEmptyList[UMLError.UException],UMLTemplateSignature[MagicDrawUML]] =
    \/-(f.createTemplateSignatureInstance())

  override def createUMLTestIdentityAction: \/[NonEmptyList[UMLError.UException],UMLTestIdentityAction[MagicDrawUML]] =
    \/-(f.createTestIdentityActionInstance())

  override def createUMLTimeConstraint: \/[NonEmptyList[UMLError.UException],UMLTimeConstraint[MagicDrawUML]] =
    \/-(f.createTimeConstraintInstance())

  override def createUMLTimeEvent: \/[NonEmptyList[UMLError.UException],UMLTimeEvent[MagicDrawUML]] =
    \/-(f.createTimeEventInstance())

  override def createUMLTimeExpression: \/[NonEmptyList[UMLError.UException],UMLTimeExpression[MagicDrawUML]] =
    \/-(f.createTimeExpressionInstance())

  override def createUMLTimeInterval: \/[NonEmptyList[UMLError.UException],UMLTimeInterval[MagicDrawUML]] =
    \/-(f.createTimeIntervalInstance())

  override def createUMLTimeObservation: \/[NonEmptyList[UMLError.UException],UMLTimeObservation[MagicDrawUML]] =
    \/-(f.createTimeObservationInstance())

  override def createUMLTransition: \/[NonEmptyList[UMLError.UException],UMLTransition[MagicDrawUML]] =
    \/-(f.createTransitionInstance())

  override def createUMLTrigger: \/[NonEmptyList[UMLError.UException],UMLTrigger[MagicDrawUML]] =
    \/-(f.createTriggerInstance())

  override def createUMLUnmarshallAction: \/[NonEmptyList[UMLError.UException],UMLUnmarshallAction[MagicDrawUML]] =
    \/-(f.createUnmarshallActionInstance())

  override def createUMLUsage: \/[NonEmptyList[UMLError.UException],UMLUsage[MagicDrawUML]] =
    \/-(f.createUsageInstance())

  override def createUMLUseCase: \/[NonEmptyList[UMLError.UException],UMLUseCase[MagicDrawUML]] =
    \/-(f.createUseCaseInstance())

  override def createUMLValuePin: \/[NonEmptyList[UMLError.UException],UMLValuePin[MagicDrawUML]] =
    \/-(f.createValuePinInstance())

  override def createUMLValueSpecificationAction: \/[NonEmptyList[UMLError.UException],UMLValueSpecificationAction[MagicDrawUML]] =
    \/-(f.createValueSpecificationActionInstance())

  override def createUMLVariable: \/[NonEmptyList[UMLError.UException],UMLVariable[MagicDrawUML]] =
    \/-(f.createVariableInstance())

}