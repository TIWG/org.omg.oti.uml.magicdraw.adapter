package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityFinalNode 
  extends UMLActivityFinalNode[MagicDrawUML]
  with MagicDrawUMLFinalNode {

  override protected def e: Uml#ActivityFinalNode
  import ops._

}
