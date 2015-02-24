package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLRemoveVariableValueAction 
  extends UMLRemoveVariableValueAction[MagicDrawUML]
  with MagicDrawUMLWriteVariableAction {

  override protected def e: Uml#RemoveVariableValueAction
  import ops._

  // 16.37
  override def isRemoveDuplicates: Boolean = ???
  
  // 16.37  
	override def removeAt: Option[UMLInputPin[Uml]] = ???
}
