package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLChangeEvent 
  extends UMLChangeEvent[MagicDrawUML]
  with MagicDrawUMLEvent {

  override protected def e: Uml#ChangeEvent
  import ops._

  override def changeExpression = 
    Option.apply( e.getChangeExpression )
  
}
