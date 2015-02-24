package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCallEvent 
  extends UMLCallEvent[MagicDrawUML]
  with MagicDrawUMLMessageEvent {

  override protected def e: Uml#CallEvent
  import ops._

  override def operation = 
    Option.apply( e.getOperation )
  
}
