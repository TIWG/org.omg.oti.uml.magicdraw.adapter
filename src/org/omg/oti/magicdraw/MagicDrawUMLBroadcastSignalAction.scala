package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLBroadcastSignalAction 
  extends UMLBroadcastSignalAction[MagicDrawUML]
  with MagicDrawUMLInvocationAction {

  override protected def e: Uml#BroadcastSignalAction
  import ops._

}
