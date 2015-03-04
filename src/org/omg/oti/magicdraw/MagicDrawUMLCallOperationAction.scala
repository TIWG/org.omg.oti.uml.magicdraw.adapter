package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLCallOperationAction 
  extends UMLCallOperationAction[MagicDrawUML]
  with MagicDrawUMLCallAction {

  override protected def e: Uml#CallOperationAction
  import ops._
  
	override def operation: Option[UMLOperation[Uml]] =
    Option.apply( e.getOperation )
    
  override def target: Option[UMLInputPin[Uml]] =
    Option.apply( e.getTarget )
    
}
