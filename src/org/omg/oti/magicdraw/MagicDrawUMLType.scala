package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLType 
  extends UMLType[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#Type
  import ops._
  
  override def type_operation: Set[UMLOperation[Uml]] = ???
  
  override def type_typedElement = e.get_typedElementOfType.toSet[Uml#TypedElement]
    
  override def raisedException_behavioralFeature = e.get_behavioralFeatureOfRaisedException.toSet[Uml#BehavioralFeature]
  
  override def raisedException_operation = e.get_operationOfRaisedException.toSet[Uml#Operation]
  
}