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

import java.net.URI

import scala.{Any,Int,Boolean}
import scala.Predef.String

trait MagicDrawUML extends org.omg.oti.uml.read.api.UML {

  override type Element = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
  override type Comment = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Comment
  override type Relationship = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Relationship
  override type DirectedRelationship = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship
  
  override type NamedElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement
  override type Namespace = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Namespace
  
  override type ElementImport = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ElementImport
  override type PackageImport = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageImport
  override type PackageableElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageableElement
  
  override type Type = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type
  override type TypedElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.TypedElement
  override type MultiplicityElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.MultiplicityElement
  
  override type Constraint = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Constraint
  
  override type Dependency = com.nomagic.uml2.ext.magicdraw.classes.mddependencies.Dependency
  
  override type ValueSpecification = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification
  override type LiteralSpecification = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralSpecification
  override type LiteralNull = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralNull
  override type LiteralBoolean = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralBoolean
  override type LiteralInteger = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralInteger
  override type LiteralReal = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralReal
  override type LiteralString = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralString
  override type LiteralUnlimitedNatural = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralUnlimitedNatural
  
  override type Expression = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Expression
  override type StringExpression = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.StringExpression
  override type OpaqueExpression = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.OpaqueExpression
  
  override type Interval = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.Interval
  
  override type Classifier = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Classifier
  override type Generalization = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Generalization
  override type RedefinableElement = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.RedefinableElement
  
  override type Feature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Feature
  override type StructuralFeature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.StructuralFeature
  override type BehavioralFeature = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.BehavioralFeature
  
  override type ConnectableElement = com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.ConnectableElement
  override type Parameter = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Parameter
  
  override type Property = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property
  
  override type Operation = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Operation
  
  override type InstanceSpecification = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification
  override type Slot = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Slot
  override type InstanceValue = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceValue
  
  override type DataType = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DataType
  override type PrimitiveType = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PrimitiveType
  override type Enumeration = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration
  override type EnumerationLiteral = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral
  
  override type BehavioredClassifier = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdbasicbehaviors.BehavioredClassifier
  
  override type StructuredClassifier = com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.StructuredClassifier
  override type Connector = com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.Connector
  override type ConnectorEnd = com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.ConnectorEnd
  
  override type EncapsulatedClassifier = com.nomagic.uml2.ext.magicdraw.compositestructures.mdports.EncapsulatedClassifier
  override type Port = com.nomagic.uml2.ext.magicdraw.compositestructures.mdports.Port
  
  override type Class = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class
  
  override type Association = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association
  override type AssociationClass = com.nomagic.uml2.ext.magicdraw.classes.mdassociationclasses.AssociationClass
    
  override type Package = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package  
  override type PackageMerge = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageMerge
  override type Model = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model
  
  override type Profile = com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile
  override type ProfileApplication = com.nomagic.uml2.ext.magicdraw.mdprofiles.ProfileApplication  
  override type Stereotype = com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype
  override type Extension = com.nomagic.uml2.ext.magicdraw.mdprofiles.Extension
  override type ExtensionEnd = com.nomagic.uml2.ext.magicdraw.mdprofiles.ExtensionEnd
  override type Image = com.nomagic.uml2.ext.magicdraw.mdprofiles.Image
  
  override type Actor = com.nomagic.uml2.ext.magicdraw.mdusecases.Actor
  override type UseCase = com.nomagic.uml2.ext.magicdraw.mdusecases.UseCase
  
  // MagicDraw-specific
  
  type Diagram = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Diagram
  override type ElementValue = com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ElementValue
  
  
  override type Abstraction = com.nomagic.uml2.ext.magicdraw.classes.mddependencies.Abstraction
  override type AcceptCallAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.AcceptCallAction
  override type AcceptEventAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.AcceptEventAction
  override type Action = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action
  override type ActionExecutionSpecification = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.ActionExecutionSpecification
  override type ActionInputPin = com.nomagic.uml2.ext.magicdraw.actions.mdstructuredactions.ActionInputPin
  override type Activity = com.nomagic.uml2.ext.magicdraw.activities.mdfundamentalactivities.Activity
  override type ActivityEdge = com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.ActivityEdge
  override type ActivityFinalNode = com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.ActivityFinalNode
  override type ActivityGroup = com.nomagic.uml2.ext.magicdraw.activities.mdfundamentalactivities.ActivityGroup
  override type ActivityNode = com.nomagic.uml2.ext.magicdraw.activities.mdfundamentalactivities.ActivityNode
  override type ActivityParameterNode = com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.ActivityParameterNode
  override type ActivityPartition = com.nomagic.uml2.ext.magicdraw.activities.mdintermediateactivities.ActivityPartition
  override type AddStructuralFeatureValueAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.AddStructuralFeatureValueAction
  override type AddVariableValueAction = com.nomagic.uml2.ext.magicdraw.actions.mdstructuredactions.AddVariableValueAction
  override type AnyReceiveEvent = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.AnyReceiveEvent
  override type Artifact = com.nomagic.uml2.ext.magicdraw.deployments.mdartifacts.Artifact
  override type Behavior = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdbasicbehaviors.Behavior
  override type BehaviorExecutionSpecification = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.BehaviorExecutionSpecification
  override type BroadcastSignalAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.BroadcastSignalAction
  override type CallAction = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.CallAction
  override type CallBehaviorAction = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.CallBehaviorAction
  override type CallEvent = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallEvent
  override type CallOperationAction = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.CallOperationAction
  override type CentralBufferNode = com.nomagic.uml2.ext.magicdraw.activities.mdintermediateactivities.CentralBufferNode
  override type ChangeEvent = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.ChangeEvent
  override type ClassifierTemplateParameter = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.ClassifierTemplateParameter
  override type Clause = com.nomagic.uml2.ext.magicdraw.activities.mdstructuredactivities.Clause
  override type ClearAssociationAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.ClearAssociationAction
  override type ClearStructuralFeatureAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.ClearStructuralFeatureAction
  override type ClearVariableAction = com.nomagic.uml2.ext.magicdraw.actions.mdstructuredactions.ClearVariableAction
  override type Collaboration = com.nomagic.uml2.ext.magicdraw.compositestructures.mdcollaborations.Collaboration
  override type CollaborationUse = com.nomagic.uml2.ext.magicdraw.compositestructures.mdcollaborations.CollaborationUse
  override type CombinedFragment = com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.CombinedFragment
  override type CommunicationPath = com.nomagic.uml2.ext.magicdraw.deployments.mdnodes.CommunicationPath
  override type Component = com.nomagic.uml2.ext.magicdraw.components.mdbasiccomponents.Component
  override type ComponentRealization = com.nomagic.uml2.ext.magicdraw.components.mdbasiccomponents.ComponentRealization
  override type ConditionalNode = com.nomagic.uml2.ext.magicdraw.activities.mdstructuredactivities.ConditionalNode
  override type ConnectableElementTemplateParameter = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.ConnectableElementTemplateParameter
  override type ConnectionPointReference = com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.ConnectionPointReference
  override type ConsiderIgnoreFragment = com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.ConsiderIgnoreFragment
  override type Continuation = com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.Continuation
  override type ControlFlow = com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.ControlFlow
  override type ControlNode = com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.ControlNode
  override type CreateLinkAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.CreateLinkAction
  override type CreateLinkObjectAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.CreateLinkObjectAction
  override type CreateObjectAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.CreateObjectAction
  override type DataStoreNode = com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.DataStoreNode
  override type DecisionNode = com.nomagic.uml2.ext.magicdraw.activities.mdintermediateactivities.DecisionNode
  override type DeployedArtifact = com.nomagic.uml2.ext.magicdraw.deployments.mdnodes.DeployedArtifact
  override type Deployment = com.nomagic.uml2.ext.magicdraw.deployments.mdnodes.Deployment
  override type DeploymentSpecification = com.nomagic.uml2.ext.magicdraw.deployments.mdcomponentdeployments.DeploymentSpecification
  override type DeploymentTarget = com.nomagic.uml2.ext.magicdraw.deployments.mdnodes.DeploymentTarget
  override type DestroyLinkAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.DestroyLinkAction
  override type DestroyObjectAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.DestroyObjectAction
  override type DestructionOccurrenceSpecification = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.DestructionOccurrenceSpecification
  override type Device = com.nomagic.uml2.ext.magicdraw.deployments.mdnodes.Device
  override type Duration = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.Duration
  override type DurationConstraint = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.DurationConstraint
  override type DurationInterval = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.DurationInterval
  override type DurationObservation = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.DurationObservation
  override type Event = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.Event
  override type ExceptionHandler = com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExceptionHandler
  override type ExecutableNode = com.nomagic.uml2.ext.magicdraw.activities.mdstructuredactivities.ExecutableNode
  override type ExecutionEnvironment = com.nomagic.uml2.ext.magicdraw.deployments.mdnodes.ExecutionEnvironment
  override type ExecutionOccurrenceSpecification = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.ExecutionOccurrenceSpecification
  override type ExecutionSpecification = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.ExecutionSpecification
  override type ExpansionNode = com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExpansionNode
  override type ExpansionRegion = com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExpansionRegion
  override type Extend = com.nomagic.uml2.ext.magicdraw.mdusecases.Extend
  override type ExtensionPoint = com.nomagic.uml2.ext.magicdraw.mdusecases.ExtensionPoint
  override type FinalNode = com.nomagic.uml2.ext.magicdraw.activities.mdintermediateactivities.FinalNode
  override type FinalState = com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.FinalState
  override type FlowFinalNode = com.nomagic.uml2.ext.magicdraw.activities.mdintermediateactivities.FlowFinalNode
  override type ForkNode = com.nomagic.uml2.ext.magicdraw.activities.mdintermediateactivities.ForkNode
  override type FunctionBehavior = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdbasicbehaviors.FunctionBehavior
  override type Gate = com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.Gate
  override type GeneralOrdering = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.GeneralOrdering
  override type GeneralizationSet = com.nomagic.uml2.ext.magicdraw.classes.mdpowertypes.GeneralizationSet
  override type Include = com.nomagic.uml2.ext.magicdraw.mdusecases.Include
  override type InformationFlow = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdinformationflows.InformationFlow
  override type InformationItem = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdinformationflows.InformationItem
  override type InitialNode = com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.InitialNode
  override type InputPin = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.InputPin
  override type Interaction = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.Interaction
  override type InteractionConstraint = com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionConstraint
  override type InteractionFragment = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.InteractionFragment
  override type InteractionOperand = com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperand
  override type InteractionUse = com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionUse
  override type Interface = com.nomagic.uml2.ext.magicdraw.classes.mdinterfaces.Interface
  override type InterfaceRealization = com.nomagic.uml2.ext.magicdraw.classes.mdinterfaces.InterfaceRealization
  override type InterruptibleActivityRegion = com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.InterruptibleActivityRegion
  override type IntervalConstraint = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.IntervalConstraint
  override type InvocationAction = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.InvocationAction
  override type JoinNode = com.nomagic.uml2.ext.magicdraw.activities.mdintermediateactivities.JoinNode
  override type Lifeline = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.Lifeline
  override type LinkAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.LinkAction
  override type LinkEndCreationData = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.LinkEndCreationData
  override type LinkEndData = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.LinkEndData
  override type LinkEndDestructionData = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.LinkEndDestructionData
  override type LoopNode = com.nomagic.uml2.ext.magicdraw.activities.mdstructuredactivities.LoopNode
  override type Manifestation = com.nomagic.uml2.ext.magicdraw.deployments.mdartifacts.Manifestation
  override type MergeNode = com.nomagic.uml2.ext.magicdraw.activities.mdintermediateactivities.MergeNode
  override type Message = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.Message
  override type MessageEnd = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageEnd
  override type MessageEvent = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.MessageEvent
  override type MessageOccurrenceSpecification = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageOccurrenceSpecification
  override type Node = com.nomagic.uml2.ext.magicdraw.deployments.mdnodes.Node
  override type ObjectFlow = com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.ObjectFlow
  override type ObjectNode = com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.ObjectNode
  override type Observation = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.Observation
  override type OccurrenceSpecification = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.OccurrenceSpecification
  override type OpaqueAction = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.OpaqueAction
  override type OpaqueBehavior = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdbasicbehaviors.OpaqueBehavior
  override type OperationTemplateParameter = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.OperationTemplateParameter
  override type OutputPin = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.OutputPin
  override type ParameterSet = com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterSet
  override type ParameterableElement = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.ParameterableElement
  override type PartDecomposition = com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.PartDecomposition
  override type Pin = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Pin
  override type ProtocolConformance = com.nomagic.uml2.ext.magicdraw.statemachines.mdprotocolstatemachines.ProtocolConformance
  override type ProtocolStateMachine = com.nomagic.uml2.ext.magicdraw.statemachines.mdprotocolstatemachines.ProtocolStateMachine
  override type ProtocolTransition = com.nomagic.uml2.ext.magicdraw.statemachines.mdprotocolstatemachines.ProtocolTransition
  override type Pseudostate = com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Pseudostate
  override type QualifierValue = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.QualifierValue
  override type RaiseExceptionAction = com.nomagic.uml2.ext.magicdraw.actions.mdstructuredactions.RaiseExceptionAction
  override type ReadExtentAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.ReadExtentAction
  override type ReadIsClassifiedObjectAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.ReadIsClassifiedObjectAction
  override type ReadLinkAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.ReadLinkAction
  override type ReadLinkObjectEndAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.ReadLinkObjectEndAction
  override type ReadLinkObjectEndQualifierAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.ReadLinkObjectEndQualifierAction
  override type ReadSelfAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.ReadSelfAction
  override type ReadStructuralFeatureAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.ReadStructuralFeatureAction
  override type ReadVariableAction = com.nomagic.uml2.ext.magicdraw.actions.mdstructuredactions.ReadVariableAction
  override type Realization = com.nomagic.uml2.ext.magicdraw.classes.mddependencies.Realization
  override type Reception = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.Reception
  override type ReclassifyObjectAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.ReclassifyObjectAction
  override type RedefinableTemplateSignature = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.RedefinableTemplateSignature
  override type ReduceAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.ReduceAction
  override type Region = com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Region
  override type RemoveStructuralFeatureValueAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.RemoveStructuralFeatureValueAction
  override type RemoveVariableValueAction = com.nomagic.uml2.ext.magicdraw.actions.mdstructuredactions.RemoveVariableValueAction
  override type ReplyAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.ReplyAction
  override type SendObjectAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.SendObjectAction
  override type SendSignalAction = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.SendSignalAction
  override type SequenceNode = com.nomagic.uml2.ext.magicdraw.activities.mdstructuredactivities.SequenceNode
  override type Signal = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.Signal
  override type SignalEvent = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.SignalEvent
  override type StartClassifierBehaviorAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.StartClassifierBehaviorAction
  override type StartObjectBehaviorAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.StartObjectBehaviorAction
  override type State = com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.State
  override type StateInvariant = com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.StateInvariant
  override type StateMachine = com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.StateMachine
  override type StructuralFeatureAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.StructuralFeatureAction
  override type StructuredActivityNode = com.nomagic.uml2.ext.magicdraw.activities.mdstructuredactivities.StructuredActivityNode
  override type Substitution = com.nomagic.uml2.ext.magicdraw.classes.mddependencies.Substitution
  override type TemplateBinding = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.TemplateBinding
  override type TemplateParameter = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.TemplateParameter
  override type TemplateParameterSubstitution = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.TemplateParameterSubstitution
  override type TemplateSignature = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.TemplateSignature
  override type TemplateableElement = com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdtemplates.TemplateableElement
  override type TestIdentityAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.TestIdentityAction
  override type TimeConstraint = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.TimeConstraint
  override type TimeEvent = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.TimeEvent
  override type TimeExpression = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.TimeExpression
  override type TimeInterval = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.TimeInterval
  override type TimeObservation = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdsimpletime.TimeObservation
  override type Transition = com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Transition
  override type Trigger = com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.Trigger
  override type UnmarshallAction = com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.UnmarshallAction
  override type Usage = com.nomagic.uml2.ext.magicdraw.classes.mddependencies.Usage
  override type ValuePin = com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.ValuePin
  override type ValueSpecificationAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.ValueSpecificationAction
  override type Variable = com.nomagic.uml2.ext.magicdraw.activities.mdstructuredactivities.Variable
  override type VariableAction = com.nomagic.uml2.ext.magicdraw.actions.mdstructuredactions.VariableAction
  override type Vertex = com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Vertex
  override type WriteLinkAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.WriteLinkAction
  override type WriteStructuralFeatureAction = com.nomagic.uml2.ext.magicdraw.actions.mdintermediateactions.WriteStructuralFeatureAction
  override type WriteVariableAction = com.nomagic.uml2.ext.magicdraw.actions.mdstructuredactions.WriteVariableAction

  override type LoadURL = MagicDrawLoadURL
  override type DocumentSetAggregate = MagicDrawDocumentSetAggregate
}

case class MagicDrawDocumentSetAggregate()

/**
 * MagicDraw-specific adaptation for OTI `DocumentOps[MagicDrawUML].LoadURL`
 */
sealed trait MagicDrawLoadURL {
  val externalDocumentResourceURL: URI
}

/**
 * Load an external OTI Document as a MagicDraw Local Project
 *
 * @param externalDocumentResourceURL the external OTI Document resource to load into MagicDraw
 * @param magicDrawLocalProjectResource the location where to save the MagicDraw local project upon successful
 *                                      completion of loading the OTI Document
 */
case class MagicDrawLocalProjectLoadURL
( override val externalDocumentResourceURL: URI,
  magicDrawLocalProjectResource: URI )
extends MagicDrawLoadURL {

  override val hashCode
  : Int
  = (externalDocumentResourceURL, magicDrawLocalProjectResource).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawLocalProjectLoadURL =>
      this.hashCode == that.hashCode &&
        this.externalDocumentResourceURL == that.externalDocumentResourceURL &&
        this.magicDrawLocalProjectResource == that.magicDrawLocalProjectResource
    case _ =>
      false
  }

}

/**
 * Load an external OTI Document as a MagicDraw Server Project
 *
 * @param externalDocumentResourceURL the external OTI Document resource to load into MagicDraw
 * @param magicDrawServerProjectResource the location that the MagicDraw Server project has been loaded from
 */
case class MagicDrawServerProjectLoadURL
( override val externalDocumentResourceURL: URI,
  magicDrawServerProjectResource: URI )
  extends MagicDrawLoadURL {

  override val hashCode
  : Int
  = (externalDocumentResourceURL, magicDrawServerProjectResource).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawServerProjectLoadURL =>
      this.hashCode == that.hashCode &&
        this.externalDocumentResourceURL == that.externalDocumentResourceURL &&
        this.magicDrawServerProjectResource == that.magicDrawServerProjectResource
    case _ =>
      false
  }

}

/**
 * Load an external OTI SerializableDocument as a MagicDraw Local Attached Module
 *
 * @param externalDocumentResourceURL the external OTI Document resource to load into MagicDraw
 * @param magicDrawAttachedLocalModuleResource the location where to save the MagicDraw local attached module
 *                                             upon successful completion of loading the OTI Document
 *                                             as an attached local module of the current MagicDraw project
 */
case class MagicDrawAttachedLocalModuleSerializableDocumentLoadURL
( override val externalDocumentResourceURL: URI,
  magicDrawAttachedLocalModuleResource: URI )
  extends MagicDrawLoadURL {

  override val hashCode
  : Int
  = (externalDocumentResourceURL, magicDrawAttachedLocalModuleResource).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawAttachedLocalModuleSerializableDocumentLoadURL =>
      this.hashCode == that.hashCode &&
        this.externalDocumentResourceURL == that.externalDocumentResourceURL &&
        this.magicDrawAttachedLocalModuleResource == that.magicDrawAttachedLocalModuleResource
    case _ =>
      false
  }

}

/**
 * Load an external OTI SerializableDocument as a MagicDraw Teamwork Attached Module
 *
 * @param externalDocumentResourceURL the external OTI Document resource to load into MagicDraw
 * @param magicDrawAttachedServerModuleResource the location that the MagicDraw Server module has been loaded from
 */
case class MagicDrawAttachedServerModuleSerializableDocumentLoadURL
( override val externalDocumentResourceURL: URI,
  magicDrawAttachedServerModuleResource: URI )
  extends MagicDrawLoadURL {

  override val hashCode
  : Int
  = (externalDocumentResourceURL, magicDrawAttachedServerModuleResource).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawAttachedServerModuleSerializableDocumentLoadURL =>
      this.hashCode == that.hashCode &&
        this.externalDocumentResourceURL == that.externalDocumentResourceURL &&
        this.magicDrawAttachedServerModuleResource == that.magicDrawAttachedServerModuleResource
  }

}

/**
 * Load an external OTI BuiltInDocument as a MagicDraw Local Attached Module
 *
 * @param externalDocumentResourceURL the external OTI Document resource to load into MagicDraw
 * @param magicDrawStandardSystemProfileResourceRelativePath the location relative to MagicDraw's
 *                                                           installation root folder corresponding to
 *                                                           the MagicDraw-specific OTI BuiltInDocument
 */
case class MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL
( override val externalDocumentResourceURL: URI,
  magicDrawStandardSystemProfileResourceRelativePath: String )
  extends MagicDrawLoadURL {

  override val hashCode
  : Int
  = (externalDocumentResourceURL, magicDrawStandardSystemProfileResourceRelativePath).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL =>
      this.hashCode == that.hashCode &&
        this.externalDocumentResourceURL == that.externalDocumentResourceURL &&
        this.magicDrawStandardSystemProfileResourceRelativePath == that.magicDrawStandardSystemProfileResourceRelativePath
    case _ =>
      false
  }

}

/**
 * Import an external OTI Document into the current MagicDraw project
 *
 * @param externalDocumentResourceURL the external OTI Document resource to import into MagicDraw
 */
case class MagicDrawImportLoadURL
( override val externalDocumentResourceURL: URI )
  extends MagicDrawLoadURL {

  override val hashCode
  : Int
  = externalDocumentResourceURL.##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawImportLoadURL =>
      this.hashCode == that.hashCode &&
        this.externalDocumentResourceURL == that.externalDocumentResourceURL
    case _ =>
      false
  }

}