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

  override def createUMLAbstraction: NonEmptyList[java.lang.Throwable] \/ UMLAbstraction[MagicDrawUML] =
    \/-(f.createAbstractionInstance())

  override def createUMLAcceptCallAction: NonEmptyList[java.lang.Throwable] \/ UMLAcceptCallAction[MagicDrawUML] =
    \/-(f.createAcceptCallActionInstance())

  override def createUMLAcceptEventAction: NonEmptyList[java.lang.Throwable] \/ UMLAcceptEventAction[MagicDrawUML] =
    \/-(f.createAcceptEventActionInstance())

  override def createUMLActionExecutionSpecification: NonEmptyList[java.lang.Throwable] \/ UMLActionExecutionSpecification[Uml] =
    \/-(f.createActionExecutionSpecificationInstance())

  override def createUMLActionInputPin: NonEmptyList[java.lang.Throwable] \/ UMLActionInputPin[MagicDrawUML] =
    \/-(f.createActionInputPinInstance())

  override def createUMLActivity: NonEmptyList[java.lang.Throwable] \/ UMLActivity[MagicDrawUML] =
    \/-(f.createActivityInstance())

  override def createUMLActivityFinalNode: NonEmptyList[java.lang.Throwable] \/ UMLActivityFinalNode[MagicDrawUML] =
    \/-(f.createActivityFinalNodeInstance())

  override def createUMLActivityParameterNode: NonEmptyList[java.lang.Throwable] \/ UMLActivityParameterNode[MagicDrawUML] =
    \/-(f.createActivityParameterNodeInstance())

  override def createUMLActivityPartition: NonEmptyList[java.lang.Throwable] \/ UMLActivityPartition[MagicDrawUML] =
    \/-(f.createActivityPartitionInstance())

  override def createUMLActor: NonEmptyList[java.lang.Throwable] \/ UMLActor[MagicDrawUML] =
    \/-(f.createActorInstance())

  override def createUMLAddStructuralFeatureValueAction: NonEmptyList[java.lang.Throwable] \/ UMLAddStructuralFeatureValueAction[MagicDrawUML] =
    \/-(f.createAddStructuralFeatureValueActionInstance())

  override def createUMLAddVariableValueAction: NonEmptyList[java.lang.Throwable] \/ UMLAddVariableValueAction[MagicDrawUML] =
    \/-(f.createAddVariableValueActionInstance())

  override def createUMLAnyReceiveEvent: NonEmptyList[java.lang.Throwable] \/ UMLAnyReceiveEvent[MagicDrawUML] =
    \/-(f.createAnyReceiveEventInstance())

  override def createUMLArtifact: NonEmptyList[java.lang.Throwable] \/ UMLArtifact[MagicDrawUML] =
    \/-(f.createArtifactInstance())

  override def createUMLAssociation: NonEmptyList[java.lang.Throwable] \/ UMLAssociation[MagicDrawUML] =
    \/-(f.createAssociationInstance())

  override def createUMLAssociationClass: NonEmptyList[java.lang.Throwable] \/ UMLAssociationClass[MagicDrawUML] =
    \/-(f.createAssociationClassInstance())

  override def createUMLBehaviorExecutionSpecification: NonEmptyList[java.lang.Throwable] \/ UMLBehaviorExecutionSpecification[MagicDrawUML] =
    \/-(f.createBehaviorExecutionSpecificationInstance())

  override def createUMLBroadcastSignalAction: NonEmptyList[java.lang.Throwable] \/ UMLBroadcastSignalAction[MagicDrawUML] =
    \/-(f.createBroadcastSignalActionInstance())

  override def createUMLCallBehaviorAction: NonEmptyList[java.lang.Throwable] \/ UMLCallBehaviorAction[MagicDrawUML] =
    \/-(f.createCallBehaviorActionInstance())

  override def createUMLCallEvent: NonEmptyList[java.lang.Throwable] \/ UMLCallEvent[MagicDrawUML] =
    \/-(f.createCallEventInstance())

  override def createUMLCallOperationAction: NonEmptyList[java.lang.Throwable] \/ UMLCallOperationAction[MagicDrawUML] =
    \/-(f.createCallOperationActionInstance())

  override def createUMLCentralBufferNode: NonEmptyList[java.lang.Throwable] \/ UMLCentralBufferNode[MagicDrawUML] =
    \/-(f.createCentralBufferNodeInstance())

  override def createUMLChangeEvent: NonEmptyList[java.lang.Throwable] \/ UMLChangeEvent[MagicDrawUML] =
    \/-(f.createChangeEventInstance())

  override def createUMLClass: NonEmptyList[java.lang.Throwable] \/ UMLClass[MagicDrawUML] =
    \/-(f.createClassInstance())

  override def createUMLClassifierTemplateParameter: NonEmptyList[java.lang.Throwable] \/ UMLClassifierTemplateParameter[MagicDrawUML] =
    \/-(f.createClassifierTemplateParameterInstance())

  override def createUMLClause: NonEmptyList[java.lang.Throwable] \/ UMLClause[MagicDrawUML] =
    \/-(f.createClauseInstance())

  override def createUMLClearAssociationAction: NonEmptyList[java.lang.Throwable] \/ UMLClearAssociationAction[MagicDrawUML] =
    \/-(f.createClearAssociationActionInstance())

  override def createUMLClearStructuralFeatureAction: NonEmptyList[java.lang.Throwable] \/ UMLClearStructuralFeatureAction[MagicDrawUML] =
    \/-(f.createClearStructuralFeatureActionInstance())

  override def createUMLClearVariableAction: NonEmptyList[java.lang.Throwable] \/ UMLClearVariableAction[MagicDrawUML] =
    \/-(f.createClearVariableActionInstance())

  override def createUMLCollaboration: NonEmptyList[java.lang.Throwable] \/ UMLCollaboration[MagicDrawUML] =
    \/-(f.createCollaborationInstance())

  override def createUMLCollaborationUse: NonEmptyList[java.lang.Throwable] \/ UMLCollaborationUse[MagicDrawUML] =
    \/-(f.createCollaborationUseInstance())

  override def createUMLCombinedFragment: NonEmptyList[java.lang.Throwable] \/ UMLCombinedFragment[MagicDrawUML] =
    \/-(f.createCombinedFragmentInstance())

  override def createUMLComment: NonEmptyList[java.lang.Throwable] \/ UMLComment[MagicDrawUML] =
    \/-(f.createCommentInstance())

  override def createUMLCommunicationPath: NonEmptyList[java.lang.Throwable] \/ UMLCommunicationPath[MagicDrawUML] =
    \/-(f.createCommunicationPathInstance())

  override def createUMLComponent: NonEmptyList[java.lang.Throwable] \/ UMLComponent[MagicDrawUML] =
    \/-(f.createComponentInstance())

  override def createUMLComponentRealization: NonEmptyList[java.lang.Throwable] \/ UMLComponentRealization[MagicDrawUML] =
    \/-(f.createComponentRealizationInstance())

  override def createUMLConditionalNode: NonEmptyList[java.lang.Throwable] \/ UMLConditionalNode[MagicDrawUML] =
    \/-(f.createConditionalNodeInstance())

  override def createUMLConnectableElementTemplateParameter: NonEmptyList[java.lang.Throwable] \/ UMLConnectableElementTemplateParameter[MagicDrawUML] =
    \/-(f.createConnectableElementTemplateParameterInstance())

  override def createUMLConnectionPointReference: NonEmptyList[java.lang.Throwable] \/ UMLConnectionPointReference[MagicDrawUML] =
    \/-(f.createConnectionPointReferenceInstance())

  override def createUMLConnector: NonEmptyList[java.lang.Throwable] \/ UMLConnector[MagicDrawUML] =
    \/-(f.createConnectorInstance())

  override def createUMLConnectorEnd: NonEmptyList[java.lang.Throwable] \/ UMLConnectorEnd[MagicDrawUML] =
    \/-(f.createConnectorEndInstance())

  override def createUMLConsiderIgnoreFragment: NonEmptyList[java.lang.Throwable] \/ UMLConsiderIgnoreFragment[MagicDrawUML] =
    \/-(f.createConsiderIgnoreFragmentInstance())

  override def createUMLConstraint: NonEmptyList[java.lang.Throwable] \/ UMLConstraint[MagicDrawUML] =
    \/-(f.createConstraintInstance())

  override def createUMLContinuation: NonEmptyList[java.lang.Throwable] \/ UMLContinuation[MagicDrawUML] =
    \/-(f.createContinuationInstance())

  override def createUMLControlFlow: NonEmptyList[java.lang.Throwable] \/ UMLControlFlow[MagicDrawUML] =
    \/-(f.createControlFlowInstance())

  override def createUMLCreateLinkAction: NonEmptyList[java.lang.Throwable] \/ UMLCreateLinkAction[MagicDrawUML] =
    \/-(f.createCreateLinkActionInstance())

  override def createUMLCreateLinkObjectAction: NonEmptyList[java.lang.Throwable] \/ UMLCreateLinkObjectAction[MagicDrawUML] =
    \/-(f.createCreateLinkObjectActionInstance())

  override def createUMLCreateObjectAction: NonEmptyList[java.lang.Throwable] \/ UMLCreateObjectAction[MagicDrawUML] =
    \/-(f.createCreateObjectActionInstance())

  override def createUMLDataStoreNode: NonEmptyList[java.lang.Throwable] \/ UMLDataStoreNode[MagicDrawUML] =
    \/-(f.createDataStoreNodeInstance())

  override def createUMLDataType: NonEmptyList[java.lang.Throwable] \/ UMLDataType[MagicDrawUML] =
    \/-(f.createDataTypeInstance())

  override def createUMLDecisionNode: NonEmptyList[java.lang.Throwable] \/ UMLDecisionNode[MagicDrawUML] =
    \/-(f.createDecisionNodeInstance())

  override def createUMLDependency: NonEmptyList[java.lang.Throwable] \/ UMLDependency[MagicDrawUML] =
    \/-(f.createDependencyInstance())

  override def createUMLDeployment: NonEmptyList[java.lang.Throwable] \/ UMLDeployment[MagicDrawUML] =
    \/-(f.createDeploymentInstance())

  override def createUMLDeploymentSpecification: NonEmptyList[java.lang.Throwable] \/ UMLDeploymentSpecification[MagicDrawUML] =
    \/-(f.createDeploymentSpecificationInstance())

  override def createUMLDestroyLinkAction: NonEmptyList[java.lang.Throwable] \/ UMLDestroyLinkAction[MagicDrawUML] =
    \/-(f.createDestroyLinkActionInstance())

  override def createUMLDestroyObjectAction: NonEmptyList[java.lang.Throwable] \/ UMLDestroyObjectAction[MagicDrawUML] =
    \/-(f.createDestroyObjectActionInstance())

  override def createUMLDestructionOccurrenceSpecification: NonEmptyList[java.lang.Throwable] \/ UMLDestructionOccurrenceSpecification[MagicDrawUML] =
    \/-(f.createDestructionOccurrenceSpecificationInstance())

  override def createUMLDevice: NonEmptyList[java.lang.Throwable] \/ UMLDevice[MagicDrawUML] =
    \/-(f.createDeviceInstance())

  override def createUMLDuration: NonEmptyList[java.lang.Throwable] \/ UMLDuration[MagicDrawUML] =
    \/-(f.createDurationInstance())

  override def createUMLDurationConstraint: NonEmptyList[java.lang.Throwable] \/ UMLDurationConstraint[MagicDrawUML] =
    \/-(f.createDurationConstraintInstance())

  override def createUMLDurationInterval: NonEmptyList[java.lang.Throwable] \/ UMLDurationInterval[MagicDrawUML] =
    \/-(f.createDurationIntervalInstance())

  override def createUMLDurationObservation: NonEmptyList[java.lang.Throwable] \/ UMLDurationObservation[MagicDrawUML] =
    \/-(f.createDurationObservationInstance())

  override def createUMLElementImport: NonEmptyList[java.lang.Throwable] \/ UMLElementImport[MagicDrawUML] =
    \/-(f.createElementImportInstance())

  override def createUMLEnumeration: NonEmptyList[java.lang.Throwable] \/ UMLEnumeration[MagicDrawUML] =
    \/-(f.createEnumerationInstance())

  override def createUMLEnumerationLiteral: NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    \/-(f.createEnumerationLiteralInstance())

  override def createUMLExceptionHandler: NonEmptyList[java.lang.Throwable] \/ UMLExceptionHandler[MagicDrawUML] =
    \/-(f.createExceptionHandlerInstance())

  override def createUMLExecutionEnvironment: NonEmptyList[java.lang.Throwable] \/ UMLExecutionEnvironment[MagicDrawUML] =
    \/-(f.createExecutionEnvironmentInstance())

  override def createUMLExecutionOccurrenceSpecification: NonEmptyList[java.lang.Throwable] \/ UMLExecutionOccurrenceSpecification[MagicDrawUML] =
    \/-(f.createExecutionOccurrenceSpecificationInstance())

  override def createUMLExpansionNode: NonEmptyList[java.lang.Throwable] \/ UMLExpansionNode[MagicDrawUML] =
    \/-(f.createExpansionNodeInstance())

  override def createUMLExpansionRegion: NonEmptyList[java.lang.Throwable] \/ UMLExpansionRegion[MagicDrawUML] =
    \/-(f.createExpansionRegionInstance())

  override def createUMLExpression: NonEmptyList[java.lang.Throwable] \/ UMLExpression[MagicDrawUML] =
    \/-(f.createExpressionInstance())

  override def createUMLExtend: NonEmptyList[java.lang.Throwable] \/ UMLExtend[MagicDrawUML] =
    \/-(f.createExtendInstance())

  override def createUMLExtension: NonEmptyList[java.lang.Throwable] \/ UMLExtension[MagicDrawUML] =
    \/-(f.createExtensionInstance())

  override def createUMLExtensionEnd: NonEmptyList[java.lang.Throwable] \/ UMLExtensionEnd[MagicDrawUML] =
    \/-(f.createExtensionEndInstance())

  override def createUMLExtensionPoint: NonEmptyList[java.lang.Throwable] \/ UMLExtensionPoint[MagicDrawUML] =
    \/-(f.createExtensionPointInstance())

  override def createUMLFinalState: NonEmptyList[java.lang.Throwable] \/ UMLFinalState[MagicDrawUML] =
    \/-(f.createFinalStateInstance())

  override def createUMLFlowFinalNode: NonEmptyList[java.lang.Throwable] \/ UMLFlowFinalNode[MagicDrawUML] =
    \/-(f.createFlowFinalNodeInstance())

  override def createUMLForkNode: NonEmptyList[java.lang.Throwable] \/ UMLForkNode[MagicDrawUML] =
    \/-(f.createForkNodeInstance())

  override def createUMLFunctionBehavior: NonEmptyList[java.lang.Throwable] \/ UMLFunctionBehavior[MagicDrawUML] =
    \/-(f.createFunctionBehaviorInstance())

  override def createUMLGate: NonEmptyList[java.lang.Throwable] \/ UMLGate[MagicDrawUML] =
    \/-(f.createGateInstance())

  override def createUMLGeneralOrdering: NonEmptyList[java.lang.Throwable] \/ UMLGeneralOrdering[MagicDrawUML] =
    \/-(f.createGeneralOrderingInstance())

  override def createUMLGeneralization: NonEmptyList[java.lang.Throwable] \/ UMLGeneralization[MagicDrawUML] =
    \/-(f.createGeneralizationInstance())

  override def createUMLGeneralizationSet: NonEmptyList[java.lang.Throwable] \/ UMLGeneralizationSet[MagicDrawUML] =
    \/-(f.createGeneralizationSetInstance())

  override def createUMLImage: NonEmptyList[java.lang.Throwable] \/ UMLImage[MagicDrawUML] =
    \/-(f.createImageInstance())

  override def createUMLInclude: NonEmptyList[java.lang.Throwable] \/ UMLInclude[MagicDrawUML] =
    \/-(f.createIncludeInstance())

  override def createUMLInformationFlow: NonEmptyList[java.lang.Throwable] \/ UMLInformationFlow[MagicDrawUML] =
    \/-(f.createInformationFlowInstance())

  override def createUMLInformationItem: NonEmptyList[java.lang.Throwable] \/ UMLInformationItem[MagicDrawUML] =
    \/-(f.createInformationItemInstance())

  override def createUMLInitialNode: NonEmptyList[java.lang.Throwable] \/ UMLInitialNode[MagicDrawUML] =
    \/-(f.createInitialNodeInstance())

  override def createUMLInputPin: NonEmptyList[java.lang.Throwable] \/ UMLInputPin[MagicDrawUML] =
    \/-(f.createInputPinInstance())

  override def createUMLInstanceSpecification: NonEmptyList[java.lang.Throwable] \/ UMLInstanceSpecification[MagicDrawUML] =
    \/-(f.createInstanceSpecificationInstance())

  override def createUMLInstanceValue: NonEmptyList[java.lang.Throwable] \/ UMLInstanceValue[MagicDrawUML] =
    \/-(f.createInstanceValueInstance())

  override def createUMLInteraction: NonEmptyList[java.lang.Throwable] \/ UMLInteraction[MagicDrawUML] =
    \/-(f.createInteractionInstance())

  override def createUMLInteractionConstraint: NonEmptyList[java.lang.Throwable] \/ UMLInteractionConstraint[MagicDrawUML] =
    \/-(f.createInteractionConstraintInstance())

  override def createUMLInteractionOperand: NonEmptyList[java.lang.Throwable] \/ UMLInteractionOperand[MagicDrawUML] =
    \/-(f.createInteractionOperandInstance())

  override def createUMLInteractionUse: NonEmptyList[java.lang.Throwable] \/ UMLInteractionUse[MagicDrawUML] =
    \/-(f.createInteractionUseInstance())

  override def createUMLInterface: NonEmptyList[java.lang.Throwable] \/ UMLInterface[MagicDrawUML] =
    \/-(f.createInterfaceInstance())

  override def createUMLInterfaceRealization: NonEmptyList[java.lang.Throwable] \/ UMLInterfaceRealization[MagicDrawUML] =
    \/-(f.createInterfaceRealizationInstance())

  override def createUMLInterruptibleActivityRegion: NonEmptyList[java.lang.Throwable] \/ UMLInterruptibleActivityRegion[MagicDrawUML] =
    \/-(f.createInterruptibleActivityRegionInstance())

  override def createUMLInterval: NonEmptyList[java.lang.Throwable] \/ UMLInterval[MagicDrawUML] =
    \/-(f.createIntervalInstance())

  override def createUMLIntervalConstraint: NonEmptyList[java.lang.Throwable] \/ UMLIntervalConstraint[MagicDrawUML] =
    \/-(f.createIntervalConstraintInstance())

  override def createUMLJoinNode: NonEmptyList[java.lang.Throwable] \/ UMLJoinNode[MagicDrawUML] =
    \/-(f.createJoinNodeInstance())

  override def createUMLLifeline: NonEmptyList[java.lang.Throwable] \/ UMLLifeline[MagicDrawUML] =
    \/-(f.createLifelineInstance())

  override def createUMLLinkEndCreationData: NonEmptyList[java.lang.Throwable] \/ UMLLinkEndCreationData[MagicDrawUML] =
    \/-(f.createLinkEndCreationDataInstance())

  override def createUMLLinkEndData: NonEmptyList[java.lang.Throwable] \/ UMLLinkEndData[MagicDrawUML] =
    \/-(f.createLinkEndDataInstance())

  override def createUMLLinkEndDestructionData: NonEmptyList[java.lang.Throwable] \/ UMLLinkEndDestructionData[MagicDrawUML] =
    \/-(f.createLinkEndDestructionDataInstance())

  override def createUMLLiteralBoolean: NonEmptyList[java.lang.Throwable] \/ UMLLiteralBoolean[MagicDrawUML] =
    \/-(f.createLiteralBooleanInstance())

  override def createUMLLiteralInteger: NonEmptyList[java.lang.Throwable] \/ UMLLiteralInteger[MagicDrawUML] =
    \/-(f.createLiteralIntegerInstance())

  override def createUMLLiteralNull: NonEmptyList[java.lang.Throwable] \/ UMLLiteralNull[MagicDrawUML] =
    \/-(f.createLiteralNullInstance())

  override def createUMLLiteralReal: NonEmptyList[java.lang.Throwable] \/ UMLLiteralReal[MagicDrawUML] =
    \/-(f.createLiteralRealInstance())

  override def createUMLLiteralString: NonEmptyList[java.lang.Throwable] \/ UMLLiteralString[MagicDrawUML] =
    \/-(f.createLiteralStringInstance())

  override def createUMLLiteralUnlimitedNatural: NonEmptyList[java.lang.Throwable] \/ UMLLiteralUnlimitedNatural[MagicDrawUML] =
    \/-(f.createLiteralUnlimitedNaturalInstance())

  override def createUMLLoopNode: NonEmptyList[java.lang.Throwable] \/ UMLLoopNode[MagicDrawUML] =
    \/-(f.createLoopNodeInstance())

  override def createUMLManifestation: NonEmptyList[java.lang.Throwable] \/ UMLManifestation[MagicDrawUML] =
    \/-(f.createManifestationInstance())

  override def createUMLMergeNode: NonEmptyList[java.lang.Throwable] \/ UMLMergeNode[MagicDrawUML] =
    \/-(f.createMergeNodeInstance())

  override def createUMLMessage: NonEmptyList[java.lang.Throwable] \/ UMLMessage[MagicDrawUML] =
    \/-(f.createMessageInstance())

  override def createUMLMessageOccurrenceSpecification: NonEmptyList[java.lang.Throwable] \/ UMLMessageOccurrenceSpecification[MagicDrawUML] =
    \/-(f.createMessageOccurrenceSpecificationInstance())

  override def createUMLModel: NonEmptyList[java.lang.Throwable] \/ UMLModel[MagicDrawUML] =
    \/-(f.createModelInstance())

  override def createUMLNode: NonEmptyList[java.lang.Throwable] \/ UMLNode[MagicDrawUML] =
    \/-(f.createNodeInstance())

  override def createUMLObjectFlow: NonEmptyList[java.lang.Throwable] \/ UMLObjectFlow[MagicDrawUML] =
    \/-(f.createObjectFlowInstance())

  override def createUMLOccurrenceSpecification: NonEmptyList[java.lang.Throwable] \/ UMLOccurrenceSpecification[MagicDrawUML] =
    \/-(f.createOccurrenceSpecificationInstance())

  override def createUMLOpaqueAction: NonEmptyList[java.lang.Throwable] \/ UMLOpaqueAction[MagicDrawUML] =
    \/-(f.createOpaqueActionInstance())

  override def createUMLOpaqueBehavior: NonEmptyList[java.lang.Throwable] \/ UMLOpaqueBehavior[MagicDrawUML] =
    \/-(f.createOpaqueBehaviorInstance())

  override def createUMLOpaqueExpression: NonEmptyList[java.lang.Throwable] \/ UMLOpaqueExpression[MagicDrawUML] =
    \/-(f.createOpaqueExpressionInstance())

  override def createUMLOperation: NonEmptyList[java.lang.Throwable] \/ UMLOperation[MagicDrawUML] =
    \/-(f.createOperationInstance())

  override def createUMLOperationTemplateParameter: NonEmptyList[java.lang.Throwable] \/ UMLOperationTemplateParameter[MagicDrawUML] =
    \/-(f.createOperationTemplateParameterInstance())

  override def createUMLOutputPin: NonEmptyList[java.lang.Throwable] \/ UMLOutputPin[MagicDrawUML] =
    \/-(f.createOutputPinInstance())

  override def createUMLPackage: NonEmptyList[java.lang.Throwable] \/ UMLPackage[MagicDrawUML] =
    \/-(f.createPackageInstance())

  override def createUMLPackageImport: NonEmptyList[java.lang.Throwable] \/ UMLPackageImport[MagicDrawUML] =
    \/-(f.createPackageImportInstance())

  override def createUMLPackageMerge: NonEmptyList[java.lang.Throwable] \/ UMLPackageMerge[MagicDrawUML] =
    \/-(f.createPackageMergeInstance())

  override def createUMLParameter: NonEmptyList[java.lang.Throwable] \/ UMLParameter[MagicDrawUML] =
    \/-(f.createParameterInstance())

  override def createUMLParameterSet: NonEmptyList[java.lang.Throwable] \/ UMLParameterSet[MagicDrawUML] =
    \/-(f.createParameterSetInstance())

  override def createUMLPartDecomposition: NonEmptyList[java.lang.Throwable] \/ UMLPartDecomposition[MagicDrawUML] =
    \/-(f.createPartDecompositionInstance())

  override def createUMLPort: NonEmptyList[java.lang.Throwable] \/ UMLPort[MagicDrawUML] =
    \/-(f.createPortInstance())

  override def createUMLPrimitiveType: NonEmptyList[java.lang.Throwable] \/ UMLPrimitiveType[MagicDrawUML] =
    \/-(f.createPrimitiveTypeInstance())

  override def createUMLProfile: NonEmptyList[java.lang.Throwable] \/ UMLProfile[MagicDrawUML] =
    \/-(f.createProfileInstance())

  override def createUMLProfileApplication: NonEmptyList[java.lang.Throwable] \/ UMLProfileApplication[MagicDrawUML] =
    \/-(f.createProfileApplicationInstance())

  override def createUMLProperty: NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    \/-(f.createPropertyInstance())

  override def createUMLProtocolConformance: NonEmptyList[java.lang.Throwable] \/ UMLProtocolConformance[MagicDrawUML] =
    \/-(f.createProtocolConformanceInstance())

  override def createUMLProtocolStateMachine: NonEmptyList[java.lang.Throwable] \/ UMLProtocolStateMachine[MagicDrawUML] =
    \/-(f.createProtocolStateMachineInstance())

  override def createUMLProtocolTransition: NonEmptyList[java.lang.Throwable] \/ UMLProtocolTransition[MagicDrawUML] =
    \/-(f.createProtocolTransitionInstance())

  override def createUMLPseudostate: NonEmptyList[java.lang.Throwable] \/ UMLPseudostate[MagicDrawUML] =
    \/-(f.createPseudostateInstance())

  override def createUMLQualifierValue: NonEmptyList[java.lang.Throwable] \/ UMLQualifierValue[MagicDrawUML] =
    \/-(f.createQualifierValueInstance())

  override def createUMLRaiseExceptionAction: NonEmptyList[java.lang.Throwable] \/ UMLRaiseExceptionAction[MagicDrawUML] =
    \/-(f.createRaiseExceptionActionInstance())

  override def createUMLReadExtentAction: NonEmptyList[java.lang.Throwable] \/ UMLReadExtentAction[MagicDrawUML] =
    \/-(f.createReadExtentActionInstance())

  override def createUMLReadIsClassifiedObjectAction: NonEmptyList[java.lang.Throwable] \/ UMLReadIsClassifiedObjectAction[MagicDrawUML] =
    \/-(f.createReadIsClassifiedObjectActionInstance())

  override def createUMLReadLinkAction: NonEmptyList[java.lang.Throwable] \/ UMLReadLinkAction[MagicDrawUML] =
    \/-(f.createReadLinkActionInstance())

  override def createUMLReadLinkObjectEndAction: NonEmptyList[java.lang.Throwable] \/ UMLReadLinkObjectEndAction[MagicDrawUML] =
    \/-(f.createReadLinkObjectEndActionInstance())

  override def createUMLReadLinkObjectEndQualifierAction: NonEmptyList[java.lang.Throwable] \/ UMLReadLinkObjectEndQualifierAction[MagicDrawUML] =
    \/-(f.createReadLinkObjectEndQualifierActionInstance())

  override def createUMLReadSelfAction: NonEmptyList[java.lang.Throwable] \/ UMLReadSelfAction[MagicDrawUML] =
    \/-(f.createReadSelfActionInstance())

  override def createUMLReadStructuralFeatureAction: NonEmptyList[java.lang.Throwable] \/ UMLReadStructuralFeatureAction[MagicDrawUML] =
    \/-(f.createReadStructuralFeatureActionInstance())

  override def createUMLReadVariableAction: NonEmptyList[java.lang.Throwable] \/ UMLReadVariableAction[MagicDrawUML] =
    \/-(f.createReadVariableActionInstance())

  override def createUMLRealization: NonEmptyList[java.lang.Throwable] \/ UMLRealization[MagicDrawUML] =
    \/-(f.createRealizationInstance())

  override def createUMLReception: NonEmptyList[java.lang.Throwable] \/ UMLReception[MagicDrawUML] =
    \/-(f.createReceptionInstance())

  override def createUMLReclassifyObjectAction: NonEmptyList[java.lang.Throwable] \/ UMLReclassifyObjectAction[MagicDrawUML] =
    \/-(f.createReclassifyObjectActionInstance())

  override def createUMLRedefinableTemplateSignature: NonEmptyList[java.lang.Throwable] \/ UMLRedefinableTemplateSignature[MagicDrawUML] =
    \/-(f.createRedefinableTemplateSignatureInstance())

  override def createUMLReduceAction: NonEmptyList[java.lang.Throwable] \/ UMLReduceAction[MagicDrawUML] =
    \/-(f.createReduceActionInstance())

  override def createUMLRegion: NonEmptyList[java.lang.Throwable] \/ UMLRegion[MagicDrawUML] =
    \/-(f.createRegionInstance())

  override def createUMLRemoveStructuralFeatureValueAction: NonEmptyList[java.lang.Throwable] \/ UMLRemoveStructuralFeatureValueAction[MagicDrawUML] =
    \/-(f.createRemoveStructuralFeatureValueActionInstance())

  override def createUMLRemoveVariableValueAction: NonEmptyList[java.lang.Throwable] \/ UMLRemoveVariableValueAction[MagicDrawUML] =
    \/-(f.createRemoveVariableValueActionInstance())

  override def createUMLReplyAction: NonEmptyList[java.lang.Throwable] \/ UMLReplyAction[MagicDrawUML] =
    \/-(f.createReplyActionInstance())

  override def createUMLSendObjectAction: NonEmptyList[java.lang.Throwable] \/ UMLSendObjectAction[MagicDrawUML] =
    \/-(f.createSendObjectActionInstance())

  override def createUMLSendSignalAction: NonEmptyList[java.lang.Throwable] \/ UMLSendSignalAction[MagicDrawUML] =
    \/-(f.createSendSignalActionInstance())

  override def createUMLSequenceNode: NonEmptyList[java.lang.Throwable] \/ UMLSequenceNode[MagicDrawUML] =
    \/-(f.createSequenceNodeInstance())

  override def createUMLSignal: NonEmptyList[java.lang.Throwable] \/ UMLSignal[MagicDrawUML] =
    \/-(f.createSignalInstance())

  override def createUMLSignalEvent: NonEmptyList[java.lang.Throwable] \/ UMLSignalEvent[MagicDrawUML] =
    \/-(f.createSignalEventInstance())

  override def createUMLSlot: NonEmptyList[java.lang.Throwable] \/ UMLSlot[MagicDrawUML] =
    \/-(f.createSlotInstance())

  override def createUMLStartClassifierBehaviorAction: NonEmptyList[java.lang.Throwable] \/ UMLStartClassifierBehaviorAction[MagicDrawUML] =
    \/-(f.createStartClassifierBehaviorActionInstance())

  override def createUMLStartObjectBehaviorAction: NonEmptyList[java.lang.Throwable] \/ UMLStartObjectBehaviorAction[MagicDrawUML] =
    \/-(f.createStartObjectBehaviorActionInstance())

  override def createUMLState: NonEmptyList[java.lang.Throwable] \/ UMLState[MagicDrawUML] =
    \/-(f.createStateInstance())

  override def createUMLStateInvariant: NonEmptyList[java.lang.Throwable] \/ UMLStateInvariant[MagicDrawUML] =
    \/-(f.createStateInvariantInstance())

  override def createUMLStateMachine: NonEmptyList[java.lang.Throwable] \/ UMLStateMachine[MagicDrawUML] =
    \/-(f.createStateMachineInstance())

  override def createUMLStereotype: NonEmptyList[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML] =
    \/-(f.createStereotypeInstance())

  override def createUMLStringExpression: NonEmptyList[java.lang.Throwable] \/ UMLStringExpression[MagicDrawUML] =
    \/-(f.createStringExpressionInstance())

  override def createUMLStructuredActivityNode: NonEmptyList[java.lang.Throwable] \/ UMLStructuredActivityNode[MagicDrawUML] =
    \/-(f.createStructuredActivityNodeInstance())

  override def createUMLSubstitution: NonEmptyList[java.lang.Throwable] \/ UMLSubstitution[MagicDrawUML] =
    \/-(f.createSubstitutionInstance())

  override def createUMLTemplateBinding: NonEmptyList[java.lang.Throwable] \/ UMLTemplateBinding[MagicDrawUML] =
    \/-(f.createTemplateBindingInstance())

  override def createUMLTemplateParameter: NonEmptyList[java.lang.Throwable] \/ UMLTemplateParameter[MagicDrawUML] =
    \/-(f.createTemplateParameterInstance())

  override def createUMLTemplateParameterSubstitution: NonEmptyList[java.lang.Throwable] \/ UMLTemplateParameterSubstitution[MagicDrawUML] =
    \/-(f.createTemplateParameterSubstitutionInstance())

  override def createUMLTemplateSignature: NonEmptyList[java.lang.Throwable] \/ UMLTemplateSignature[MagicDrawUML] =
    \/-(f.createTemplateSignatureInstance())

  override def createUMLTestIdentityAction: NonEmptyList[java.lang.Throwable] \/ UMLTestIdentityAction[MagicDrawUML] =
    \/-(f.createTestIdentityActionInstance())

  override def createUMLTimeConstraint: NonEmptyList[java.lang.Throwable] \/ UMLTimeConstraint[MagicDrawUML] =
    \/-(f.createTimeConstraintInstance())

  override def createUMLTimeEvent: NonEmptyList[java.lang.Throwable] \/ UMLTimeEvent[MagicDrawUML] =
    \/-(f.createTimeEventInstance())

  override def createUMLTimeExpression: NonEmptyList[java.lang.Throwable] \/ UMLTimeExpression[MagicDrawUML] =
    \/-(f.createTimeExpressionInstance())

  override def createUMLTimeInterval: NonEmptyList[java.lang.Throwable] \/ UMLTimeInterval[MagicDrawUML] =
    \/-(f.createTimeIntervalInstance())

  override def createUMLTimeObservation: NonEmptyList[java.lang.Throwable] \/ UMLTimeObservation[MagicDrawUML] =
    \/-(f.createTimeObservationInstance())

  override def createUMLTransition: NonEmptyList[java.lang.Throwable] \/ UMLTransition[MagicDrawUML] =
    \/-(f.createTransitionInstance())

  override def createUMLTrigger: NonEmptyList[java.lang.Throwable] \/ UMLTrigger[MagicDrawUML] =
    \/-(f.createTriggerInstance())

  override def createUMLUnmarshallAction: NonEmptyList[java.lang.Throwable] \/ UMLUnmarshallAction[MagicDrawUML] =
    \/-(f.createUnmarshallActionInstance())

  override def createUMLUsage: NonEmptyList[java.lang.Throwable] \/ UMLUsage[MagicDrawUML] =
    \/-(f.createUsageInstance())

  override def createUMLUseCase: NonEmptyList[java.lang.Throwable] \/ UMLUseCase[MagicDrawUML] =
    \/-(f.createUseCaseInstance())

  override def createUMLValuePin: NonEmptyList[java.lang.Throwable] \/ UMLValuePin[MagicDrawUML] =
    \/-(f.createValuePinInstance())

  override def createUMLValueSpecificationAction: NonEmptyList[java.lang.Throwable] \/ UMLValueSpecificationAction[MagicDrawUML] =
    \/-(f.createValueSpecificationActionInstance())

  override def createUMLVariable: NonEmptyList[java.lang.Throwable] \/ UMLVariable[MagicDrawUML] =
    \/-(f.createVariableInstance())

}