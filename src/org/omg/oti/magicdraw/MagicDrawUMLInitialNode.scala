package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInitialNode 
  extends UMLInitialNode[MagicDrawUML]
  with MagicDrawUMLControlNode {

  override protected def e: Uml#InitialNode
  import ops._

}
