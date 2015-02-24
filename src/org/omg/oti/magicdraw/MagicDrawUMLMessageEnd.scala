package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLMessageEnd 
  extends UMLMessageEnd[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#MessageEnd
  import ops._

  // 17.7
  override def message: Option[UMLMessage[Uml]] = ???
  
  // 17.7
  override def receiveEvent_endMessage: Option[UMLMessage[Uml]] = ???
  
  // 17.7
  override def sendEvent_endMessage: Option[UMLMessage[Uml]] = ???
}
