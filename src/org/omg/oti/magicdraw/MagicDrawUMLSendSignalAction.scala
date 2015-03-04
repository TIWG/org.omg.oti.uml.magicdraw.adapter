package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLSendSignalAction 
  extends UMLSendSignalAction[MagicDrawUML]
  with MagicDrawUMLInvocationAction {

  override protected def e: Uml#SendSignalAction
  import ops._

  // 16.13
	override def signal: Option[UMLSignal[Uml]] = ???
  
  // 16.13
	override def target: Option[UMLInputPin[Uml]] = ???
  
}
