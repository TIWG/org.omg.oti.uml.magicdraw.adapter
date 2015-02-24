package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLWriteVariableAction 
  extends UMLWriteVariableAction[MagicDrawUML]
  with MagicDrawUMLVariableAction {

  override protected def e: Uml#WriteVariableAction
  import ops._

  override def value: Option[UMLInputPin[Uml]] =
    Option.apply( e.getValue )
    
}
