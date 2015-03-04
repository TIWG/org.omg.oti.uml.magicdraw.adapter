package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTimeEvent 
  extends UMLTimeEvent[MagicDrawUML]
  with MagicDrawUMLEvent {

  override protected def e: Uml#TimeEvent
  import ops._

  override def when = Option.apply( e.getWhen )
    
	override def isRelative: Boolean =
    e.isRelative
    
}
