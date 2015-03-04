package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLSendObjectAction 
  extends UMLSendObjectAction[MagicDrawUML]
  with MagicDrawUMLInvocationAction {

  override protected def e: Uml#SendObjectAction
  import ops._

  // 16.13
	override def request: Option[UMLInputPin[Uml]] = ???
  
  // 16.13
	override def target: Option[UMLInputPin[Uml]] = ??? 
}
