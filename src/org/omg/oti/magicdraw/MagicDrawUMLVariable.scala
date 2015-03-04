package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLVariable 
  extends UMLVariable[MagicDrawUML]
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLMultiplicityElement {

  override protected def e: Uml#Variable
  import ops._

  // 16.37
	override def variable_variableAction: Set[UMLVariableAction[Uml]] = ???
}
