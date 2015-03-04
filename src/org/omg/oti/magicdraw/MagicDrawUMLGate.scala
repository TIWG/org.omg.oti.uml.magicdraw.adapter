package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLGate 
  extends UMLGate[MagicDrawUML]
  with MagicDrawUMLMessageEnd {

  override protected def e: Uml#Gate
  import ops._

}
