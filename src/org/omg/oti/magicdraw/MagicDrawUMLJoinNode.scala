package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLJoinNode 
  extends UMLJoinNode[MagicDrawUML]
  with MagicDrawUMLControlNode {

  override protected def e: Uml#JoinNode
  import ops._

}
