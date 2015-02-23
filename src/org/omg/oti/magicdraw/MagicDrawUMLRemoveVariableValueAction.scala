package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLRemoveVariableValueAction 
  extends UMLRemoveVariableValueAction[MagicDrawUML]
  with MagicDrawUMLWriteVariableAction {

  override protected def e: Uml#RemoveVariableValueAction
  import ops._

}
