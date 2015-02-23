package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLControlFlow 
  extends UMLControlFlow[MagicDrawUML]
  with MagicDrawUMLActivityEdge {

  override protected def e: Uml#ControlFlow
  import ops._

}
