package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReplyAction 
  extends UMLReplyAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReplyAction
  import ops._

  // 16.39
	override def replyToCall: Option[UMLTrigger[Uml]] = ??? 
  
  // 16.39
	override def replyValue: Seq[UMLInputPin[Uml]] = ???
  
  // 16.39
  override def returnInformation: Option[UMLInputPin[Uml]] = ???
  
  
}
