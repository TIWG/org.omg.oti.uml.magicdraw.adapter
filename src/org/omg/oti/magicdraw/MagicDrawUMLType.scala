package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLType 
  extends UMLType[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#Type
  import ops._
  
  override def endType_association = e.get_associationOfEndType.toSet[Uml#Association]

  override def type_operation = ???
  
  override def type_typedElement = e.get_typedElementOfType.toSet[Uml#TypedElement]
    
  override def raisedException_behavioralFeature = e.get_behavioralFeatureOfRaisedException.toSet[Uml#BehavioralFeature]
  
  override def raisedException_operation = e.get_operationOfRaisedException.toSet[Uml#Operation]
  
}