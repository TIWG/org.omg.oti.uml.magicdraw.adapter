package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLMessageEvent 
  extends UMLMessageEvent[MagicDrawUML]
  with MagicDrawUMLEvent {

  override protected def e: Uml#MessageEvent
  import ops._

}
