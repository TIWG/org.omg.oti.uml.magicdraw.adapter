package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLParameter 
  extends UMLParameter[MagicDrawUML]
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLMultiplicityElement {

  import ops._
  override protected def e: Uml#Parameter
  
	override def default: Option[String]

	// 9.9
	override def defaultValue: Option[UMLValueSpecification[Uml]] = ???
  
  // 9.9
	override def direction: UMLParameterDirectionKind.Value = ???
  
  // 9.9
	override def effect: UMLParameterEffectKind.Value = ???
  
  // 9.9
	override def isException: Boolean = ???
  
  // 9.9
	override def isStream: Boolean = ???
  
  // 9.9
	override def operation: Option[UMLOperation[Uml]] = ???
  
  // 9.9
	override def parameterSet: Set[UMLParameterSet[Uml]] = ???
  
  // 9.9
	override def ownedParameter_ownerFormalParam: Option[UMLBehavioralFeature[Uml]] = ???
  
  // 9.9
	override def parameter_activityParameterNode: Set[UMLActivityParameterNode[Uml]] = ???
  
  // 9.9
	override def ownedParameter_behavior: Option[UMLBehavior[Uml]] = ???
  
  // 9.9
	override def result_opaqueExpression: Set[UMLOpaqueExpression[Uml]] = ???

}