package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLFlowFinalNode 
  extends UMLFlowFinalNode[MagicDrawUML]
  with MagicDrawUMLFinalNode {

  override protected def e: Uml#FlowFinalNode
  import ops._

}
