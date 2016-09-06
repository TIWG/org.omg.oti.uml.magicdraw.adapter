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

package org.omg.oti.magicdraw.uml.write

import org.omg.oti.magicdraw.uml.read.{MagicDrawUML, MagicDrawUMLUtil}

import org.omg.oti.uml.read.api._
import org.omg.oti.uml.write.api.UMLFactory
import scala.collection.immutable.Set
import scalaz._

case class MagicDrawUMLFactory(mdUMLUtils: MagicDrawUMLUtil)
  extends UMLFactory[MagicDrawUML] {

  val f = mdUMLUtils.project.getElementsFactory

  import mdUMLUtils._

  override def createUMLAbstraction: Set[java.lang.Throwable] \/ UMLAbstraction[MagicDrawUML] =
    \/-(f.createAbstractionInstance())

  override def createUMLAcceptCallAction: Set[java.lang.Throwable] \/ UMLAcceptCallAction[MagicDrawUML] =
    \/-(f.createAcceptCallActionInstance())

  override def createUMLAcceptEventAction: Set[java.lang.Throwable] \/ UMLAcceptEventAction[MagicDrawUML] =
    \/-(f.createAcceptEventActionInstance())

  override def createUMLActionExecutionSpecification: Set[java.lang.Throwable] \/ UMLActionExecutionSpecification[Uml] =
    \/-(f.createActionExecutionSpecificationInstance())

  override def createUMLActionInputPin: Set[java.lang.Throwable] \/ UMLActionInputPin[MagicDrawUML] =
    \/-(f.createActionInputPinInstance())

  override def createUMLActivity: Set[java.lang.Throwable] \/ UMLActivity[MagicDrawUML] =
    \/-(f.createActivityInstance())

  override def createUMLActivityFinalNode: Set[java.lang.Throwable] \/ UMLActivityFinalNode[MagicDrawUML] =
    \/-(f.createActivityFinalNodeInstance())

  override def createUMLActivityParameterNode: Set[java.lang.Throwable] \/ UMLActivityParameterNode[MagicDrawUML] =
    \/-(f.createActivityParameterNodeInstance())

  override def createUMLActivityPartition: Set[java.lang.Throwable] \/ UMLActivityPartition[MagicDrawUML] =
    \/-(f.createActivityPartitionInstance())

  override def createUMLActor: Set[java.lang.Throwable] \/ UMLActor[MagicDrawUML] =
    \/-(f.createActorInstance())

  override def createUMLAddStructuralFeatureValueAction: Set[java.lang.Throwable] \/ UMLAddStructuralFeatureValueAction[MagicDrawUML] =
    \/-(f.createAddStructuralFeatureValueActionInstance())

  override def createUMLAddVariableValueAction: Set[java.lang.Throwable] \/ UMLAddVariableValueAction[MagicDrawUML] =
    \/-(f.createAddVariableValueActionInstance())

  override def createUMLAnyReceiveEvent: Set[java.lang.Throwable] \/ UMLAnyReceiveEvent[MagicDrawUML] =
    \/-(f.createAnyReceiveEventInstance())

  override def createUMLArtifact: Set[java.lang.Throwable] \/ UMLArtifact[MagicDrawUML] =
    \/-(f.createArtifactInstance())

  override def createUMLAssociation: Set[java.lang.Throwable] \/ UMLAssociation[MagicDrawUML] =
    \/-(f.createAssociationInstance())

  override def createUMLAssociationClass: Set[java.lang.Throwable] \/ UMLAssociationClass[MagicDrawUML] =
    \/-(f.createAssociationClassInstance())

  override def createUMLBehaviorExecutionSpecification: Set[java.lang.Throwable] \/ UMLBehaviorExecutionSpecification[MagicDrawUML] =
    \/-(f.createBehaviorExecutionSpecificationInstance())

  override def createUMLBroadcastSignalAction: Set[java.lang.Throwable] \/ UMLBroadcastSignalAction[MagicDrawUML] =
    \/-(f.createBroadcastSignalActionInstance())

  override def createUMLCallBehaviorAction: Set[java.lang.Throwable] \/ UMLCallBehaviorAction[MagicDrawUML] =
    \/-(f.createCallBehaviorActionInstance())

  override def createUMLCallEvent: Set[java.lang.Throwable] \/ UMLCallEvent[MagicDrawUML] =
    \/-(f.createCallEventInstance())

  override def createUMLCallOperationAction: Set[java.lang.Throwable] \/ UMLCallOperationAction[MagicDrawUML] =
    \/-(f.createCallOperationActionInstance())

  override def createUMLCentralBufferNode: Set[java.lang.Throwable] \/ UMLCentralBufferNode[MagicDrawUML] =
    \/-(f.createCentralBufferNodeInstance())

  override def createUMLChangeEvent: Set[java.lang.Throwable] \/ UMLChangeEvent[MagicDrawUML] =
    \/-(f.createChangeEventInstance())

  override def createUMLClass: Set[java.lang.Throwable] \/ UMLClass[MagicDrawUML] =
    \/-(f.createClassInstance())

  override def createUMLClassifierTemplateParameter: Set[java.lang.Throwable] \/ UMLClassifierTemplateParameter[MagicDrawUML] =
    \/-(f.createClassifierTemplateParameterInstance())

  override def createUMLClause: Set[java.lang.Throwable] \/ UMLClause[MagicDrawUML] =
    \/-(f.createClauseInstance())

  override def createUMLClearAssociationAction: Set[java.lang.Throwable] \/ UMLClearAssociationAction[MagicDrawUML] =
    \/-(f.createClearAssociationActionInstance())

  override def createUMLClearStructuralFeatureAction: Set[java.lang.Throwable] \/ UMLClearStructuralFeatureAction[MagicDrawUML] =
    \/-(f.createClearStructuralFeatureActionInstance())

  override def createUMLClearVariableAction: Set[java.lang.Throwable] \/ UMLClearVariableAction[MagicDrawUML] =
    \/-(f.createClearVariableActionInstance())

  override def createUMLCollaboration: Set[java.lang.Throwable] \/ UMLCollaboration[MagicDrawUML] =
    \/-(f.createCollaborationInstance())

  override def createUMLCollaborationUse: Set[java.lang.Throwable] \/ UMLCollaborationUse[MagicDrawUML] =
    \/-(f.createCollaborationUseInstance())

  override def createUMLCombinedFragment: Set[java.lang.Throwable] \/ UMLCombinedFragment[MagicDrawUML] =
    \/-(f.createCombinedFragmentInstance())

  override def createUMLComment: Set[java.lang.Throwable] \/ UMLComment[MagicDrawUML] =
    \/-(f.createCommentInstance())

  override def createUMLCommunicationPath: Set[java.lang.Throwable] \/ UMLCommunicationPath[MagicDrawUML] =
    \/-(f.createCommunicationPathInstance())

  override def createUMLComponent: Set[java.lang.Throwable] \/ UMLComponent[MagicDrawUML] =
    \/-(f.createComponentInstance())

  override def createUMLComponentRealization: Set[java.lang.Throwable] \/ UMLComponentRealization[MagicDrawUML] =
    \/-(f.createComponentRealizationInstance())

  override def createUMLConditionalNode: Set[java.lang.Throwable] \/ UMLConditionalNode[MagicDrawUML] =
    \/-(f.createConditionalNodeInstance())

  override def createUMLConnectableElementTemplateParameter: Set[java.lang.Throwable] \/ UMLConnectableElementTemplateParameter[MagicDrawUML] =
    \/-(f.createConnectableElementTemplateParameterInstance())

  override def createUMLConnectionPointReference: Set[java.lang.Throwable] \/ UMLConnectionPointReference[MagicDrawUML] =
    \/-(f.createConnectionPointReferenceInstance())

  override def createUMLConnector: Set[java.lang.Throwable] \/ UMLConnector[MagicDrawUML] =
    \/-(f.createConnectorInstance())

  override def createUMLConnectorEnd: Set[java.lang.Throwable] \/ UMLConnectorEnd[MagicDrawUML] =
    \/-(f.createConnectorEndInstance())

  override def createUMLConsiderIgnoreFragment: Set[java.lang.Throwable] \/ UMLConsiderIgnoreFragment[MagicDrawUML] =
    \/-(f.createConsiderIgnoreFragmentInstance())

  override def createUMLConstraint: Set[java.lang.Throwable] \/ UMLConstraint[MagicDrawUML] =
    \/-(f.createConstraintInstance())

  override def createUMLContinuation: Set[java.lang.Throwable] \/ UMLContinuation[MagicDrawUML] =
    \/-(f.createContinuationInstance())

  override def createUMLControlFlow: Set[java.lang.Throwable] \/ UMLControlFlow[MagicDrawUML] =
    \/-(f.createControlFlowInstance())

  override def createUMLCreateLinkAction: Set[java.lang.Throwable] \/ UMLCreateLinkAction[MagicDrawUML] =
    \/-(f.createCreateLinkActionInstance())

  override def createUMLCreateLinkObjectAction: Set[java.lang.Throwable] \/ UMLCreateLinkObjectAction[MagicDrawUML] =
    \/-(f.createCreateLinkObjectActionInstance())

  override def createUMLCreateObjectAction: Set[java.lang.Throwable] \/ UMLCreateObjectAction[MagicDrawUML] =
    \/-(f.createCreateObjectActionInstance())

  override def createUMLDataStoreNode: Set[java.lang.Throwable] \/ UMLDataStoreNode[MagicDrawUML] =
    \/-(f.createDataStoreNodeInstance())

  override def createUMLDataType: Set[java.lang.Throwable] \/ UMLDataType[MagicDrawUML] =
    \/-(f.createDataTypeInstance())

  override def createUMLDecisionNode: Set[java.lang.Throwable] \/ UMLDecisionNode[MagicDrawUML] =
    \/-(f.createDecisionNodeInstance())

  override def createUMLDependency: Set[java.lang.Throwable] \/ UMLDependency[MagicDrawUML] =
    \/-(f.createDependencyInstance())

  override def createUMLDeployment: Set[java.lang.Throwable] \/ UMLDeployment[MagicDrawUML] =
    \/-(f.createDeploymentInstance())

  override def createUMLDeploymentSpecification: Set[java.lang.Throwable] \/ UMLDeploymentSpecification[MagicDrawUML] =
    \/-(f.createDeploymentSpecificationInstance())

  override def createUMLDestroyLinkAction: Set[java.lang.Throwable] \/ UMLDestroyLinkAction[MagicDrawUML] =
    \/-(f.createDestroyLinkActionInstance())

  override def createUMLDestroyObjectAction: Set[java.lang.Throwable] \/ UMLDestroyObjectAction[MagicDrawUML] =
    \/-(f.createDestroyObjectActionInstance())

  override def createUMLDestructionOccurrenceSpecification: Set[java.lang.Throwable] \/ UMLDestructionOccurrenceSpecification[MagicDrawUML] =
    \/-(f.createDestructionOccurrenceSpecificationInstance())

  override def createUMLDevice: Set[java.lang.Throwable] \/ UMLDevice[MagicDrawUML] =
    \/-(f.createDeviceInstance())

  override def createUMLDiagram: Set[java.lang.Throwable] \/ UMLDiagram[MagicDrawUML] =
    \/-(f.createDiagramInstance())

  override def createUMLDuration: Set[java.lang.Throwable] \/ UMLDuration[MagicDrawUML] =
    \/-(f.createDurationInstance())

  override def createUMLDurationConstraint: Set[java.lang.Throwable] \/ UMLDurationConstraint[MagicDrawUML] =
    \/-(f.createDurationConstraintInstance())

  override def createUMLDurationInterval: Set[java.lang.Throwable] \/ UMLDurationInterval[MagicDrawUML] =
    \/-(f.createDurationIntervalInstance())

  override def createUMLDurationObservation: Set[java.lang.Throwable] \/ UMLDurationObservation[MagicDrawUML] =
    \/-(f.createDurationObservationInstance())

  override def createUMLElementImport: Set[java.lang.Throwable] \/ UMLElementImport[MagicDrawUML] =
    \/-(f.createElementImportInstance())

  override def createUMLElementValue: Set[java.lang.Throwable] \/ UMLElementValue[MagicDrawUML]
  = \/-(f.createElementValueInstance())

  override def createUMLEnumeration: Set[java.lang.Throwable] \/ UMLEnumeration[MagicDrawUML] =
    \/-(f.createEnumerationInstance())

  override def createUMLEnumerationLiteral: Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    \/-(f.createEnumerationLiteralInstance())

  override def createUMLExceptionHandler: Set[java.lang.Throwable] \/ UMLExceptionHandler[MagicDrawUML] =
    \/-(f.createExceptionHandlerInstance())

  override def createUMLExecutionEnvironment: Set[java.lang.Throwable] \/ UMLExecutionEnvironment[MagicDrawUML] =
    \/-(f.createExecutionEnvironmentInstance())

  override def createUMLExecutionOccurrenceSpecification: Set[java.lang.Throwable] \/ UMLExecutionOccurrenceSpecification[MagicDrawUML] =
    \/-(f.createExecutionOccurrenceSpecificationInstance())

  override def createUMLExpansionNode: Set[java.lang.Throwable] \/ UMLExpansionNode[MagicDrawUML] =
    \/-(f.createExpansionNodeInstance())

  override def createUMLExpansionRegion: Set[java.lang.Throwable] \/ UMLExpansionRegion[MagicDrawUML] =
    \/-(f.createExpansionRegionInstance())

  override def createUMLExpression: Set[java.lang.Throwable] \/ UMLExpression[MagicDrawUML] =
    \/-(f.createExpressionInstance())

  override def createUMLExtend: Set[java.lang.Throwable] \/ UMLExtend[MagicDrawUML] =
    \/-(f.createExtendInstance())

  override def createUMLExtension: Set[java.lang.Throwable] \/ UMLExtension[MagicDrawUML] =
    \/-(f.createExtensionInstance())

  override def createUMLExtensionEnd: Set[java.lang.Throwable] \/ UMLExtensionEnd[MagicDrawUML] =
    \/-(f.createExtensionEndInstance())

  override def createUMLExtensionPoint: Set[java.lang.Throwable] \/ UMLExtensionPoint[MagicDrawUML] =
    \/-(f.createExtensionPointInstance())

  override def createUMLFinalState: Set[java.lang.Throwable] \/ UMLFinalState[MagicDrawUML] =
    \/-(f.createFinalStateInstance())

  override def createUMLFlowFinalNode: Set[java.lang.Throwable] \/ UMLFlowFinalNode[MagicDrawUML] =
    \/-(f.createFlowFinalNodeInstance())

  override def createUMLForkNode: Set[java.lang.Throwable] \/ UMLForkNode[MagicDrawUML] =
    \/-(f.createForkNodeInstance())

  override def createUMLFunctionBehavior: Set[java.lang.Throwable] \/ UMLFunctionBehavior[MagicDrawUML] =
    \/-(f.createFunctionBehaviorInstance())

  override def createUMLGate: Set[java.lang.Throwable] \/ UMLGate[MagicDrawUML] =
    \/-(f.createGateInstance())

  override def createUMLGeneralOrdering: Set[java.lang.Throwable] \/ UMLGeneralOrdering[MagicDrawUML] =
    \/-(f.createGeneralOrderingInstance())

  override def createUMLGeneralization: Set[java.lang.Throwable] \/ UMLGeneralization[MagicDrawUML] =
    \/-(f.createGeneralizationInstance())

  override def createUMLGeneralizationSet: Set[java.lang.Throwable] \/ UMLGeneralizationSet[MagicDrawUML] =
    \/-(f.createGeneralizationSetInstance())

  override def createUMLImage: Set[java.lang.Throwable] \/ UMLImage[MagicDrawUML] =
    \/-(f.createImageInstance())

  override def createUMLInclude: Set[java.lang.Throwable] \/ UMLInclude[MagicDrawUML] =
    \/-(f.createIncludeInstance())

  override def createUMLInformationFlow: Set[java.lang.Throwable] \/ UMLInformationFlow[MagicDrawUML] =
    \/-(f.createInformationFlowInstance())

  override def createUMLInformationItem: Set[java.lang.Throwable] \/ UMLInformationItem[MagicDrawUML] =
    \/-(f.createInformationItemInstance())

  override def createUMLInitialNode: Set[java.lang.Throwable] \/ UMLInitialNode[MagicDrawUML] =
    \/-(f.createInitialNodeInstance())

  override def createUMLInputPin: Set[java.lang.Throwable] \/ UMLInputPin[MagicDrawUML] =
    \/-(f.createInputPinInstance())

  override def createUMLInstanceSpecification: Set[java.lang.Throwable] \/ UMLInstanceSpecification[MagicDrawUML] =
    \/-(f.createInstanceSpecificationInstance())

  override def createUMLInstanceValue: Set[java.lang.Throwable] \/ UMLInstanceValue[MagicDrawUML] =
    \/-(f.createInstanceValueInstance())

  override def createUMLInteraction: Set[java.lang.Throwable] \/ UMLInteraction[MagicDrawUML] =
    \/-(f.createInteractionInstance())

  override def createUMLInteractionConstraint: Set[java.lang.Throwable] \/ UMLInteractionConstraint[MagicDrawUML] =
    \/-(f.createInteractionConstraintInstance())

  override def createUMLInteractionOperand: Set[java.lang.Throwable] \/ UMLInteractionOperand[MagicDrawUML] =
    \/-(f.createInteractionOperandInstance())

  override def createUMLInteractionUse: Set[java.lang.Throwable] \/ UMLInteractionUse[MagicDrawUML] =
    \/-(f.createInteractionUseInstance())

  override def createUMLInterface: Set[java.lang.Throwable] \/ UMLInterface[MagicDrawUML] =
    \/-(f.createInterfaceInstance())

  override def createUMLInterfaceRealization: Set[java.lang.Throwable] \/ UMLInterfaceRealization[MagicDrawUML] =
    \/-(f.createInterfaceRealizationInstance())

  override def createUMLInterruptibleActivityRegion: Set[java.lang.Throwable] \/ UMLInterruptibleActivityRegion[MagicDrawUML] =
    \/-(f.createInterruptibleActivityRegionInstance())

  override def createUMLInterval: Set[java.lang.Throwable] \/ UMLInterval[MagicDrawUML] =
    \/-(f.createIntervalInstance())

  override def createUMLIntervalConstraint: Set[java.lang.Throwable] \/ UMLIntervalConstraint[MagicDrawUML] =
    \/-(f.createIntervalConstraintInstance())

  override def createUMLJoinNode: Set[java.lang.Throwable] \/ UMLJoinNode[MagicDrawUML] =
    \/-(f.createJoinNodeInstance())

  override def createUMLLifeline: Set[java.lang.Throwable] \/ UMLLifeline[MagicDrawUML] =
    \/-(f.createLifelineInstance())

  override def createUMLLinkEndCreationData: Set[java.lang.Throwable] \/ UMLLinkEndCreationData[MagicDrawUML] =
    \/-(f.createLinkEndCreationDataInstance())

  override def createUMLLinkEndData: Set[java.lang.Throwable] \/ UMLLinkEndData[MagicDrawUML] =
    \/-(f.createLinkEndDataInstance())

  override def createUMLLinkEndDestructionData: Set[java.lang.Throwable] \/ UMLLinkEndDestructionData[MagicDrawUML] =
    \/-(f.createLinkEndDestructionDataInstance())

  override def createUMLLiteralBoolean: Set[java.lang.Throwable] \/ UMLLiteralBoolean[MagicDrawUML] =
    \/-(f.createLiteralBooleanInstance())

  override def createUMLLiteralInteger: Set[java.lang.Throwable] \/ UMLLiteralInteger[MagicDrawUML] =
    \/-(f.createLiteralIntegerInstance())

  override def createUMLLiteralNull: Set[java.lang.Throwable] \/ UMLLiteralNull[MagicDrawUML] =
    \/-(f.createLiteralNullInstance())

  override def createUMLLiteralReal: Set[java.lang.Throwable] \/ UMLLiteralReal[MagicDrawUML] =
    \/-(f.createLiteralRealInstance())

  override def createUMLLiteralString: Set[java.lang.Throwable] \/ UMLLiteralString[MagicDrawUML] =
    \/-(f.createLiteralStringInstance())

  override def createUMLLiteralUnlimitedNatural: Set[java.lang.Throwable] \/ UMLLiteralUnlimitedNatural[MagicDrawUML] =
    \/-(f.createLiteralUnlimitedNaturalInstance())

  override def createUMLLoopNode: Set[java.lang.Throwable] \/ UMLLoopNode[MagicDrawUML] =
    \/-(f.createLoopNodeInstance())

  override def createUMLManifestation: Set[java.lang.Throwable] \/ UMLManifestation[MagicDrawUML] =
    \/-(f.createManifestationInstance())

  override def createUMLMergeNode: Set[java.lang.Throwable] \/ UMLMergeNode[MagicDrawUML] =
    \/-(f.createMergeNodeInstance())

  override def createUMLMessage: Set[java.lang.Throwable] \/ UMLMessage[MagicDrawUML] =
    \/-(f.createMessageInstance())

  override def createUMLMessageOccurrenceSpecification: Set[java.lang.Throwable] \/ UMLMessageOccurrenceSpecification[MagicDrawUML] =
    \/-(f.createMessageOccurrenceSpecificationInstance())

  override def createUMLModel: Set[java.lang.Throwable] \/ UMLModel[MagicDrawUML] =
    \/-(f.createModelInstance())

  override def createUMLNode: Set[java.lang.Throwable] \/ UMLNode[MagicDrawUML] =
    \/-(f.createNodeInstance())

  override def createUMLObjectFlow: Set[java.lang.Throwable] \/ UMLObjectFlow[MagicDrawUML] =
    \/-(f.createObjectFlowInstance())

  override def createUMLOccurrenceSpecification: Set[java.lang.Throwable] \/ UMLOccurrenceSpecification[MagicDrawUML] =
    \/-(f.createOccurrenceSpecificationInstance())

  override def createUMLOpaqueAction: Set[java.lang.Throwable] \/ UMLOpaqueAction[MagicDrawUML] =
    \/-(f.createOpaqueActionInstance())

  override def createUMLOpaqueBehavior: Set[java.lang.Throwable] \/ UMLOpaqueBehavior[MagicDrawUML] =
    \/-(f.createOpaqueBehaviorInstance())

  override def createUMLOpaqueExpression: Set[java.lang.Throwable] \/ UMLOpaqueExpression[MagicDrawUML] =
    \/-(f.createOpaqueExpressionInstance())

  override def createUMLOperation: Set[java.lang.Throwable] \/ UMLOperation[MagicDrawUML] =
    \/-(f.createOperationInstance())

  override def createUMLOperationTemplateParameter: Set[java.lang.Throwable] \/ UMLOperationTemplateParameter[MagicDrawUML] =
    \/-(f.createOperationTemplateParameterInstance())

  override def createUMLOutputPin: Set[java.lang.Throwable] \/ UMLOutputPin[MagicDrawUML] =
    \/-(f.createOutputPinInstance())

  override def createUMLPackage: Set[java.lang.Throwable] \/ UMLPackage[MagicDrawUML] =
    \/-(f.createPackageInstance())

  override def createUMLPackageImport: Set[java.lang.Throwable] \/ UMLPackageImport[MagicDrawUML] =
    \/-(f.createPackageImportInstance())

  override def createUMLPackageMerge: Set[java.lang.Throwable] \/ UMLPackageMerge[MagicDrawUML] =
    \/-(f.createPackageMergeInstance())

  override def createUMLParameter: Set[java.lang.Throwable] \/ UMLParameter[MagicDrawUML] =
    \/-(f.createParameterInstance())

  override def createUMLParameterSet: Set[java.lang.Throwable] \/ UMLParameterSet[MagicDrawUML] =
    \/-(f.createParameterSetInstance())

  override def createUMLPartDecomposition: Set[java.lang.Throwable] \/ UMLPartDecomposition[MagicDrawUML] =
    \/-(f.createPartDecompositionInstance())

  override def createUMLPort: Set[java.lang.Throwable] \/ UMLPort[MagicDrawUML] =
    \/-(f.createPortInstance())

  override def createUMLPrimitiveType: Set[java.lang.Throwable] \/ UMLPrimitiveType[MagicDrawUML] =
    \/-(f.createPrimitiveTypeInstance())

  override def createUMLProfile: Set[java.lang.Throwable] \/ UMLProfile[MagicDrawUML] =
    \/-(f.createProfileInstance())

  override def createUMLProfileApplication: Set[java.lang.Throwable] \/ UMLProfileApplication[MagicDrawUML] =
    \/-(f.createProfileApplicationInstance())

  override def createUMLProperty: Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    \/-(f.createPropertyInstance())

  override def createUMLProtocolConformance: Set[java.lang.Throwable] \/ UMLProtocolConformance[MagicDrawUML] =
    \/-(f.createProtocolConformanceInstance())

  override def createUMLProtocolStateMachine: Set[java.lang.Throwable] \/ UMLProtocolStateMachine[MagicDrawUML] =
    \/-(f.createProtocolStateMachineInstance())

  override def createUMLProtocolTransition: Set[java.lang.Throwable] \/ UMLProtocolTransition[MagicDrawUML] =
    \/-(f.createProtocolTransitionInstance())

  override def createUMLPseudostate: Set[java.lang.Throwable] \/ UMLPseudostate[MagicDrawUML] =
    \/-(f.createPseudostateInstance())

  override def createUMLQualifierValue: Set[java.lang.Throwable] \/ UMLQualifierValue[MagicDrawUML] =
    \/-(f.createQualifierValueInstance())

  override def createUMLRaiseExceptionAction: Set[java.lang.Throwable] \/ UMLRaiseExceptionAction[MagicDrawUML] =
    \/-(f.createRaiseExceptionActionInstance())

  override def createUMLReadExtentAction: Set[java.lang.Throwable] \/ UMLReadExtentAction[MagicDrawUML] =
    \/-(f.createReadExtentActionInstance())

  override def createUMLReadIsClassifiedObjectAction: Set[java.lang.Throwable] \/ UMLReadIsClassifiedObjectAction[MagicDrawUML] =
    \/-(f.createReadIsClassifiedObjectActionInstance())

  override def createUMLReadLinkAction: Set[java.lang.Throwable] \/ UMLReadLinkAction[MagicDrawUML] =
    \/-(f.createReadLinkActionInstance())

  override def createUMLReadLinkObjectEndAction: Set[java.lang.Throwable] \/ UMLReadLinkObjectEndAction[MagicDrawUML] =
    \/-(f.createReadLinkObjectEndActionInstance())

  override def createUMLReadLinkObjectEndQualifierAction: Set[java.lang.Throwable] \/ UMLReadLinkObjectEndQualifierAction[MagicDrawUML] =
    \/-(f.createReadLinkObjectEndQualifierActionInstance())

  override def createUMLReadSelfAction: Set[java.lang.Throwable] \/ UMLReadSelfAction[MagicDrawUML] =
    \/-(f.createReadSelfActionInstance())

  override def createUMLReadStructuralFeatureAction: Set[java.lang.Throwable] \/ UMLReadStructuralFeatureAction[MagicDrawUML] =
    \/-(f.createReadStructuralFeatureActionInstance())

  override def createUMLReadVariableAction: Set[java.lang.Throwable] \/ UMLReadVariableAction[MagicDrawUML] =
    \/-(f.createReadVariableActionInstance())

  override def createUMLRealization: Set[java.lang.Throwable] \/ UMLRealization[MagicDrawUML] =
    \/-(f.createRealizationInstance())

  override def createUMLReception: Set[java.lang.Throwable] \/ UMLReception[MagicDrawUML] =
    \/-(f.createReceptionInstance())

  override def createUMLReclassifyObjectAction: Set[java.lang.Throwable] \/ UMLReclassifyObjectAction[MagicDrawUML] =
    \/-(f.createReclassifyObjectActionInstance())

  override def createUMLRedefinableTemplateSignature: Set[java.lang.Throwable] \/ UMLRedefinableTemplateSignature[MagicDrawUML] =
    \/-(f.createRedefinableTemplateSignatureInstance())

  override def createUMLReduceAction: Set[java.lang.Throwable] \/ UMLReduceAction[MagicDrawUML] =
    \/-(f.createReduceActionInstance())

  override def createUMLRegion: Set[java.lang.Throwable] \/ UMLRegion[MagicDrawUML] =
    \/-(f.createRegionInstance())

  override def createUMLRemoveStructuralFeatureValueAction: Set[java.lang.Throwable] \/ UMLRemoveStructuralFeatureValueAction[MagicDrawUML] =
    \/-(f.createRemoveStructuralFeatureValueActionInstance())

  override def createUMLRemoveVariableValueAction: Set[java.lang.Throwable] \/ UMLRemoveVariableValueAction[MagicDrawUML] =
    \/-(f.createRemoveVariableValueActionInstance())

  override def createUMLReplyAction: Set[java.lang.Throwable] \/ UMLReplyAction[MagicDrawUML] =
    \/-(f.createReplyActionInstance())

  override def createUMLSendObjectAction: Set[java.lang.Throwable] \/ UMLSendObjectAction[MagicDrawUML] =
    \/-(f.createSendObjectActionInstance())

  override def createUMLSendSignalAction: Set[java.lang.Throwable] \/ UMLSendSignalAction[MagicDrawUML] =
    \/-(f.createSendSignalActionInstance())

  override def createUMLSequenceNode: Set[java.lang.Throwable] \/ UMLSequenceNode[MagicDrawUML] =
    \/-(f.createSequenceNodeInstance())

  override def createUMLSignal: Set[java.lang.Throwable] \/ UMLSignal[MagicDrawUML] =
    \/-(f.createSignalInstance())

  override def createUMLSignalEvent: Set[java.lang.Throwable] \/ UMLSignalEvent[MagicDrawUML] =
    \/-(f.createSignalEventInstance())

  override def createUMLSlot: Set[java.lang.Throwable] \/ UMLSlot[MagicDrawUML] =
    \/-(f.createSlotInstance())

  override def createUMLStartClassifierBehaviorAction: Set[java.lang.Throwable] \/ UMLStartClassifierBehaviorAction[MagicDrawUML] =
    \/-(f.createStartClassifierBehaviorActionInstance())

  override def createUMLStartObjectBehaviorAction: Set[java.lang.Throwable] \/ UMLStartObjectBehaviorAction[MagicDrawUML] =
    \/-(f.createStartObjectBehaviorActionInstance())

  override def createUMLState: Set[java.lang.Throwable] \/ UMLState[MagicDrawUML] =
    \/-(f.createStateInstance())

  override def createUMLStateInvariant: Set[java.lang.Throwable] \/ UMLStateInvariant[MagicDrawUML] =
    \/-(f.createStateInvariantInstance())

  override def createUMLStateMachine: Set[java.lang.Throwable] \/ UMLStateMachine[MagicDrawUML] =
    \/-(f.createStateMachineInstance())

  override def createUMLStereotype: Set[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML] =
    \/-(f.createStereotypeInstance())

  override def createUMLStringExpression: Set[java.lang.Throwable] \/ UMLStringExpression[MagicDrawUML] =
    \/-(f.createStringExpressionInstance())

  override def createUMLStructuredActivityNode: Set[java.lang.Throwable] \/ UMLStructuredActivityNode[MagicDrawUML] =
    \/-(f.createStructuredActivityNodeInstance())

  override def createUMLSubstitution: Set[java.lang.Throwable] \/ UMLSubstitution[MagicDrawUML] =
    \/-(f.createSubstitutionInstance())

  override def createUMLTemplateBinding: Set[java.lang.Throwable] \/ UMLTemplateBinding[MagicDrawUML] =
    \/-(f.createTemplateBindingInstance())

  override def createUMLTemplateParameter: Set[java.lang.Throwable] \/ UMLTemplateParameter[MagicDrawUML] =
    \/-(f.createTemplateParameterInstance())

  override def createUMLTemplateParameterSubstitution: Set[java.lang.Throwable] \/ UMLTemplateParameterSubstitution[MagicDrawUML] =
    \/-(f.createTemplateParameterSubstitutionInstance())

  override def createUMLTemplateSignature: Set[java.lang.Throwable] \/ UMLTemplateSignature[MagicDrawUML] =
    \/-(f.createTemplateSignatureInstance())

  override def createUMLTestIdentityAction: Set[java.lang.Throwable] \/ UMLTestIdentityAction[MagicDrawUML] =
    \/-(f.createTestIdentityActionInstance())

  override def createUMLTimeConstraint: Set[java.lang.Throwable] \/ UMLTimeConstraint[MagicDrawUML] =
    \/-(f.createTimeConstraintInstance())

  override def createUMLTimeEvent: Set[java.lang.Throwable] \/ UMLTimeEvent[MagicDrawUML] =
    \/-(f.createTimeEventInstance())

  override def createUMLTimeExpression: Set[java.lang.Throwable] \/ UMLTimeExpression[MagicDrawUML] =
    \/-(f.createTimeExpressionInstance())

  override def createUMLTimeInterval: Set[java.lang.Throwable] \/ UMLTimeInterval[MagicDrawUML] =
    \/-(f.createTimeIntervalInstance())

  override def createUMLTimeObservation: Set[java.lang.Throwable] \/ UMLTimeObservation[MagicDrawUML] =
    \/-(f.createTimeObservationInstance())

  override def createUMLTransition: Set[java.lang.Throwable] \/ UMLTransition[MagicDrawUML] =
    \/-(f.createTransitionInstance())

  override def createUMLTrigger: Set[java.lang.Throwable] \/ UMLTrigger[MagicDrawUML] =
    \/-(f.createTriggerInstance())

  override def createUMLUnmarshallAction: Set[java.lang.Throwable] \/ UMLUnmarshallAction[MagicDrawUML] =
    \/-(f.createUnmarshallActionInstance())

  override def createUMLUsage: Set[java.lang.Throwable] \/ UMLUsage[MagicDrawUML] =
    \/-(f.createUsageInstance())

  override def createUMLUseCase: Set[java.lang.Throwable] \/ UMLUseCase[MagicDrawUML] =
    \/-(f.createUseCaseInstance())

  override def createUMLValuePin: Set[java.lang.Throwable] \/ UMLValuePin[MagicDrawUML] =
    \/-(f.createValuePinInstance())

  override def createUMLValueSpecificationAction: Set[java.lang.Throwable] \/ UMLValueSpecificationAction[MagicDrawUML] =
    \/-(f.createValueSpecificationActionInstance())

  override def createUMLVariable: Set[java.lang.Throwable] \/ UMLVariable[MagicDrawUML] =
    \/-(f.createVariableInstance())

}