package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLSendSignalAction 
  extends UMLSendSignalAction[MagicDrawUML]
  with MagicDrawUMLInvocationAction {

  override protected def e: Uml#SendSignalAction
  import ops._

}
