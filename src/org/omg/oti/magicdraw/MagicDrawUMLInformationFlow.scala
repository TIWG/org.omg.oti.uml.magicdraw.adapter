package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInformationFlow 
  extends UMLInformationFlow[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#InformationFlow
  import ops._
  
  // 20.1  
	override def conveyed: Set[UMLClassifier[Uml]] = ???
    
  // 20.1  
	override def realization: Set[UMLRelationship[Uml]] = ??? 
  
  // 20.1  
	override def realizingActivityEdge: Set[UMLActivityEdge[Uml]] = ??? 
	
  // 20.1
  override def realizingConnector: Set[UMLConnector[Uml]] = ??? 
	
  // 20.1  
  override def realizingMessage: Set[UMLMessage[Uml]] = ??? 
}
