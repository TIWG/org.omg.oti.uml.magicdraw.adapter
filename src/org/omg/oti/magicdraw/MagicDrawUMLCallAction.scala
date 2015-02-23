package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCallAction 
  extends UMLCallAction[MagicDrawUML]
  with MagicDrawUMLInvocationAction {

  override protected def e: Uml#CallAction
  import ops._

}
