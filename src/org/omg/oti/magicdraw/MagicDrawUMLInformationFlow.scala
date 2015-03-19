package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInformationFlow 
  extends UMLInformationFlow[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#InformationFlow
  import ops._
  
  // 20.1  
	override def conveyed: Set[UMLClassifier[Uml]] = e.getConveyed.toSet[Uml#Classifier]
    
  // 20.1  
	override def realization: Set[UMLRelationship[Uml]] = e.getRealization.toSet[Uml#Relationship]
  
  // 20.1  
	override def realizingActivityEdge: Set[UMLActivityEdge[Uml]] = e.getRealizingActivityEdge.toSet[Uml#ActivityEdge]
	
  // 20.1
  override def realizingConnector: Set[UMLConnector[Uml]] = e.getRealizingConnector.toSet[Uml#Connector]
	
  // 20.1  
  override def realizingMessage: Set[UMLMessage[Uml]] = e.getRealizingMessage.toSet[Uml#Message]
}
