package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLControlNode 
  extends UMLControlNode[MagicDrawUML]
  with MagicDrawUMLActivityNode {

  override protected def e: Uml#ControlNode
  import ops._

}
