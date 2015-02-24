package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTrigger 
  extends UMLTrigger[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#Trigger
  import ops._

  // 13.2
  override def event: Option[UMLEvent[Uml]] = ???
  
  // 13.2
  override def port: Set[UMLPort[Uml]] = ???
  
  // 16.39
  override def replyToCall_replyAction: Option[UMLReplyAction[Uml]] = ???
  
}
