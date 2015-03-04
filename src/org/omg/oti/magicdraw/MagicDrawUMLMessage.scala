package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLMessage 
  extends UMLMessage[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#Message
  import ops._

  // 17.7
  override def argument: Seq[UMLValueSpecification[Uml]] = ???
  
  // 17.7
  override def connector: Option[UMLConnector[Uml]] = ???
  
  // 17.7
  override def messageKind: UMLMessageKind.Value = ???
  
  // 17.7
  override def messageSort: UMLMessageSort.Value = ???
  
  // 17.7
  override def receiveEvent: Option[UMLMessageEnd[Uml]] = ???
  
  // 17.7
  override def sendEvent: Option[UMLMessageEnd[Uml]] = ???
  
  // 17.7
  override def signature: Option[UMLNamedElement[Uml]] = ???
  
  // 17.7
  override def message_messageEnd: Set[UMLMessageEnd[Uml]] = ???
  
  // 20.1
  override def realizingMessage_informationFlow: Set[UMLInformationFlow[Uml]] = ???
}
