package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLForkNode 
  extends UMLForkNode[MagicDrawUML]
  with MagicDrawUMLControlNode {

  override protected def e: Uml#ForkNode
  import ops._

}
