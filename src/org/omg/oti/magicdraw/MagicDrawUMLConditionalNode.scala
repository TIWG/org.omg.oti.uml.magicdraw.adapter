package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLConditionalNode 
  extends UMLConditionalNode[MagicDrawUML]
  with MagicDrawUMLStructuredActivityNode {

  override protected def e: Uml#ConditionalNode
  import ops._

}
