/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * *   Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * *   Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 * *   Neither the name of Caltech nor its operating division, the Jet
 *    Propulsion Laboratory, nor the names of its contributors may be
 *    used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw

import scala.language.postfixOps
import scala.reflect.runtime.universe._
import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import org.omg.oti.api._
import org.omg.oti.operations._
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import org.omg.oti.canonicalXMI.BuiltInDocument
import java.net.URI
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper 
import org.omg.oti.canonicalXMI.DocumentEdge

trait MagicDrawUMLOps extends EarlyInit[MagicDrawUMLOps] with UMLOps[MagicDrawUML] {

  val project: Project

  import scala.reflect._

  val MD_ABSTRACTION: TypeTag[MagicDrawUML#Abstraction] = typeTag[MagicDrawUML#Abstraction]
  override implicit val ABSTRACTION = MD_ABSTRACTION

  val MD_ACCEPT_CALL_ACTION: TypeTag[MagicDrawUML#AcceptCallAction] = typeTag[MagicDrawUML#AcceptCallAction]
  override implicit val ACCEPT_CALL_ACTION = MD_ACCEPT_CALL_ACTION

  val MD_ACCEPT_EVENT_ACTION: TypeTag[MagicDrawUML#AcceptEventAction] = typeTag[MagicDrawUML#AcceptEventAction]
  override implicit val ACCEPT_EVENT_ACTION = MD_ACCEPT_EVENT_ACTION

  val MD_ACTION: TypeTag[MagicDrawUML#Action] = typeTag[MagicDrawUML#Action]
  override implicit val ACTION = MD_ACTION

  val MD_ACTION_EXECUTION_SPECIFICATION: TypeTag[MagicDrawUML#ActionExecutionSpecification] = typeTag[MagicDrawUML#ActionExecutionSpecification]
  override implicit val ACTION_EXECUTION_SPECIFICATION = MD_ACTION_EXECUTION_SPECIFICATION

  val MD_ACTION_INPUT_PIN: TypeTag[MagicDrawUML#ActionInputPin] = typeTag[MagicDrawUML#ActionInputPin]
  override implicit val ACTION_INPUT_PIN = MD_ACTION_INPUT_PIN

  val MD_ACTIVITY: TypeTag[MagicDrawUML#Activity] = typeTag[MagicDrawUML#Activity]
  override implicit val ACTIVITY = MD_ACTIVITY

  val MD_ACTIVITY_EDGE: TypeTag[MagicDrawUML#ActivityEdge] = typeTag[MagicDrawUML#ActivityEdge]
  override implicit val ACTIVITY_EDGE = MD_ACTIVITY_EDGE

  val MD_ACTIVITY_FINAL_NODE: TypeTag[MagicDrawUML#ActivityFinalNode] = typeTag[MagicDrawUML#ActivityFinalNode]
  override implicit val ACTIVITY_FINAL_NODE = MD_ACTIVITY_FINAL_NODE

  val MD_ACTIVITY_GROUP: TypeTag[MagicDrawUML#ActivityGroup] = typeTag[MagicDrawUML#ActivityGroup]
  override implicit val ACTIVITY_GROUP = MD_ACTIVITY_GROUP

  val MD_ACTIVITY_NODE: TypeTag[MagicDrawUML#ActivityNode] = typeTag[MagicDrawUML#ActivityNode]
  override implicit val ACTIVITY_NODE = MD_ACTIVITY_NODE

  val MD_ACTIVITY_PARAMETER_NODE: TypeTag[MagicDrawUML#ActivityParameterNode] = typeTag[MagicDrawUML#ActivityParameterNode]
  override implicit val ACTIVITY_PARAMETER_NODE = MD_ACTIVITY_PARAMETER_NODE

  val MD_ACTIVITY_PARTITION: TypeTag[MagicDrawUML#ActivityPartition] = typeTag[MagicDrawUML#ActivityPartition]
  override implicit val ACTIVITY_PARTITION = MD_ACTIVITY_PARTITION

  val MD_ACTOR: TypeTag[MagicDrawUML#Actor] = typeTag[MagicDrawUML#Actor]
  override implicit val ACTOR = MD_ACTOR

  val MD_ADD_STRUCTURAL_FEATURE_VALUE_ACTION: TypeTag[MagicDrawUML#AddStructuralFeatureValueAction] = typeTag[MagicDrawUML#AddStructuralFeatureValueAction]
  override implicit val ADD_STRUCTURAL_FEATURE_VALUE_ACTION = MD_ADD_STRUCTURAL_FEATURE_VALUE_ACTION

  val MD_ADD_VARIABLE_VALUE_ACTION: TypeTag[MagicDrawUML#AddVariableValueAction] = typeTag[MagicDrawUML#AddVariableValueAction]
  override implicit val ADD_VARIABLE_VALUE_ACTION = MD_ADD_VARIABLE_VALUE_ACTION

  val MD_ANY_RECEIVE_EVENT: TypeTag[MagicDrawUML#AnyReceiveEvent] = typeTag[MagicDrawUML#AnyReceiveEvent]
  override implicit val ANY_RECEIVE_EVENT = MD_ANY_RECEIVE_EVENT

  val MD_ARTIFACT: TypeTag[MagicDrawUML#Artifact] = typeTag[MagicDrawUML#Artifact]
  override implicit val ARTIFACT = MD_ARTIFACT

  val MD_ASSOCIATION: TypeTag[MagicDrawUML#Association] = typeTag[MagicDrawUML#Association]
  override implicit val ASSOCIATION = MD_ASSOCIATION

  val MD_ASSOCIATION_CLASS: TypeTag[MagicDrawUML#AssociationClass] = typeTag[MagicDrawUML#AssociationClass]
  override implicit val ASSOCIATION_CLASS = MD_ASSOCIATION_CLASS

  val MD_BEHAVIOR: TypeTag[MagicDrawUML#Behavior] = typeTag[MagicDrawUML#Behavior]
  override implicit val BEHAVIOR = MD_BEHAVIOR

  val MD_BEHAVIOR_EXECUTION_SPECIFICATION: TypeTag[MagicDrawUML#BehaviorExecutionSpecification] = typeTag[MagicDrawUML#BehaviorExecutionSpecification]
  override implicit val BEHAVIOR_EXECUTION_SPECIFICATION = MD_BEHAVIOR_EXECUTION_SPECIFICATION

  val MD_BEHAVIORAL_FEATURE: TypeTag[MagicDrawUML#BehavioralFeature] = typeTag[MagicDrawUML#BehavioralFeature]
  override implicit val BEHAVIORAL_FEATURE = MD_BEHAVIORAL_FEATURE

  val MD_BEHAVIORED_CLASSIFIER: TypeTag[MagicDrawUML#BehavioredClassifier] = typeTag[MagicDrawUML#BehavioredClassifier]
  override implicit val BEHAVIORED_CLASSIFIER = MD_BEHAVIORED_CLASSIFIER

  val MD_BROADCAST_SIGNAL_ACTION: TypeTag[MagicDrawUML#BroadcastSignalAction] = typeTag[MagicDrawUML#BroadcastSignalAction]
  override implicit val BROADCAST_SIGNAL_ACTION = MD_BROADCAST_SIGNAL_ACTION

  val MD_CALL_ACTION: TypeTag[MagicDrawUML#CallAction] = typeTag[MagicDrawUML#CallAction]
  override implicit val CALL_ACTION = MD_CALL_ACTION

  val MD_CALL_BEHAVIOR_ACTION: TypeTag[MagicDrawUML#CallBehaviorAction] = typeTag[MagicDrawUML#CallBehaviorAction]
  override implicit val CALL_BEHAVIOR_ACTION = MD_CALL_BEHAVIOR_ACTION

  val MD_CALL_EVENT: TypeTag[MagicDrawUML#CallEvent] = typeTag[MagicDrawUML#CallEvent]
  override implicit val CALL_EVENT = MD_CALL_EVENT

  val MD_CALL_OPERATION_ACTION: TypeTag[MagicDrawUML#CallOperationAction] = typeTag[MagicDrawUML#CallOperationAction]
  override implicit val CALL_OPERATION_ACTION = MD_CALL_OPERATION_ACTION

  val MD_CENTRAL_BUFFER_NODE: TypeTag[MagicDrawUML#CentralBufferNode] = typeTag[MagicDrawUML#CentralBufferNode]
  override implicit val CENTRAL_BUFFER_NODE = MD_CENTRAL_BUFFER_NODE

  val MD_CHANGE_EVENT: TypeTag[MagicDrawUML#ChangeEvent] = typeTag[MagicDrawUML#ChangeEvent]
  override implicit val CHANGE_EVENT = MD_CHANGE_EVENT

  val MD_CLASS: TypeTag[MagicDrawUML#Class] = typeTag[MagicDrawUML#Class]
  override implicit val CLASS = MD_CLASS

  val MD_CLASSIFIER: TypeTag[MagicDrawUML#Classifier] = typeTag[MagicDrawUML#Classifier]
  override implicit val CLASSIFIER = MD_CLASSIFIER

  val MD_CLASSIFIER_TEMPLATE_PARAMETER: TypeTag[MagicDrawUML#ClassifierTemplateParameter] = typeTag[MagicDrawUML#ClassifierTemplateParameter]
  override implicit val CLASSIFIER_TEMPLATE_PARAMETER = MD_CLASSIFIER_TEMPLATE_PARAMETER

  val MD_CLAUSE: TypeTag[MagicDrawUML#Clause] = typeTag[MagicDrawUML#Clause]
  override implicit val CLAUSE = MD_CLAUSE

  val MD_CLEAR_ASSOCIATION_ACTION: TypeTag[MagicDrawUML#ClearAssociationAction] = typeTag[MagicDrawUML#ClearAssociationAction]
  override implicit val CLEAR_ASSOCIATION_ACTION = MD_CLEAR_ASSOCIATION_ACTION

  val MD_CLEAR_STRUCTURAL_FEATURE_ACTION: TypeTag[MagicDrawUML#ClearStructuralFeatureAction] = typeTag[MagicDrawUML#ClearStructuralFeatureAction]
  override implicit val CLEAR_STRUCTURAL_FEATURE_ACTION = MD_CLEAR_STRUCTURAL_FEATURE_ACTION

  val MD_CLEAR_VARIABLE_ACTION: TypeTag[MagicDrawUML#ClearVariableAction] = typeTag[MagicDrawUML#ClearVariableAction]
  override implicit val CLEAR_VARIABLE_ACTION = MD_CLEAR_VARIABLE_ACTION

  val MD_COLLABORATION: TypeTag[MagicDrawUML#Collaboration] = typeTag[MagicDrawUML#Collaboration]
  override implicit val COLLABORATION = MD_COLLABORATION

  val MD_COLLABORATION_USE: TypeTag[MagicDrawUML#CollaborationUse] = typeTag[MagicDrawUML#CollaborationUse]
  override implicit val COLLABORATION_USE = MD_COLLABORATION_USE

  val MD_COMBINED_FRAGMENT: TypeTag[MagicDrawUML#CombinedFragment] = typeTag[MagicDrawUML#CombinedFragment]
  override implicit val COMBINED_FRAGMENT = MD_COMBINED_FRAGMENT

  val MD_COMMENT: TypeTag[MagicDrawUML#Comment] = typeTag[MagicDrawUML#Comment]
  override implicit val COMMENT = MD_COMMENT

  val MD_COMMUNICATION_PATH: TypeTag[MagicDrawUML#CommunicationPath] = typeTag[MagicDrawUML#CommunicationPath]
  override implicit val COMMUNICATION_PATH = MD_COMMUNICATION_PATH

  val MD_COMPONENT: TypeTag[MagicDrawUML#Component] = typeTag[MagicDrawUML#Component]
  override implicit val COMPONENT = MD_COMPONENT

  val MD_COMPONENT_REALIZATION: TypeTag[MagicDrawUML#ComponentRealization] = typeTag[MagicDrawUML#ComponentRealization]
  override implicit val COMPONENT_REALIZATION = MD_COMPONENT_REALIZATION

  val MD_CONDITIONAL_NODE: TypeTag[MagicDrawUML#ConditionalNode] = typeTag[MagicDrawUML#ConditionalNode]
  override implicit val CONDITIONAL_NODE = MD_CONDITIONAL_NODE

  val MD_CONNECTABLE_ELEMENT: TypeTag[MagicDrawUML#ConnectableElement] = typeTag[MagicDrawUML#ConnectableElement]
  override implicit val CONNECTABLE_ELEMENT = MD_CONNECTABLE_ELEMENT

  val MD_CONNECTABLE_ELEMENT_TEMPLATE_PARAMETER: TypeTag[MagicDrawUML#ConnectableElementTemplateParameter] = typeTag[MagicDrawUML#ConnectableElementTemplateParameter]
  override implicit val CONNECTABLE_ELEMENT_TEMPLATE_PARAMETER = MD_CONNECTABLE_ELEMENT_TEMPLATE_PARAMETER

  val MD_CONNECTION_POINT_REFERENCE: TypeTag[MagicDrawUML#ConnectionPointReference] = typeTag[MagicDrawUML#ConnectionPointReference]
  override implicit val CONNECTION_POINT_REFERENCE = MD_CONNECTION_POINT_REFERENCE

  val MD_CONNECTOR: TypeTag[MagicDrawUML#Connector] = typeTag[MagicDrawUML#Connector]
  override implicit val CONNECTOR = MD_CONNECTOR

  val MD_CONNECTOR_END: TypeTag[MagicDrawUML#ConnectorEnd] = typeTag[MagicDrawUML#ConnectorEnd]
  override implicit val CONNECTOR_END = MD_CONNECTOR_END

  val MD_CONSIDER_IGNORE_FRAGMENT: TypeTag[MagicDrawUML#ConsiderIgnoreFragment] = typeTag[MagicDrawUML#ConsiderIgnoreFragment]
  override implicit val CONSIDER_IGNORE_FRAGMENT = MD_CONSIDER_IGNORE_FRAGMENT

  val MD_CONSTRAINT: TypeTag[MagicDrawUML#Constraint] = typeTag[MagicDrawUML#Constraint]
  override implicit val CONSTRAINT = MD_CONSTRAINT

  val MD_CONTINUATION: TypeTag[MagicDrawUML#Continuation] = typeTag[MagicDrawUML#Continuation]
  override implicit val CONTINUATION = MD_CONTINUATION

  val MD_CONTROL_FLOW: TypeTag[MagicDrawUML#ControlFlow] = typeTag[MagicDrawUML#ControlFlow]
  override implicit val CONTROL_FLOW = MD_CONTROL_FLOW

  val MD_CONTROL_NODE: TypeTag[MagicDrawUML#ControlNode] = typeTag[MagicDrawUML#ControlNode]
  override implicit val CONTROL_NODE = MD_CONTROL_NODE

  val MD_CREATE_LINK_ACTION: TypeTag[MagicDrawUML#CreateLinkAction] = typeTag[MagicDrawUML#CreateLinkAction]
  override implicit val CREATE_LINK_ACTION = MD_CREATE_LINK_ACTION

  val MD_CREATE_LINK_OBJECT_ACTION: TypeTag[MagicDrawUML#CreateLinkObjectAction] = typeTag[MagicDrawUML#CreateLinkObjectAction]
  override implicit val CREATE_LINK_OBJECT_ACTION = MD_CREATE_LINK_OBJECT_ACTION

  val MD_CREATE_OBJECT_ACTION: TypeTag[MagicDrawUML#CreateObjectAction] = typeTag[MagicDrawUML#CreateObjectAction]
  override implicit val CREATE_OBJECT_ACTION = MD_CREATE_OBJECT_ACTION

  val MD_DATA_STORE_NODE: TypeTag[MagicDrawUML#DataStoreNode] = typeTag[MagicDrawUML#DataStoreNode]
  override implicit val DATA_STORE_NODE = MD_DATA_STORE_NODE

  val MD_DATA_TYPE: TypeTag[MagicDrawUML#DataType] = typeTag[MagicDrawUML#DataType]
  override implicit val DATA_TYPE = MD_DATA_TYPE

  val MD_DECISION_NODE: TypeTag[MagicDrawUML#DecisionNode] = typeTag[MagicDrawUML#DecisionNode]
  override implicit val DECISION_NODE = MD_DECISION_NODE

  val MD_DEPENDENCY: TypeTag[MagicDrawUML#Dependency] = typeTag[MagicDrawUML#Dependency]
  override implicit val DEPENDENCY = MD_DEPENDENCY

  val MD_DEPLOYED_ARTIFACT: TypeTag[MagicDrawUML#DeployedArtifact] = typeTag[MagicDrawUML#DeployedArtifact]
  override implicit val DEPLOYED_ARTIFACT = MD_DEPLOYED_ARTIFACT

  val MD_DEPLOYMENT: TypeTag[MagicDrawUML#Deployment] = typeTag[MagicDrawUML#Deployment]
  override implicit val DEPLOYMENT = MD_DEPLOYMENT

  val MD_DEPLOYMENT_SPECIFICATION: TypeTag[MagicDrawUML#DeploymentSpecification] = typeTag[MagicDrawUML#DeploymentSpecification]
  override implicit val DEPLOYMENT_SPECIFICATION = MD_DEPLOYMENT_SPECIFICATION

  val MD_DEPLOYMENT_TARGET: TypeTag[MagicDrawUML#DeploymentTarget] = typeTag[MagicDrawUML#DeploymentTarget]
  override implicit val DEPLOYMENT_TARGET = MD_DEPLOYMENT_TARGET

  val MD_DESTROY_LINK_ACTION: TypeTag[MagicDrawUML#DestroyLinkAction] = typeTag[MagicDrawUML#DestroyLinkAction]
  override implicit val DESTROY_LINK_ACTION = MD_DESTROY_LINK_ACTION

  val MD_DESTROY_OBJECT_ACTION: TypeTag[MagicDrawUML#DestroyObjectAction] = typeTag[MagicDrawUML#DestroyObjectAction]
  override implicit val DESTROY_OBJECT_ACTION = MD_DESTROY_OBJECT_ACTION

  val MD_DESTRUCTION_OCCURRENCE_SPECIFICATION: TypeTag[MagicDrawUML#DestructionOccurrenceSpecification] = typeTag[MagicDrawUML#DestructionOccurrenceSpecification]
  override implicit val DESTRUCTION_OCCURRENCE_SPECIFICATION = MD_DESTRUCTION_OCCURRENCE_SPECIFICATION

  val MD_DEVICE: TypeTag[MagicDrawUML#Device] = typeTag[MagicDrawUML#Device]
  override implicit val DEVICE = MD_DEVICE

  val MD_DIRECTED_RELATIONSHIP: TypeTag[MagicDrawUML#DirectedRelationship] = typeTag[MagicDrawUML#DirectedRelationship]
  override implicit val DIRECTED_RELATIONSHIP = MD_DIRECTED_RELATIONSHIP

  val MD_DURATION: TypeTag[MagicDrawUML#Duration] = typeTag[MagicDrawUML#Duration]
  override implicit val DURATION = MD_DURATION

  val MD_DURATION_CONSTRAINT: TypeTag[MagicDrawUML#DurationConstraint] = typeTag[MagicDrawUML#DurationConstraint]
  override implicit val DURATION_CONSTRAINT = MD_DURATION_CONSTRAINT

  val MD_DURATION_INTERVAL: TypeTag[MagicDrawUML#DurationInterval] = typeTag[MagicDrawUML#DurationInterval]
  override implicit val DURATION_INTERVAL = MD_DURATION_INTERVAL

  val MD_DURATION_OBSERVATION: TypeTag[MagicDrawUML#DurationObservation] = typeTag[MagicDrawUML#DurationObservation]
  override implicit val DURATION_OBSERVATION = MD_DURATION_OBSERVATION

  val MD_ELEMENT: TypeTag[MagicDrawUML#Element] = typeTag[MagicDrawUML#Element]
  override implicit val ELEMENT = MD_ELEMENT

  val MD_ELEMENT_IMPORT: TypeTag[MagicDrawUML#ElementImport] = typeTag[MagicDrawUML#ElementImport]
  override implicit val ELEMENT_IMPORT = MD_ELEMENT_IMPORT

  val MD_ENCAPSULATED_CLASSIFIER: TypeTag[MagicDrawUML#EncapsulatedClassifier] = typeTag[MagicDrawUML#EncapsulatedClassifier]
  override implicit val ENCAPSULATED_CLASSIFIER = MD_ENCAPSULATED_CLASSIFIER

  val MD_ENUMERATION: TypeTag[MagicDrawUML#Enumeration] = typeTag[MagicDrawUML#Enumeration]
  override implicit val ENUMERATION = MD_ENUMERATION

  val MD_ENUMERATION_LITERAL: TypeTag[MagicDrawUML#EnumerationLiteral] = typeTag[MagicDrawUML#EnumerationLiteral]
  override implicit val ENUMERATION_LITERAL = MD_ENUMERATION_LITERAL

  val MD_EVENT: TypeTag[MagicDrawUML#Event] = typeTag[MagicDrawUML#Event]
  override implicit val EVENT = MD_EVENT

  val MD_EXCEPTION_HANDLER: TypeTag[MagicDrawUML#ExceptionHandler] = typeTag[MagicDrawUML#ExceptionHandler]
  override implicit val EXCEPTION_HANDLER = MD_EXCEPTION_HANDLER

  val MD_EXECUTABLE_NODE: TypeTag[MagicDrawUML#ExecutableNode] = typeTag[MagicDrawUML#ExecutableNode]
  override implicit val EXECUTABLE_NODE = MD_EXECUTABLE_NODE

  val MD_EXECUTION_ENVIRONMENT: TypeTag[MagicDrawUML#ExecutionEnvironment] = typeTag[MagicDrawUML#ExecutionEnvironment]
  override implicit val EXECUTION_ENVIRONMENT = MD_EXECUTION_ENVIRONMENT

  val MD_EXECUTION_OCCURRENCE_SPECIFICATION: TypeTag[MagicDrawUML#ExecutionOccurrenceSpecification] = typeTag[MagicDrawUML#ExecutionOccurrenceSpecification]
  override implicit val EXECUTION_OCCURRENCE_SPECIFICATION = MD_EXECUTION_OCCURRENCE_SPECIFICATION

  val MD_EXECUTION_SPECIFICATION: TypeTag[MagicDrawUML#ExecutionSpecification] = typeTag[MagicDrawUML#ExecutionSpecification]
  override implicit val EXECUTION_SPECIFICATION = MD_EXECUTION_SPECIFICATION

  val MD_EXPANSION_NODE: TypeTag[MagicDrawUML#ExpansionNode] = typeTag[MagicDrawUML#ExpansionNode]
  override implicit val EXPANSION_NODE = MD_EXPANSION_NODE

  val MD_EXPANSION_REGION: TypeTag[MagicDrawUML#ExpansionRegion] = typeTag[MagicDrawUML#ExpansionRegion]
  override implicit val EXPANSION_REGION = MD_EXPANSION_REGION

  val MD_EXPRESSION: TypeTag[MagicDrawUML#Expression] = typeTag[MagicDrawUML#Expression]
  override implicit val EXPRESSION = MD_EXPRESSION

  val MD_EXTEND: TypeTag[MagicDrawUML#Extend] = typeTag[MagicDrawUML#Extend]
  override implicit val EXTEND = MD_EXTEND

  val MD_EXTENSION: TypeTag[MagicDrawUML#Extension] = typeTag[MagicDrawUML#Extension]
  override implicit val EXTENSION = MD_EXTENSION

  val MD_EXTENSION_END: TypeTag[MagicDrawUML#ExtensionEnd] = typeTag[MagicDrawUML#ExtensionEnd]
  override implicit val EXTENSION_END = MD_EXTENSION_END

  val MD_EXTENSION_POINT: TypeTag[MagicDrawUML#ExtensionPoint] = typeTag[MagicDrawUML#ExtensionPoint]
  override implicit val EXTENSION_POINT = MD_EXTENSION_POINT

  val MD_FEATURE: TypeTag[MagicDrawUML#Feature] = typeTag[MagicDrawUML#Feature]
  override implicit val FEATURE = MD_FEATURE

  val MD_FINAL_NODE: TypeTag[MagicDrawUML#FinalNode] = typeTag[MagicDrawUML#FinalNode]
  override implicit val FINAL_NODE = MD_FINAL_NODE

  val MD_FINAL_STATE: TypeTag[MagicDrawUML#FinalState] = typeTag[MagicDrawUML#FinalState]
  override implicit val FINAL_STATE = MD_FINAL_STATE

  val MD_FLOW_FINAL_NODE: TypeTag[MagicDrawUML#FlowFinalNode] = typeTag[MagicDrawUML#FlowFinalNode]
  override implicit val FLOW_FINAL_NODE = MD_FLOW_FINAL_NODE

  val MD_FORK_NODE: TypeTag[MagicDrawUML#ForkNode] = typeTag[MagicDrawUML#ForkNode]
  override implicit val FORK_NODE = MD_FORK_NODE

  val MD_FUNCTION_BEHAVIOR: TypeTag[MagicDrawUML#FunctionBehavior] = typeTag[MagicDrawUML#FunctionBehavior]
  override implicit val FUNCTION_BEHAVIOR = MD_FUNCTION_BEHAVIOR

  val MD_GATE: TypeTag[MagicDrawUML#Gate] = typeTag[MagicDrawUML#Gate]
  override implicit val GATE = MD_GATE

  val MD_GENERAL_ORDERING: TypeTag[MagicDrawUML#GeneralOrdering] = typeTag[MagicDrawUML#GeneralOrdering]
  override implicit val GENERAL_ORDERING = MD_GENERAL_ORDERING

  val MD_GENERALIZATION: TypeTag[MagicDrawUML#Generalization] = typeTag[MagicDrawUML#Generalization]
  override implicit val GENERALIZATION = MD_GENERALIZATION

  val MD_GENERALIZATION_SET: TypeTag[MagicDrawUML#GeneralizationSet] = typeTag[MagicDrawUML#GeneralizationSet]
  override implicit val GENERALIZATION_SET = MD_GENERALIZATION_SET

  val MD_IMAGE: TypeTag[MagicDrawUML#Image] = typeTag[MagicDrawUML#Image]
  override implicit val IMAGE = MD_IMAGE

  val MD_INCLUDE: TypeTag[MagicDrawUML#Include] = typeTag[MagicDrawUML#Include]
  override implicit val INCLUDE = MD_INCLUDE

  val MD_INFORMATION_FLOW: TypeTag[MagicDrawUML#InformationFlow] = typeTag[MagicDrawUML#InformationFlow]
  override implicit val INFORMATION_FLOW = MD_INFORMATION_FLOW

  val MD_INFORMATION_ITEM: TypeTag[MagicDrawUML#InformationItem] = typeTag[MagicDrawUML#InformationItem]
  override implicit val INFORMATION_ITEM = MD_INFORMATION_ITEM

  val MD_INITIAL_NODE: TypeTag[MagicDrawUML#InitialNode] = typeTag[MagicDrawUML#InitialNode]
  override implicit val INITIAL_NODE = MD_INITIAL_NODE

  val MD_INPUT_PIN: TypeTag[MagicDrawUML#InputPin] = typeTag[MagicDrawUML#InputPin]
  override implicit val INPUT_PIN = MD_INPUT_PIN

  val MD_INSTANCE_SPECIFICATION: TypeTag[MagicDrawUML#InstanceSpecification] = typeTag[MagicDrawUML#InstanceSpecification]
  override implicit val INSTANCE_SPECIFICATION = MD_INSTANCE_SPECIFICATION

  val MD_INSTANCE_VALUE: TypeTag[MagicDrawUML#InstanceValue] = typeTag[MagicDrawUML#InstanceValue]
  override implicit val INSTANCE_VALUE = MD_INSTANCE_VALUE

  val MD_INTERACTION: TypeTag[MagicDrawUML#Interaction] = typeTag[MagicDrawUML#Interaction]
  override implicit val INTERACTION = MD_INTERACTION

  val MD_INTERACTION_CONSTRAINT: TypeTag[MagicDrawUML#InteractionConstraint] = typeTag[MagicDrawUML#InteractionConstraint]
  override implicit val INTERACTION_CONSTRAINT = MD_INTERACTION_CONSTRAINT

  val MD_INTERACTION_FRAGMENT: TypeTag[MagicDrawUML#InteractionFragment] = typeTag[MagicDrawUML#InteractionFragment]
  override implicit val INTERACTION_FRAGMENT = MD_INTERACTION_FRAGMENT

  val MD_INTERACTION_OPERAND: TypeTag[MagicDrawUML#InteractionOperand] = typeTag[MagicDrawUML#InteractionOperand]
  override implicit val INTERACTION_OPERAND = MD_INTERACTION_OPERAND

  val MD_INTERACTION_USE: TypeTag[MagicDrawUML#InteractionUse] = typeTag[MagicDrawUML#InteractionUse]
  override implicit val INTERACTION_USE = MD_INTERACTION_USE

  val MD_INTERFACE: TypeTag[MagicDrawUML#Interface] = typeTag[MagicDrawUML#Interface]
  override implicit val INTERFACE = MD_INTERFACE

  val MD_INTERFACE_REALIZATION: TypeTag[MagicDrawUML#InterfaceRealization] = typeTag[MagicDrawUML#InterfaceRealization]
  override implicit val INTERFACE_REALIZATION = MD_INTERFACE_REALIZATION

  val MD_INTERRUPTIBLE_ACTIVITY_REGION: TypeTag[MagicDrawUML#InterruptibleActivityRegion] = typeTag[MagicDrawUML#InterruptibleActivityRegion]
  override implicit val INTERRUPTIBLE_ACTIVITY_REGION = MD_INTERRUPTIBLE_ACTIVITY_REGION

  val MD_INTERVAL: TypeTag[MagicDrawUML#Interval] = typeTag[MagicDrawUML#Interval]
  override implicit val INTERVAL = MD_INTERVAL

  val MD_INTERVAL_CONSTRAINT: TypeTag[MagicDrawUML#IntervalConstraint] = typeTag[MagicDrawUML#IntervalConstraint]
  override implicit val INTERVAL_CONSTRAINT = MD_INTERVAL_CONSTRAINT

  val MD_INVOCATION_ACTION: TypeTag[MagicDrawUML#InvocationAction] = typeTag[MagicDrawUML#InvocationAction]
  override implicit val INVOCATION_ACTION = MD_INVOCATION_ACTION

  val MD_JOIN_NODE: TypeTag[MagicDrawUML#JoinNode] = typeTag[MagicDrawUML#JoinNode]
  override implicit val JOIN_NODE = MD_JOIN_NODE

  val MD_LIFELINE: TypeTag[MagicDrawUML#Lifeline] = typeTag[MagicDrawUML#Lifeline]
  override implicit val LIFELINE = MD_LIFELINE

  val MD_LINK_ACTION: TypeTag[MagicDrawUML#LinkAction] = typeTag[MagicDrawUML#LinkAction]
  override implicit val LINK_ACTION = MD_LINK_ACTION

  val MD_LINK_END_CREATION_DATA: TypeTag[MagicDrawUML#LinkEndCreationData] = typeTag[MagicDrawUML#LinkEndCreationData]
  override implicit val LINK_END_CREATION_DATA = MD_LINK_END_CREATION_DATA

  val MD_LINK_END_DATA: TypeTag[MagicDrawUML#LinkEndData] = typeTag[MagicDrawUML#LinkEndData]
  override implicit val LINK_END_DATA = MD_LINK_END_DATA

  val MD_LINK_END_DESTRUCTION_DATA: TypeTag[MagicDrawUML#LinkEndDestructionData] = typeTag[MagicDrawUML#LinkEndDestructionData]
  override implicit val LINK_END_DESTRUCTION_DATA = MD_LINK_END_DESTRUCTION_DATA

  val MD_LITERAL_BOOLEAN: TypeTag[MagicDrawUML#LiteralBoolean] = typeTag[MagicDrawUML#LiteralBoolean]
  override implicit val LITERAL_BOOLEAN = MD_LITERAL_BOOLEAN

  val MD_LITERAL_INTEGER: TypeTag[MagicDrawUML#LiteralInteger] = typeTag[MagicDrawUML#LiteralInteger]
  override implicit val LITERAL_INTEGER = MD_LITERAL_INTEGER

  val MD_LITERAL_NULL: TypeTag[MagicDrawUML#LiteralNull] = typeTag[MagicDrawUML#LiteralNull]
  override implicit val LITERAL_NULL = MD_LITERAL_NULL

  val MD_LITERAL_REAL: TypeTag[MagicDrawUML#LiteralReal] = typeTag[MagicDrawUML#LiteralReal]
  override implicit val LITERAL_REAL = MD_LITERAL_REAL

  val MD_LITERAL_SPECIFICATION: TypeTag[MagicDrawUML#LiteralSpecification] = typeTag[MagicDrawUML#LiteralSpecification]
  override implicit val LITERAL_SPECIFICATION = MD_LITERAL_SPECIFICATION

  val MD_LITERAL_STRING: TypeTag[MagicDrawUML#LiteralString] = typeTag[MagicDrawUML#LiteralString]
  override implicit val LITERAL_STRING = MD_LITERAL_STRING

  val MD_LITERAL_UNLIMITED_NATURAL: TypeTag[MagicDrawUML#LiteralUnlimitedNatural] = typeTag[MagicDrawUML#LiteralUnlimitedNatural]
  override implicit val LITERAL_UNLIMITED_NATURAL = MD_LITERAL_UNLIMITED_NATURAL

  val MD_LOOP_NODE: TypeTag[MagicDrawUML#LoopNode] = typeTag[MagicDrawUML#LoopNode]
  override implicit val LOOP_NODE = MD_LOOP_NODE

  val MD_MANIFESTATION: TypeTag[MagicDrawUML#Manifestation] = typeTag[MagicDrawUML#Manifestation]
  override implicit val MANIFESTATION = MD_MANIFESTATION

  val MD_MERGE_NODE: TypeTag[MagicDrawUML#MergeNode] = typeTag[MagicDrawUML#MergeNode]
  override implicit val MERGE_NODE = MD_MERGE_NODE

  val MD_MESSAGE: TypeTag[MagicDrawUML#Message] = typeTag[MagicDrawUML#Message]
  override implicit val MESSAGE = MD_MESSAGE

  val MD_MESSAGE_END: TypeTag[MagicDrawUML#MessageEnd] = typeTag[MagicDrawUML#MessageEnd]
  override implicit val MESSAGE_END = MD_MESSAGE_END

  val MD_MESSAGE_EVENT: TypeTag[MagicDrawUML#MessageEvent] = typeTag[MagicDrawUML#MessageEvent]
  override implicit val MESSAGE_EVENT = MD_MESSAGE_EVENT

  val MD_MESSAGE_OCCURRENCE_SPECIFICATION: TypeTag[MagicDrawUML#MessageOccurrenceSpecification] = typeTag[MagicDrawUML#MessageOccurrenceSpecification]
  override implicit val MESSAGE_OCCURRENCE_SPECIFICATION = MD_MESSAGE_OCCURRENCE_SPECIFICATION

  val MD_MODEL: TypeTag[MagicDrawUML#Model] = typeTag[MagicDrawUML#Model]
  override implicit val MODEL = MD_MODEL

  val MD_MULTIPLICITY_ELEMENT: TypeTag[MagicDrawUML#MultiplicityElement] = typeTag[MagicDrawUML#MultiplicityElement]
  override implicit val MULTIPLICITY_ELEMENT = MD_MULTIPLICITY_ELEMENT

  val MD_NAMED_ELEMENT: TypeTag[MagicDrawUML#NamedElement] = typeTag[MagicDrawUML#NamedElement]
  override implicit val NAMED_ELEMENT = MD_NAMED_ELEMENT

  val MD_NAMESPACE: TypeTag[MagicDrawUML#Namespace] = typeTag[MagicDrawUML#Namespace]
  override implicit val NAMESPACE = MD_NAMESPACE

  val MD_NODE: TypeTag[MagicDrawUML#Node] = typeTag[MagicDrawUML#Node]
  override implicit val NODE = MD_NODE

  val MD_OBJECT_FLOW: TypeTag[MagicDrawUML#ObjectFlow] = typeTag[MagicDrawUML#ObjectFlow]
  override implicit val OBJECT_FLOW = MD_OBJECT_FLOW

  val MD_OBJECT_NODE: TypeTag[MagicDrawUML#ObjectNode] = typeTag[MagicDrawUML#ObjectNode]
  override implicit val OBJECT_NODE = MD_OBJECT_NODE

  val MD_OBSERVATION: TypeTag[MagicDrawUML#Observation] = typeTag[MagicDrawUML#Observation]
  override implicit val OBSERVATION = MD_OBSERVATION

  val MD_OCCURRENCE_SPECIFICATION: TypeTag[MagicDrawUML#OccurrenceSpecification] = typeTag[MagicDrawUML#OccurrenceSpecification]
  override implicit val OCCURRENCE_SPECIFICATION = MD_OCCURRENCE_SPECIFICATION

  val MD_OPAQUE_ACTION: TypeTag[MagicDrawUML#OpaqueAction] = typeTag[MagicDrawUML#OpaqueAction]
  override implicit val OPAQUE_ACTION = MD_OPAQUE_ACTION

  val MD_OPAQUE_BEHAVIOR: TypeTag[MagicDrawUML#OpaqueBehavior] = typeTag[MagicDrawUML#OpaqueBehavior]
  override implicit val OPAQUE_BEHAVIOR = MD_OPAQUE_BEHAVIOR

  val MD_OPAQUE_EXPRESSION: TypeTag[MagicDrawUML#OpaqueExpression] = typeTag[MagicDrawUML#OpaqueExpression]
  override implicit val OPAQUE_EXPRESSION = MD_OPAQUE_EXPRESSION

  val MD_OPERATION: TypeTag[MagicDrawUML#Operation] = typeTag[MagicDrawUML#Operation]
  override implicit val OPERATION = MD_OPERATION

  val MD_OPERATION_TEMPLATE_PARAMETER: TypeTag[MagicDrawUML#OperationTemplateParameter] = typeTag[MagicDrawUML#OperationTemplateParameter]
  override implicit val OPERATION_TEMPLATE_PARAMETER = MD_OPERATION_TEMPLATE_PARAMETER

  val MD_OUTPUT_PIN: TypeTag[MagicDrawUML#OutputPin] = typeTag[MagicDrawUML#OutputPin]
  override implicit val OUTPUT_PIN = MD_OUTPUT_PIN

  val MD_PACKAGE: TypeTag[MagicDrawUML#Package] = typeTag[MagicDrawUML#Package]
  override implicit val PACKAGE = MD_PACKAGE

  val MD_PACKAGE_IMPORT: TypeTag[MagicDrawUML#PackageImport] = typeTag[MagicDrawUML#PackageImport]
  override implicit val PACKAGE_IMPORT = MD_PACKAGE_IMPORT

  val MD_PACKAGE_MERGE: TypeTag[MagicDrawUML#PackageMerge] = typeTag[MagicDrawUML#PackageMerge]
  override implicit val PACKAGE_MERGE = MD_PACKAGE_MERGE

  val MD_PACKAGEABLE_ELEMENT: TypeTag[MagicDrawUML#PackageableElement] = typeTag[MagicDrawUML#PackageableElement]
  override implicit val PACKAGEABLE_ELEMENT = MD_PACKAGEABLE_ELEMENT

  val MD_PARAMETER: TypeTag[MagicDrawUML#Parameter] = typeTag[MagicDrawUML#Parameter]
  override implicit val PARAMETER = MD_PARAMETER

  val MD_PARAMETER_SET: TypeTag[MagicDrawUML#ParameterSet] = typeTag[MagicDrawUML#ParameterSet]
  override implicit val PARAMETER_SET = MD_PARAMETER_SET

  val MD_PARAMETERABLE_ELEMENT: TypeTag[MagicDrawUML#ParameterableElement] = typeTag[MagicDrawUML#ParameterableElement]
  override implicit val PARAMETERABLE_ELEMENT = MD_PARAMETERABLE_ELEMENT

  val MD_PART_DECOMPOSITION: TypeTag[MagicDrawUML#PartDecomposition] = typeTag[MagicDrawUML#PartDecomposition]
  override implicit val PART_DECOMPOSITION = MD_PART_DECOMPOSITION

  val MD_PIN: TypeTag[MagicDrawUML#Pin] = typeTag[MagicDrawUML#Pin]
  override implicit val PIN = MD_PIN

  val MD_PORT: TypeTag[MagicDrawUML#Port] = typeTag[MagicDrawUML#Port]
  override implicit val PORT = MD_PORT

  val MD_PRIMITIVE_TYPE: TypeTag[MagicDrawUML#PrimitiveType] = typeTag[MagicDrawUML#PrimitiveType]
  override implicit val PRIMITIVE_TYPE = MD_PRIMITIVE_TYPE

  val MD_PROFILE: TypeTag[MagicDrawUML#Profile] = typeTag[MagicDrawUML#Profile]
  override implicit val PROFILE = MD_PROFILE

  val MD_PROFILE_APPLICATION: TypeTag[MagicDrawUML#ProfileApplication] = typeTag[MagicDrawUML#ProfileApplication]
  override implicit val PROFILE_APPLICATION = MD_PROFILE_APPLICATION

  val MD_PROPERTY: TypeTag[MagicDrawUML#Property] = typeTag[MagicDrawUML#Property]
  override implicit val PROPERTY = MD_PROPERTY

  val MD_PROTOCOL_CONFORMANCE: TypeTag[MagicDrawUML#ProtocolConformance] = typeTag[MagicDrawUML#ProtocolConformance]
  override implicit val PROTOCOL_CONFORMANCE = MD_PROTOCOL_CONFORMANCE

  val MD_PROTOCOL_STATE_MACHINE: TypeTag[MagicDrawUML#ProtocolStateMachine] = typeTag[MagicDrawUML#ProtocolStateMachine]
  override implicit val PROTOCOL_STATE_MACHINE = MD_PROTOCOL_STATE_MACHINE

  val MD_PROTOCOL_TRANSITION: TypeTag[MagicDrawUML#ProtocolTransition] = typeTag[MagicDrawUML#ProtocolTransition]
  override implicit val PROTOCOL_TRANSITION = MD_PROTOCOL_TRANSITION

  val MD_PSEUDOSTATE: TypeTag[MagicDrawUML#Pseudostate] = typeTag[MagicDrawUML#Pseudostate]
  override implicit val PSEUDOSTATE = MD_PSEUDOSTATE

  val MD_QUALIFIER_VALUE: TypeTag[MagicDrawUML#QualifierValue] = typeTag[MagicDrawUML#QualifierValue]
  override implicit val QUALIFIER_VALUE = MD_QUALIFIER_VALUE

  val MD_RAISE_EXCEPTION_ACTION: TypeTag[MagicDrawUML#RaiseExceptionAction] = typeTag[MagicDrawUML#RaiseExceptionAction]
  override implicit val RAISE_EXCEPTION_ACTION = MD_RAISE_EXCEPTION_ACTION

  val MD_READ_EXTENT_ACTION: TypeTag[MagicDrawUML#ReadExtentAction] = typeTag[MagicDrawUML#ReadExtentAction]
  override implicit val READ_EXTENT_ACTION = MD_READ_EXTENT_ACTION

  val MD_READ_IS_CLASSIFIED_OBJECT_ACTION: TypeTag[MagicDrawUML#ReadIsClassifiedObjectAction] = typeTag[MagicDrawUML#ReadIsClassifiedObjectAction]
  override implicit val READ_IS_CLASSIFIED_OBJECT_ACTION = MD_READ_IS_CLASSIFIED_OBJECT_ACTION

  val MD_READ_LINK_ACTION: TypeTag[MagicDrawUML#ReadLinkAction] = typeTag[MagicDrawUML#ReadLinkAction]
  override implicit val READ_LINK_ACTION = MD_READ_LINK_ACTION

  val MD_READ_LINK_OBJECT_END_ACTION: TypeTag[MagicDrawUML#ReadLinkObjectEndAction] = typeTag[MagicDrawUML#ReadLinkObjectEndAction]
  override implicit val READ_LINK_OBJECT_END_ACTION = MD_READ_LINK_OBJECT_END_ACTION

  val MD_READ_LINK_OBJECT_END_QUALIFIER_ACTION: TypeTag[MagicDrawUML#ReadLinkObjectEndQualifierAction] = typeTag[MagicDrawUML#ReadLinkObjectEndQualifierAction]
  override implicit val READ_LINK_OBJECT_END_QUALIFIER_ACTION = MD_READ_LINK_OBJECT_END_QUALIFIER_ACTION

  val MD_READ_SELF_ACTION: TypeTag[MagicDrawUML#ReadSelfAction] = typeTag[MagicDrawUML#ReadSelfAction]
  override implicit val READ_SELF_ACTION = MD_READ_SELF_ACTION

  val MD_READ_STRUCTURAL_FEATURE_ACTION: TypeTag[MagicDrawUML#ReadStructuralFeatureAction] = typeTag[MagicDrawUML#ReadStructuralFeatureAction]
  override implicit val READ_STRUCTURAL_FEATURE_ACTION = MD_READ_STRUCTURAL_FEATURE_ACTION

  val MD_READ_VARIABLE_ACTION: TypeTag[MagicDrawUML#ReadVariableAction] = typeTag[MagicDrawUML#ReadVariableAction]
  override implicit val READ_VARIABLE_ACTION = MD_READ_VARIABLE_ACTION

  val MD_REALIZATION: TypeTag[MagicDrawUML#Realization] = typeTag[MagicDrawUML#Realization]
  override implicit val REALIZATION = MD_REALIZATION

  val MD_RECEPTION: TypeTag[MagicDrawUML#Reception] = typeTag[MagicDrawUML#Reception]
  override implicit val RECEPTION = MD_RECEPTION

  val MD_RECLASSIFY_OBJECT_ACTION: TypeTag[MagicDrawUML#ReclassifyObjectAction] = typeTag[MagicDrawUML#ReclassifyObjectAction]
  override implicit val RECLASSIFY_OBJECT_ACTION = MD_RECLASSIFY_OBJECT_ACTION

  val MD_REDEFINABLE_ELEMENT: TypeTag[MagicDrawUML#RedefinableElement] = typeTag[MagicDrawUML#RedefinableElement]
  override implicit val REDEFINABLE_ELEMENT = MD_REDEFINABLE_ELEMENT

  val MD_REDEFINABLE_TEMPLATE_SIGNATURE: TypeTag[MagicDrawUML#RedefinableTemplateSignature] = typeTag[MagicDrawUML#RedefinableTemplateSignature]
  override implicit val REDEFINABLE_TEMPLATE_SIGNATURE = MD_REDEFINABLE_TEMPLATE_SIGNATURE

  val MD_REDUCE_ACTION: TypeTag[MagicDrawUML#ReduceAction] = typeTag[MagicDrawUML#ReduceAction]
  override implicit val REDUCE_ACTION = MD_REDUCE_ACTION

  val MD_REGION: TypeTag[MagicDrawUML#Region] = typeTag[MagicDrawUML#Region]
  override implicit val REGION = MD_REGION

  val MD_RELATIONSHIP: TypeTag[MagicDrawUML#Relationship] = typeTag[MagicDrawUML#Relationship]
  override implicit val RELATIONSHIP = MD_RELATIONSHIP

  val MD_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION: TypeTag[MagicDrawUML#RemoveStructuralFeatureValueAction] = typeTag[MagicDrawUML#RemoveStructuralFeatureValueAction]
  override implicit val REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION = MD_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION

  val MD_REMOVE_VARIABLE_VALUE_ACTION: TypeTag[MagicDrawUML#RemoveVariableValueAction] = typeTag[MagicDrawUML#RemoveVariableValueAction]
  override implicit val REMOVE_VARIABLE_VALUE_ACTION = MD_REMOVE_VARIABLE_VALUE_ACTION

  val MD_REPLY_ACTION: TypeTag[MagicDrawUML#ReplyAction] = typeTag[MagicDrawUML#ReplyAction]
  override implicit val REPLY_ACTION = MD_REPLY_ACTION

  val MD_SEND_OBJECT_ACTION: TypeTag[MagicDrawUML#SendObjectAction] = typeTag[MagicDrawUML#SendObjectAction]
  override implicit val SEND_OBJECT_ACTION = MD_SEND_OBJECT_ACTION

  val MD_SEND_SIGNAL_ACTION: TypeTag[MagicDrawUML#SendSignalAction] = typeTag[MagicDrawUML#SendSignalAction]
  override implicit val SEND_SIGNAL_ACTION = MD_SEND_SIGNAL_ACTION

  val MD_SEQUENCE_NODE: TypeTag[MagicDrawUML#SequenceNode] = typeTag[MagicDrawUML#SequenceNode]
  override implicit val SEQUENCE_NODE = MD_SEQUENCE_NODE

  val MD_SIGNAL: TypeTag[MagicDrawUML#Signal] = typeTag[MagicDrawUML#Signal]
  override implicit val SIGNAL = MD_SIGNAL

  val MD_SIGNAL_EVENT: TypeTag[MagicDrawUML#SignalEvent] = typeTag[MagicDrawUML#SignalEvent]
  override implicit val SIGNAL_EVENT = MD_SIGNAL_EVENT

  val MD_SLOT: TypeTag[MagicDrawUML#Slot] = typeTag[MagicDrawUML#Slot]
  override implicit val SLOT = MD_SLOT

  val MD_START_CLASSIFIER_BEHAVIOR_ACTION: TypeTag[MagicDrawUML#StartClassifierBehaviorAction] = typeTag[MagicDrawUML#StartClassifierBehaviorAction]
  override implicit val START_CLASSIFIER_BEHAVIOR_ACTION = MD_START_CLASSIFIER_BEHAVIOR_ACTION

  val MD_START_OBJECT_BEHAVIOR_ACTION: TypeTag[MagicDrawUML#StartObjectBehaviorAction] = typeTag[MagicDrawUML#StartObjectBehaviorAction]
  override implicit val START_OBJECT_BEHAVIOR_ACTION = MD_START_OBJECT_BEHAVIOR_ACTION

  val MD_STATE: TypeTag[MagicDrawUML#State] = typeTag[MagicDrawUML#State]
  override implicit val STATE = MD_STATE

  val MD_STATE_INVARIANT: TypeTag[MagicDrawUML#StateInvariant] = typeTag[MagicDrawUML#StateInvariant]
  override implicit val STATE_INVARIANT = MD_STATE_INVARIANT

  val MD_STATE_MACHINE: TypeTag[MagicDrawUML#StateMachine] = typeTag[MagicDrawUML#StateMachine]
  override implicit val STATE_MACHINE = MD_STATE_MACHINE

  val MD_STEREOTYPE: TypeTag[MagicDrawUML#Stereotype] = typeTag[MagicDrawUML#Stereotype]
  override implicit val STEREOTYPE = MD_STEREOTYPE

  val MD_STRING_EXPRESSION: TypeTag[MagicDrawUML#StringExpression] = typeTag[MagicDrawUML#StringExpression]
  override implicit val STRING_EXPRESSION = MD_STRING_EXPRESSION

  val MD_STRUCTURAL_FEATURE: TypeTag[MagicDrawUML#StructuralFeature] = typeTag[MagicDrawUML#StructuralFeature]
  override implicit val STRUCTURAL_FEATURE = MD_STRUCTURAL_FEATURE

  val MD_STRUCTURAL_FEATURE_ACTION: TypeTag[MagicDrawUML#StructuralFeatureAction] = typeTag[MagicDrawUML#StructuralFeatureAction]
  override implicit val STRUCTURAL_FEATURE_ACTION = MD_STRUCTURAL_FEATURE_ACTION

  val MD_STRUCTURED_ACTIVITY_NODE: TypeTag[MagicDrawUML#StructuredActivityNode] = typeTag[MagicDrawUML#StructuredActivityNode]
  override implicit val STRUCTURED_ACTIVITY_NODE = MD_STRUCTURED_ACTIVITY_NODE

  val MD_STRUCTURED_CLASSIFIER: TypeTag[MagicDrawUML#StructuredClassifier] = typeTag[MagicDrawUML#StructuredClassifier]
  override implicit val STRUCTURED_CLASSIFIER = MD_STRUCTURED_CLASSIFIER

  val MD_SUBSTITUTION: TypeTag[MagicDrawUML#Substitution] = typeTag[MagicDrawUML#Substitution]
  override implicit val SUBSTITUTION = MD_SUBSTITUTION

  val MD_TEMPLATE_BINDING: TypeTag[MagicDrawUML#TemplateBinding] = typeTag[MagicDrawUML#TemplateBinding]
  override implicit val TEMPLATE_BINDING = MD_TEMPLATE_BINDING

  val MD_TEMPLATE_PARAMETER: TypeTag[MagicDrawUML#TemplateParameter] = typeTag[MagicDrawUML#TemplateParameter]
  override implicit val TEMPLATE_PARAMETER = MD_TEMPLATE_PARAMETER

  val MD_TEMPLATE_PARAMETER_SUBSTITUTION: TypeTag[MagicDrawUML#TemplateParameterSubstitution] = typeTag[MagicDrawUML#TemplateParameterSubstitution]
  override implicit val TEMPLATE_PARAMETER_SUBSTITUTION = MD_TEMPLATE_PARAMETER_SUBSTITUTION

  val MD_TEMPLATE_SIGNATURE: TypeTag[MagicDrawUML#TemplateSignature] = typeTag[MagicDrawUML#TemplateSignature]
  override implicit val TEMPLATE_SIGNATURE = MD_TEMPLATE_SIGNATURE

  val MD_TEMPLATEABLE_ELEMENT: TypeTag[MagicDrawUML#TemplateableElement] = typeTag[MagicDrawUML#TemplateableElement]
  override implicit val TEMPLATEABLE_ELEMENT = MD_TEMPLATEABLE_ELEMENT

  val MD_TEST_IDENTITY_ACTION: TypeTag[MagicDrawUML#TestIdentityAction] = typeTag[MagicDrawUML#TestIdentityAction]
  override implicit val TEST_IDENTITY_ACTION = MD_TEST_IDENTITY_ACTION

  val MD_TIME_CONSTRAINT: TypeTag[MagicDrawUML#TimeConstraint] = typeTag[MagicDrawUML#TimeConstraint]
  override implicit val TIME_CONSTRAINT = MD_TIME_CONSTRAINT

  val MD_TIME_EVENT: TypeTag[MagicDrawUML#TimeEvent] = typeTag[MagicDrawUML#TimeEvent]
  override implicit val TIME_EVENT = MD_TIME_EVENT

  val MD_TIME_EXPRESSION: TypeTag[MagicDrawUML#TimeExpression] = typeTag[MagicDrawUML#TimeExpression]
  override implicit val TIME_EXPRESSION = MD_TIME_EXPRESSION

  val MD_TIME_INTERVAL: TypeTag[MagicDrawUML#TimeInterval] = typeTag[MagicDrawUML#TimeInterval]
  override implicit val TIME_INTERVAL = MD_TIME_INTERVAL

  val MD_TIME_OBSERVATION: TypeTag[MagicDrawUML#TimeObservation] = typeTag[MagicDrawUML#TimeObservation]
  override implicit val TIME_OBSERVATION = MD_TIME_OBSERVATION

  val MD_TRANSITION: TypeTag[MagicDrawUML#Transition] = typeTag[MagicDrawUML#Transition]
  override implicit val TRANSITION = MD_TRANSITION

  val MD_TRIGGER: TypeTag[MagicDrawUML#Trigger] = typeTag[MagicDrawUML#Trigger]
  override implicit val TRIGGER = MD_TRIGGER

  val MD_TYPE: TypeTag[MagicDrawUML#Type] = typeTag[MagicDrawUML#Type]
  override implicit val TYPE = MD_TYPE

  val MD_TYPED_ELEMENT: TypeTag[MagicDrawUML#TypedElement] = typeTag[MagicDrawUML#TypedElement]
  override implicit val TYPED_ELEMENT = MD_TYPED_ELEMENT

  val MD_UNMARSHALL_ACTION: TypeTag[MagicDrawUML#UnmarshallAction] = typeTag[MagicDrawUML#UnmarshallAction]
  override implicit val UNMARSHALL_ACTION = MD_UNMARSHALL_ACTION

  val MD_USAGE: TypeTag[MagicDrawUML#Usage] = typeTag[MagicDrawUML#Usage]
  override implicit val USAGE = MD_USAGE

  val MD_USE_CASE: TypeTag[MagicDrawUML#UseCase] = typeTag[MagicDrawUML#UseCase]
  override implicit val USE_CASE = MD_USE_CASE

  val MD_VALUE_PIN: TypeTag[MagicDrawUML#ValuePin] = typeTag[MagicDrawUML#ValuePin]
  override implicit val VALUE_PIN = MD_VALUE_PIN

  val MD_VALUE_SPECIFICATION: TypeTag[MagicDrawUML#ValueSpecification] = typeTag[MagicDrawUML#ValueSpecification]
  override implicit val VALUE_SPECIFICATION = MD_VALUE_SPECIFICATION

  val MD_VALUE_SPECIFICATION_ACTION: TypeTag[MagicDrawUML#ValueSpecificationAction] = typeTag[MagicDrawUML#ValueSpecificationAction]
  override implicit val VALUE_SPECIFICATION_ACTION = MD_VALUE_SPECIFICATION_ACTION

  val MD_VARIABLE: TypeTag[MagicDrawUML#Variable] = typeTag[MagicDrawUML#Variable]
  override implicit val VARIABLE = MD_VARIABLE

  val MD_VARIABLE_ACTION: TypeTag[MagicDrawUML#VariableAction] = typeTag[MagicDrawUML#VariableAction]
  override implicit val VARIABLE_ACTION = MD_VARIABLE_ACTION

  val MD_VERTEX: TypeTag[MagicDrawUML#Vertex] = typeTag[MagicDrawUML#Vertex]
  override implicit val VERTEX = MD_VERTEX

  val MD_WRITE_LINK_ACTION: TypeTag[MagicDrawUML#WriteLinkAction] = typeTag[MagicDrawUML#WriteLinkAction]
  override implicit val WRITE_LINK_ACTION = MD_WRITE_LINK_ACTION

  val MD_WRITE_STRUCTURAL_FEATURE_ACTION: TypeTag[MagicDrawUML#WriteStructuralFeatureAction] = typeTag[MagicDrawUML#WriteStructuralFeatureAction]
  override implicit val WRITE_STRUCTURAL_FEATURE_ACTION = MD_WRITE_STRUCTURAL_FEATURE_ACTION

  val MD_WRITE_VARIABLE_ACTION: TypeTag[MagicDrawUML#WriteVariableAction] = typeTag[MagicDrawUML#WriteVariableAction]
  override implicit val WRITE_VARIABLE_ACTION = MD_WRITE_VARIABLE_ACTION

  // MagicDraw-specific

  implicit val DIAGRAM: TypeTag[MagicDrawUML#Diagram] = typeTag[MagicDrawUML#Diagram]

  implicit val ELEMENT_VALUE: TypeTag[MagicDrawUML#ElementValue] = typeTag[MagicDrawUML#ElementValue]

  override val OTI_SPECIFICATION_ROOT_S = 
    StereotypesHelper.getProfile( project, "OTI" ) match {
      case null => None
      case pf => Option.apply(StereotypesHelper.getStereotype( project, "SpecificationRoot", pf ))
    }
   
  override val OTI_SPECIFICATION_ROOT_packageURI = OTI_SPECIFICATION_ROOT_S match {
    case None => None
    case Some( s ) => Option.apply(StereotypesHelper.getPropertyByName(s, "packageURI"))
  }

  override val OTI_SPECIFICATION_ROOT_documentURL = OTI_SPECIFICATION_ROOT_S match {
    case None => None
    case Some( s ) => Option.apply(StereotypesHelper.getPropertyByName(s, "documentURL"))
  }

  override val OTI_ID_S =    
    StereotypesHelper.getProfile( project, "OTI" ) match {
      case null => None
      case pf => Option.apply(StereotypesHelper.getStereotype( project, "OTI", pf ))
    }
  
  override val OTI_ID_uuid = OTI_ID_S match {
    case None => None
    case Some( s ) => Option.apply(StereotypesHelper.getPropertyByName(s, "uuid"))
  }
    
  override val SLOT_VALUE = com.nomagic.uml2.ext.magicdraw.metadata.UMLPackage.eINSTANCE.getSlot_Value

  val MD_OTI_ValidationSuite = MagicDrawValidationDataResults.lookupValidationSuite( project, "*::MagicDrawOTIValidation")
  
  val MD_OTI_ValidationConstraint_NotOTISpecificationRoot = MD_OTI_ValidationSuite match {
    case None => None
    case Some( vInfo ) => MagicDrawValidationDataResults.lookupValidationConstraint( vInfo, "*::NotOTISpecificationRoot" )
  }
  
  // val stdProfile = ModelHelper.getUMLStandardProfile(project)
  lazy val MDBuiltInPrimitiveTypes = {
    
    val mdPrimitiveTypesPkg = 
      umlPackage( project.getElementByID("_12_0EAPbeta_be00301_1157529392394_202602_1").asInstanceOf[MagicDrawUML#Package] )
 
    val mdPrimitiveTypesExtent: Set[UMLElement[MagicDrawUML]] =
      Set(mdPrimitiveTypesPkg) ++ mdPrimitiveTypesPkg.ownedType.toSet
      
    BuiltInDocument(
        uri=new URI("http://www.omg.org/spec/PrimitiveTypes/20100901"),
        nsPrefix="PrimitiveTypes",
        scope=mdPrimitiveTypesPkg,
        documentURL=new URI("http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi"),
        builtInExtent=mdPrimitiveTypesExtent )( this )
  }
  
  lazy val MDBuiltInUML = {
    
    val mdUMLPkg = 
      umlPackage( project.getElementByID("_9_0_be00301_1108053761194_467635_11463").asInstanceOf[MagicDrawUML#Package] )
 
    val mdUMLExtent: Set[UMLElement[MagicDrawUML]] =
      Set(mdUMLPkg) ++ mdUMLPkg.ownedType.selectByKindOf { case mc: UMLClass[MagicDrawUML] => mc }
      
    BuiltInDocument(
        uri=new URI("http://www.omg.org/spec/UML/20131001"),
        nsPrefix="uml",
        scope=mdUMLPkg,
        documentURL=new URI("http://www.omg.org/spec/UML/20131001/UML.xmi"),
        builtInExtent=mdUMLExtent )( this )
  }
  
  lazy val MDBuiltInStandardProfile = {
    
    val mdStandardProfile = 
      umlProfile( project.getElementByID("_9_0_be00301_1108050582343_527400_10847").asInstanceOf[MagicDrawUML#Profile] )
 
    val mdStandardProfileExtensions = mdStandardProfile.ownedType.selectByKindOf { case e: UMLExtension[MagicDrawUML] => e }
    val mdStandardProfileExtensionFeatures = mdStandardProfileExtensions flatMap (_.ownedEnd)
    val mdStandardProfileStereotypes = mdStandardProfile.ownedType.selectByKindOf { case s: UMLStereotype[MagicDrawUML] => s }
    val mdStandardProfileStereotypeFeatures = mdStandardProfileStereotypes flatMap (_.ownedAttribute)
    val mdStandardProfileExtent: Set[UMLNamedElement[MagicDrawUML]] =
      Set(mdStandardProfile) ++ 
      mdStandardProfileExtensions ++ mdStandardProfileExtensionFeatures ++
      mdStandardProfileStereotypes ++ mdStandardProfileStereotypeFeatures
      
    BuiltInDocument(
        uri=new URI("http://www.omg.org/spec/UML/20131001/StandardProfile"),
        nsPrefix="StandardProfile",
        scope=mdStandardProfile,
        documentURL=new URI("http://www.omg.org/spec/UML/20131001/StandardProfile.xmi"),
        builtInExtent=mdStandardProfileExtent.toSet[UMLElement[MagicDrawUML]] )( this )
  }
  
  lazy val MDBuiltInUML2PrimitiveTypes = DocumentEdge( MDBuiltInUML, MDBuiltInPrimitiveTypes )
  lazy val MDBuiltInStandardProfile2UML = DocumentEdge( MDBuiltInStandardProfile, MDBuiltInUML )
}