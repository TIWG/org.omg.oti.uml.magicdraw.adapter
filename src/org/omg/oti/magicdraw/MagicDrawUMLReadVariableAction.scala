package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReadVariableAction 
  extends UMLReadVariableAction[MagicDrawUML]
  with MagicDrawUMLVariableAction {

  override protected def e: Uml#ReadVariableAction
  import ops._

}
