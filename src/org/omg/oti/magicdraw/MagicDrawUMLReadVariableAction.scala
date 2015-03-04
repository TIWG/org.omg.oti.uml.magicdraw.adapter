package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReadVariableAction 
  extends UMLReadVariableAction[MagicDrawUML]
  with MagicDrawUMLVariableAction {

  override protected def e: Uml#ReadVariableAction
  import ops._

  override def result: Option[UMLOutputPin[Uml]] =
    Option.apply( e.getResult )
    
}
