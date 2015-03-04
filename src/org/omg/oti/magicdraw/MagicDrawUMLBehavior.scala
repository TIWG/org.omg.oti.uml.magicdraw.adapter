package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLBehavior 
  extends UMLBehavior[MagicDrawUML]
  with MagicDrawUMLClass {

  override protected def e: Uml#Behavior
  import ops._

  override def context = Option.apply( e.getContext )
  
  override def isReentrant = e.isReentrant
  
  override def ownedParameter = e.getOwnedParameter.toSeq
  
  override def postcondition = e.getPostcondition.toSet[Uml#Constraint]
  
  override def precondition = e.getPrecondition.toSet[Uml#Constraint]
  
  override def specification = Option.apply( e.getSpecification )
  
  override def redefinedBehavior = e.getRedefinedBehavior.toSet[Uml#Behavior]
  
  override def selection_objectFlow = e.get_objectFlowOfSelection.toSet[Uml#ObjectFlow]
  
  override def behavior_callBehaviorAction = e.get_callBehaviorActionOfBehavior.toSet[Uml#CallBehaviorAction]
  
  override def behavior_behaviorExecutionSpecification = e.get_behaviorExecutionSpecificationOfBehavior.toSet[Uml#BehaviorExecutionSpecification]
  
  override def decisionInput_decisionNode = e.get_decisionNodeOfDecisionInput.toSet[Uml#DecisionNode]
  
  override def reducer_reduceAction = e.get_reduceActionOfReducer.toSet[Uml#ReduceAction]
  
  override def selection_objectNode = e.get_objectNodeOfSelection.toSet[Uml#ObjectNode]
  
  override def transformation_objectFlow = e.get_objectFlowOfTransformation.toSet[Uml#ObjectFlow]
  
  override def redefinedBehavior_behavior = e.getRedefinedBehavior.toSet[Uml#Behavior]
  
  override def behavior_opaqueExpression = e.get_opaqueExpressionOfBehavior.toSet[Uml#OpaqueExpression]
  
  override def contract_connector = e.get_connectorOfContract.toSet[Uml#Connector]
  
  override def classifierBehavior_behavioredClassifier: Option[UMLBehavioredClassifier[Uml]] = ???
  
  	override def ownedBehavior_behavioredClassifier: Option[UMLBehavioredClassifier[Uml]] = ???
    
  override def doActivity_state: Option[UMLState[Uml]] = ???
  
  override def entry_state: Option[UMLState[Uml]] = ???

	/**
	 * <!-- begin-model-doc -->
	 * <!-- end-model-doc -->
	 *
	 * @property derived="false" ordered="false" unique="true" aggregation="none" multiplicity="0..1"
	 * @opposite org.omg.oti.api.UMLState.exit
	 */
	override def exit_state: Option[UMLState[Uml]] = ???
  
}
