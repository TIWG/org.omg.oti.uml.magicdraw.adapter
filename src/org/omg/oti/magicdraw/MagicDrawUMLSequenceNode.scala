package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLSequenceNode 
  extends UMLSequenceNode[MagicDrawUML]
  with MagicDrawUMLStructuredActivityNode {

  override protected def e: Uml#SequenceNode
  import ops._

}
