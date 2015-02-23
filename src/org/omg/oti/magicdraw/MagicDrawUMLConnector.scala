package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLConnector 
  extends UMLConnector[MagicDrawUML]
  with MagicDrawUMLFeature {

  override protected def e: Uml#Connector
  import ops._
  
  override def end = e.getEnd.toSeq
  
  override def _type = Option.apply( e.getType )
  
  override def connector_message =
    e.get_messageOfConnector.toSet[Uml#Message]
  
  override def contract =
    e.getContract.toSet[Uml#Behavior]
  
  override def realizingConnector_informationFlow =
    e.get_informationFlowOfRealizingConnector.toSet[Uml#InformationFlow]
  
}