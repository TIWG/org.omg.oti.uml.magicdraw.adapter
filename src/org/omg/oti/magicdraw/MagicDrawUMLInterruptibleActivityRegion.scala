package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInterruptibleActivityRegion 
  extends UMLInterruptibleActivityRegion[MagicDrawUML]
  with MagicDrawUMLActivityGroup {

  override protected def e: Uml#InterruptibleActivityRegion
  import ops._

}
