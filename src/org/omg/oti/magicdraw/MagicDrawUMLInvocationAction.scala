package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInvocationAction 
  extends UMLInvocationAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#InvocationAction
  import ops._

}
