package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLoopNode 
  extends UMLLoopNode[MagicDrawUML]
  with MagicDrawUMLStructuredActivityNode {

  override protected def e: Uml#LoopNode
  import ops._

}
