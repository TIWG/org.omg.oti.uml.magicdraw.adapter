package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLSignalEvent 
  extends UMLSignalEvent[MagicDrawUML]
  with MagicDrawUMLMessageEvent {

  override protected def e: Uml#SignalEvent
  import ops._

  override def signal = Option.apply( e.getSignal )
  
}
