package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLAddVariableValueAction 
  extends UMLAddVariableValueAction[MagicDrawUML]
  with MagicDrawUMLWriteVariableAction {

  override protected def e: Uml#AddVariableValueAction
  import ops._

}
