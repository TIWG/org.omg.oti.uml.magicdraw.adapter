package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCallOperationAction 
  extends UMLCallOperationAction[MagicDrawUML]
  with MagicDrawUMLCallAction {

  override protected def e: Uml#CallOperationAction
  import ops._

}
