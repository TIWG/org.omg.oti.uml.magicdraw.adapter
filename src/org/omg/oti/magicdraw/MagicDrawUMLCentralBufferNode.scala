package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCentralBufferNode 
  extends UMLCentralBufferNode[MagicDrawUML]
  with MagicDrawUMLObjectNode {

  override protected def e: Uml#CentralBufferNode
  import ops._

}
