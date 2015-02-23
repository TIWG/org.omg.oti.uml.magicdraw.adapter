package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLVariableAction 
  extends UMLVariableAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#VariableAction
  import ops._

}
