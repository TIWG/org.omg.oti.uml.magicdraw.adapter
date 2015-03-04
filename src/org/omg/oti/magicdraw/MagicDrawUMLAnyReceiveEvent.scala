package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLAnyReceiveEvent 
  extends UMLAnyReceiveEvent[MagicDrawUML]
  with MagicDrawUMLMessageEvent {

  override protected def e: Uml#AnyReceiveEvent
  import ops._

}
