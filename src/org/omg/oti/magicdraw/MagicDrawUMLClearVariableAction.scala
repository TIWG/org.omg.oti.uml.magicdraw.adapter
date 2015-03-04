package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLClearVariableAction 
  extends UMLClearVariableAction[MagicDrawUML]
  with MagicDrawUMLVariableAction {

  override protected def e: Uml#ClearVariableAction
  import ops._

}
